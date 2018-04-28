/*
 * Copyright 2018 Shinya Mochida
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mikeneck.youtrack.request.http;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import org.mikeneck.youtrack.request.ApiResponse;
import org.mikeneck.youtrack.request.Handler;
import org.mikeneck.youtrack.request.UnknownException;

public class FutureBasedApiResponse<R> implements ApiResponse<R> {

  private static <T> Consumer<T> doNothing() {
    return t -> {};
  }

  private final CompletableFuture<R> future;

  FutureBasedApiResponse(CompletableFuture<R> future) {
    this.future = future;
  }

  @Override
  public ApiResponse<R> onSuccess(Handler.SuccessHandler<? super R> successHandler) {
    final CompletableFuture<R> next =
        future.whenCompleteAsync(
            (result, error) ->
                handle(result, error).onSuccess(successHandler::handle).onError(doNothing()));
    return new FutureBasedApiResponse<>(next);
  }

  @Override
  public <S> ApiResponse<S> map(Handler.SuccessMapper<? super R, ? extends S> successMapper) {
    final CompletableFuture<S> next = future.thenApply(successMapper::handle);
    return new FutureBasedApiResponse<>(next);
  }

  @Override
  public <S> ApiResponse<S> next(Handler.NextRequest<? super R, S> nextRequest) {
    final CompletableFuture<S> next = new CompletableFuture<>();
    future
        .thenApplyAsync(nextRequest::execute)
        .whenCompleteAsync(
            (response, exception) ->
                handle(response, exception)
                    .onSuccess(
                        res -> res.onSuccess(next::complete).onError(next::completeExceptionally))
                    .onError(next::completeExceptionally));
    return new FutureBasedApiResponse<>(next);
  }

  @Override
  public ApiResponse<R> onError(final Consumer<Throwable> errorHandler) {
    final CompletableFuture<R> next =
        future.whenCompleteAsync(
            (result, error) -> handle(result, error).onSuccess(doNothing()).onError(errorHandler));
    return new FutureBasedApiResponse<>(next);
  }

  @Override
  public ApiResponse<R> onFailure(Handler.FailureHandler failureHandler) {
    return onError(failureHandler::handleException);
  }

  @Override
  public R block() throws RuntimeException {
    try {
      return future.get();
    } catch (InterruptedException | ExecutionException e) {
      throw new UnknownException("error occurred, while processing", e);
    }
  }

  private static <R> OnSuccess<R> handle(final R result, final Throwable error) {
    if (error == null && result != null) {
      return resultConsumer -> errorConsumer -> resultConsumer.accept(result);
    } else if (error != null) {
      return resultConsumer -> errorConsumer -> errorConsumer.accept(error);
    } else {
      final IllegalStateException exception =
          new IllegalStateException("not success nor not error");
      return resultConsumer -> errorConsumer -> errorConsumer.accept(exception);
    }
  }

  private interface OnSuccess<R> {
    OnError onSuccess(final Consumer<? super R> resultConsumer);
  }

  private interface OnError {
    void onError(final Consumer<? super Throwable> errorConsumer);
  }
}

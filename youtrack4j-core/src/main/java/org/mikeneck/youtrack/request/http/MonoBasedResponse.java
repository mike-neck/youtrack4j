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

import org.mikeneck.youtrack.request.ApiException;
import org.mikeneck.youtrack.request.ApiResponse;
import org.mikeneck.youtrack.request.Handler;
import reactor.core.publisher.Mono;

public class MonoBasedResponse<R> implements ApiResponse<R> {

  private final Mono<R> mono;

  MonoBasedResponse(final Mono<R> mono) {
    this.mono = mono;
  }

  @Override
  public ApiResponse<R> onSuccess(final Handler.SuccessHandler<? super R> successHandler) {
    mono.subscribe(successHandler::handle);
    return this;
  }

  @Override
  public <S> ApiResponse<S> map(final Handler.SuccessMapper<? super R, ? extends S> successMapper) {
    return new MonoBasedResponse<>(mono.map(successMapper::handle));
  }

  @Override
  public <S> ApiResponse<S> next(final Handler.NextRequest<? super R, ? extends S> nextRequest) {
    final Mono<S> next =
        Mono.create(
            sink ->
                mono.map(nextRequest::handle)
                    .subscribe(
                        response ->
                            response
                                .onSuccess(sink::success)
                                .onFailure(
                                    res -> {
                                      if (res instanceof ApiException) {
                                        sink.error((ApiException) res);
                                      } else {
                                        sink.error(
                                            new ApiException(res.getStatusCode(), res.getBody(), res.headers()));
                                      }
                                    })));
    return new MonoBasedResponse<>(next);
  }

  @Override
  public ApiResponse<R> onFailure(final Handler.FailureHandler failureHandler) {
    final Mono<R> next =
        mono.doOnError(
            e -> {
              if (e instanceof ApiException) {
                failureHandler.handle((ApiException) e);
              }
            });
    return new MonoBasedResponse<>(next);
  }

  @Override
  public R block() throws RuntimeException {
    return mono.block();
  }
}

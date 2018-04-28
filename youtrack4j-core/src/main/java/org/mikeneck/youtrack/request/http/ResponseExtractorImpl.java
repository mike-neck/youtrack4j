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

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.request.ApiException;
import org.mikeneck.youtrack.request.ApiResponse;
import org.mikeneck.youtrack.request.Handler;

abstract class ResponseExtractorImpl implements ResponseExtractor {

  private final AsyncHttpClientBackedHttpClient.RequestExecutor requestExecutor;

  ResponseExtractorImpl(AsyncHttpClientBackedHttpClient.RequestExecutor requestExecutor) {
    this.requestExecutor = requestExecutor;
  }

  @NotNull
  abstract RequestBuilder initializeBuilder();

  abstract Request configure(final RequestBuilder requestBuilder);

  @NotNull
  @Override
  public <R> ApiResponse<R> executeRequest(@NotNull Handler.ResponseHandler<R> extractor) {
    final RequestBuilder requestBuilder = initializeBuilder();
    final Request request = configure(requestBuilder);

    final CompletableFuture<Response> response =
        requestExecutor.executeRequest(request).toCompletableFuture();
    final CompletableFuture<R> future =
        response
            .thenApplyAsync(AsyncHttpClientBackedHttpResponse::new)
            .thenApplyAsync(
                res -> {
                  final Optional<R> result = extractor.handle(res);
                  return result.orElseThrow(
                      () -> new ApiException(res.getStatusCode(), res.getBody(), res.headers()));
                });

    return new FutureBasedApiResponse<>(future);
  }
}

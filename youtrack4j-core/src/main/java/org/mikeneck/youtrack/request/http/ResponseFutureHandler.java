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
import java.util.function.Consumer;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.mikeneck.youtrack.request.ApiException;
import org.mikeneck.youtrack.request.Handler;
import reactor.core.publisher.MonoSink;

public class ResponseFutureHandler<R> implements Consumer<MonoSink<R>> {

  private final AsyncHttpClientBackedHttpClient.RequestExecutor requestExecutor;
  private final Handler.ResponseHandler<R> extractor;
  private final Request request;

  ResponseFutureHandler(
      final AsyncHttpClientBackedHttpClient.RequestExecutor requestExecutor,
      final Handler.ResponseHandler<R> extractor,
      final Request request) {
    this.requestExecutor = requestExecutor;
    this.extractor = extractor;
    this.request = request;
  }

  @Override
  public void accept(MonoSink<R> sink) {
    final CompletableFuture<Response> future =
        requestExecutor.executeRequest(request).toCompletableFuture();
    future.thenAccept(
        response -> {
          final AsyncHttpClientBackedHttpResponse res =
              new AsyncHttpClientBackedHttpResponse(response);
          final Optional<R> result = extractor.handle(res);
          result.ifPresent(sink::success);
          if (!result.isPresent()) {
            final ApiException exception =
                new ApiException(res.getStatusCode(), res.getBody(), res.headers());
            sink.error(exception);
          }
        });
  }
}

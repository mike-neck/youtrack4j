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
package org.mikeneck.youtrack.request;

import org.asynchttpclient.*;
import org.asynchttpclient.RequestBuilder;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.asynchttpclient.Dsl.get;

public abstract class GetRequest<R> implements ApiRequest<R> {

  private final AsyncHttpClient client;

  private final AccessToken accessToken;

  private final GetUrl getUrl;

  public GetRequest(
      final AsyncHttpClient client, final AccessToken accessToken, final GetUrl getUrl) {
    this.client = client;
    this.accessToken = accessToken;
    this.getUrl = getUrl;
  }

  protected AsyncHttpClient client() {
    return client;
  }

  protected AccessToken accessToken() {
    return accessToken;
  }

  protected GetUrl getUrl() {
    return getUrl;
  }

  protected abstract QueryParameters queryParameters();

  protected abstract Optional<R> extractResult(final Response response);

  @Override
  public ApiResponse<R> executeRequest() {
    final RequestBuilder requestBuilder =
        get(getUrl.url)
            .addHeader("Authorization", accessToken.get())
            .addHeader("Accept", "application/json");
    final Request request =
        queryParameters()
            .configureParameter(requestBuilder, RequestBuilderBase::addQueryParam)
            .build();
    final CompletableFuture<Response> future = client.executeRequest(request).toCompletableFuture();
    final Mono<R> mono =
        Mono.create(
            sink ->
                future.thenAccept(
                    response -> {
                      final Optional<R> result = extractResult(response);
                      if (!result.isPresent()) {
                        final ApiException apiException =
                            new ApiException(response.getStatusCode(), response.getResponseBody());
                        sink.error(apiException);
                      }
                      result.ifPresent(sink::success);
                    }));
    return new MonoBasedResponse<>(mono);
  }
}

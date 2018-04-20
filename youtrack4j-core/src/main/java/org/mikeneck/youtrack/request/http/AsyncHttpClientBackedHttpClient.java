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

import static org.asynchttpclient.Dsl.get;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.impl.factory.Maps;
import org.mikeneck.youtrack.request.*;
import reactor.core.publisher.Mono;

public class AsyncHttpClientBackedHttpClient implements HttpClient {

  private final AsyncHttpClient client;

  private AsyncHttpClientBackedHttpClient(final AsyncHttpClient client) {
    this.client = client;
  }

  public static HttpClient with(final AsyncHttpClient client) {
    return new AsyncHttpClientBackedHttpClient(client);
  }

  @Override
  public Get forGet(final GetUrl getUrl) {
    return new GetImpl(getUrl, Maps.immutable.empty(), Maps.immutable.empty());
  }

  @Override
  public void close() throws Exception {
    client.close();
  }

  private class GetImpl implements HttpClient.Get {

    private final GetUrl getUrl;

    private final ImmutableMap<String, String> headers;
    private final ImmutableMap<String, String> queries;

    private GetImpl(
        final GetUrl getUrl,
        final ImmutableMap<String, String> headers,
        final ImmutableMap<String, String> queries) {
      this.getUrl = getUrl;
      this.headers = headers;
      this.queries = queries;
    }

    @Override
    public Get withHeader(final String headerName, final String headerValue) {
      return new GetImpl(getUrl, headers.newWithKeyValue(headerName, headerValue), queries);
    }

    @Override
    public Get withQueryParameters(final QueryParameters queryParameters) {
      final ImmutableMap<String, String> map =
          queryParameters.configureParameters(queries, ImmutableMap::newWithKeyValue);
      return new GetImpl(getUrl, headers, map);
    }

    @Override
    public Get withQueryParameter(final String queryName, final String queryValue) {
      return new GetImpl(getUrl, headers, queries.newWithKeyValue(queryName, queryValue));
    }

    @Override
    public <R> ApiResponse<R> executeRequest(final Handler.BodyHandler<R> extractor) {
      final RequestBuilder builder = get(getUrl.url);
      final RequestBuilder headerFin =
          headers
              .keyValuesView()
              .injectInto(builder, (bld, pair) -> bld.addHeader(pair.getOne(), pair.getTwo()));
      final Request request =
          queries
              .keyValuesView()
              .injectInto(headerFin, (bld, pair) -> bld.addQueryParam(pair.getOne(), pair.getTwo()))
              .build();
      final Mono<R> mono =
          Mono.create(
              sink -> {
                final CompletableFuture<Response> future =
                    client.executeRequest(request).toCompletableFuture();
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
              });
      return new MonoBasedResponse<>(mono);
    }
  }
}

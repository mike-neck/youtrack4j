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

import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.eclipse.collections.api.map.ImmutableMap;
import org.jetbrains.annotations.NotNull;

class GetImpl extends ResponseExtractorImpl
    implements HttpClient.HeaderConfigurer<HttpClient.Get>, HttpClient.Get {

  private final GetUrl getUrl;

  private final AsyncHttpClientBackedHttpClient.RequestExecutor requestExecutor;

  private final ImmutableMap<String, String> headers;
  private final ImmutableMap<String, String> queries;

  GetImpl(
      final GetUrl getUrl,
      AsyncHttpClientBackedHttpClient.RequestExecutor requestExecutor,
      final ImmutableMap<String, String> headers,
      final ImmutableMap<String, String> queries) {
    super(requestExecutor);
    this.getUrl = getUrl;
    this.requestExecutor = requestExecutor;
    this.headers = headers;
    this.queries = queries;
  }

  @Override
  public HttpClient.Get withHeader(final String headerName, final String headerValue) {
    return new GetImpl(
        getUrl, requestExecutor, headers.newWithKeyValue(headerName, headerValue), queries);
  }

  @Override
  public HttpClient.Get withQueryParameters(final QueryParameters queryParameters) {
    final ImmutableMap<String, String> map =
        queryParameters.configureParameters(queries, ImmutableMap::newWithKeyValue);
    return new GetImpl(getUrl, requestExecutor, headers, map);
  }

  @NotNull
  @Override
  RequestBuilder initializeBuilder() {
    return get(getUrl.url);
  }

  @Override
  Request configure(RequestBuilder requestBuilder) {
    final RequestBuilder headerFin =
        headers
            .keyValuesView()
            .injectInto(requestBuilder, (bld, pair) -> bld.addHeader(pair.getOne(), pair.getTwo()));
    return queries
        .keyValuesView()
        .injectInto(headerFin, (bld, pair) -> bld.addQueryParam(pair.getOne(), pair.getTwo()))
        .build();
  }
}

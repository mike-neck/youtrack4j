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

import static org.asynchttpclient.Dsl.post;

import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.Multimaps;
import org.jetbrains.annotations.NotNull;

class PostFormImpl extends ResponseExtractorImpl
    implements HttpClient.HeaderConfigurer<HttpClient.PostForm>, HttpClient.PostForm {

  private final AsyncHttpClientBackedHttpClient.RequestExecutor requestExecutor;
  private final PostUrl postUrl;
  private final ImmutableMap<String, String> headers;
  private final ImmutableListMultimap<String, String> formData;

  PostFormImpl(
      final PostUrl postUrl,
      final AsyncHttpClientBackedHttpClient.RequestExecutor requestExecutor) {
    this(postUrl, requestExecutor, Maps.immutable.empty(), null);
  }

  private PostFormImpl(
      PostUrl postUrl,
      AsyncHttpClientBackedHttpClient.RequestExecutor requestExecutor,
      ImmutableMap<String, String> headers,
      ImmutableListMultimap<String, String> formData) {
    super(requestExecutor);
    this.postUrl = postUrl;
    this.requestExecutor = requestExecutor;
    this.headers = headers;
    this.formData = formData;
  }

  @Override
  public HttpClient.PostForm withHeader(String headerName, String headerValue) {
    return new PostFormImpl(
        postUrl, requestExecutor, headers.newWithKeyValue(headerName, headerValue), formData);
  }

  @Override
  public HttpClient.PostForm withForms(FormData forms) {
    final ImmutableListMultimap<String, String> newMap =
        forms
            .configureParameters(
                Multimaps.mutable.list.<String, String>empty(),
                (map, key, list) -> {
                  map.putAll(key, list);
                  return map;
                })
            .toImmutable();
    return new PostFormImpl(postUrl, requestExecutor, headers, newMap);
  }

  @NotNull
  @Override
  RequestBuilder initializeBuilder() {
    return post(postUrl.url);
  }

  @Override
  Request configure(RequestBuilder requestBuilder) {
    final RequestBuilder headerFin =
        headers
            .keyValuesView()
            .injectInto(requestBuilder, (bld, pair) -> bld.addHeader(pair.getOne(), pair.getTwo()));
    return headerFin
        .setFormParams(
            formData.toMap().collectValues((k, list) -> Lists.immutable.ofAll(list).castToList()))
        .build();
  }
}

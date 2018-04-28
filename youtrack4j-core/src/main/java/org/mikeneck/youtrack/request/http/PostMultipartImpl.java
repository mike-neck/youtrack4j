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
import org.asynchttpclient.request.body.multipart.Part;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mikeneck.youtrack.request.MultipartData;
import org.mikeneck.youtrack.request.MultipartEntry;

class PostMultipartImpl extends ResponseExtractorImpl
    implements HttpClient.HeaderConfigurer<HttpClient.PostMultipart>, HttpClient.PostMultipart {

  private final PostUrl postUrl;
  private final AsyncHttpClientBackedHttpClient.RequestExecutor executeRequest;
  private final ImmutableMap<String, String> headers;
  @Nullable private final ImmutableList<Part> multipartData;

  PostMultipartImpl(
      PostUrl postUrl, AsyncHttpClientBackedHttpClient.RequestExecutor executeRequest) {
    this(postUrl, executeRequest, Maps.immutable.empty(), null);
  }

  private PostMultipartImpl(
      PostUrl postUrl,
      AsyncHttpClientBackedHttpClient.RequestExecutor executeRequest,
      final ImmutableMap<String, String> headers,
      @Nullable final ImmutableList<Part> multipartData) {
    super(executeRequest);
    this.postUrl = postUrl;
    this.executeRequest = executeRequest;
    this.headers = headers;
    this.multipartData = multipartData;
  }

  @Override
  public HttpClient.PostMultipart withHeader(String headerName, String headerValue) {
    return new PostMultipartImpl(
        postUrl, executeRequest, headers.newWithKeyValue(headerName, headerValue), multipartData);
  }

  @Override
  public HttpClient.PostMultipart withMultiparts(MultipartData multiparts) {
    final ImmutableList<Part> newMap =
        multiparts
            .configureParameters(
                Lists.mutable.<Part>empty(),
                (list, key, parts) -> {
                  final ImmutableList<Part> ps =
                      Lists.immutable
                          .ofAll(parts)
                          .collect(MultipartEntry::part)
                          .collect(f -> f.apply(key));
                  list.addAll(ps.castToList());
                  return list;
                })
            .toImmutable();
    return new PostMultipartImpl(postUrl, executeRequest, headers, newMap);
  }

  @NotNull
  @Override
  RequestBuilder initializeBuilder() {
    return post(postUrl.url);
  }

  private Request part(final RequestBuilder requestBuilder) {
    if (multipartData != null) {
      return requestBuilder.setBodyParts(multipartData.castToList()).build();
    }
    return requestBuilder.build();
  }

  @Override
  Request configure(RequestBuilder requestBuilder) {
    final RequestBuilder headerFin =
        headers
            .keyValuesView()
            .injectInto(requestBuilder, (bld, pair) -> bld.addHeader(pair.getOne(), pair.getTwo()));
    return part(headerFin);
  }
}

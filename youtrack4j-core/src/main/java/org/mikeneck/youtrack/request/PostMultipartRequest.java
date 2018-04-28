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

import org.mikeneck.youtrack.request.http.HttpClient;

public abstract class PostMultipartRequest<R> extends PostRequest<R, HttpClient.PostMultipart> {

  protected PostMultipartRequest(PostRequestContext context) {
    super(context);
  }

  protected abstract MultipartData multipartData();

  @Override
  HttpClient.HeaderConfigurer<HttpClient.PostMultipart> post(HttpClient client) {
    return client.forPostMultipart(postUrl());
  }

  @Override
  ApiResponse<R> execute(HttpClient.PostMultipart post) {
    return post.withMultiparts(multipartData()).executeRequest(this::extractResult);
  }

  @Override
  PostContentType contentType() {
    return PostContentType.MULTIPART_FORM_DATA;
  }
}

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

import org.mikeneck.youtrack.request.http.FormData;
import org.mikeneck.youtrack.request.http.HttpClient;

public abstract class PostFormRequest<R> extends PostRequest<R, HttpClient.PostForm> {

  public PostFormRequest(PostRequestContext context) {
    super(context);
  }

  protected abstract FormData formData();

  @Override
  HttpClient.HeaderConfigurer<HttpClient.PostForm> post(HttpClient client) {
    return client.forPostForm(postUrl());
  }

  @Override
  ApiResponse<R> execute(HttpClient.PostForm post) {
    return post.withForms(formData()).executeRequest(this::extractResult);
  }

  @Override
  PostContentType contentType() {
    return PostContentType.FORM_URLENCODED;
  }
}

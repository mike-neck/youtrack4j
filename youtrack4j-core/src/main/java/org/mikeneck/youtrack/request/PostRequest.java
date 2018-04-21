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
import org.mikeneck.youtrack.request.http.HttpResponse;
import org.mikeneck.youtrack.request.http.PostUrl;

import java.util.Optional;

public abstract class PostRequest<R> implements ApiRequest<R> {

  private final HttpClient client;

  private final AccessToken accessToken;

  private final PostUrl postUrl;

  PostRequest(HttpClient client, AccessToken accessToken, PostUrl postUrl) {
    this.client = client;
    this.accessToken = accessToken;
    this.postUrl = postUrl;
  }

  protected HttpClient client() {
    return client;
  }

  protected AccessToken accessToken() {
    return accessToken;
  }

  protected PostUrl postUrl() {
    return postUrl;
  }

  abstract PostContentType contentType();

  protected abstract Optional<R> extractResult(final HttpResponse response);

  protected enum PostContentType {
    FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data");

    private final String contentType;

    PostContentType(String contentType) {
      this.contentType = contentType;
    }
  }
}

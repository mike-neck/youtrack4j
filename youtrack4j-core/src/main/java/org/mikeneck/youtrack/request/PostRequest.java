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

import java.util.Optional;
import org.mikeneck.youtrack.request.http.HttpClient;
import org.mikeneck.youtrack.request.http.HttpResponse;
import org.mikeneck.youtrack.request.http.PostUrl;

public abstract class PostRequest<R, P extends HttpClient.Post<P> & HttpClient.HeaderConfigurer<P>>
    implements ApiRequest<R> {

  private final PostRequestContext context;

  PostRequest(final PostRequestContext context) {
    this.context = context;
  }

  protected AccessToken accessToken() {
    return context.accessToken;
  }

  PostUrl postUrl() {
    return context.postUrl;
  }

  abstract PostContentType contentType();

  abstract Optional<R> extractResult(final HttpResponse response);

  abstract HttpClient.HeaderConfigurer<P> post(final HttpClient client);

  abstract ApiResponse<R> execute(final P post);

  @Override
  public ApiResponse<R> executeRequest() {
    final P post = post(context.client).withAccessToken(accessToken()).acceptJson();
    return execute(post);
  }

  protected enum PostContentType {
    FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data");

    private final String contentType;

    PostContentType(String contentType) {
      this.contentType = contentType;
    }
  }
}

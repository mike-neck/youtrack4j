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

import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.mikeneck.youtrack.request.AccessToken;
import org.mikeneck.youtrack.request.MultipartData;

public interface HttpClient extends AutoCloseable {

  HeaderConfigurer<Get> forGet(final GetUrl getUrl);

  HeaderConfigurer<PostForm> forPostForm(final PostUrl postUrl);

  HeaderConfigurer<PostMultipart> forPostMultipart(final PostUrl postUrl);

  interface RequestBuilderInitializer {
    RequestBuilder initializeBuilder();
  }

  interface RequestConfigurer {
    Request configure(final RequestBuilder builder);
  }

  interface HeaderConfigurer<M extends HeaderConfigurer<M> & ResponseExtractor> {

    default M withAccessToken(final AccessToken accessToken) {
      return withHeader(AccessToken.AUTHORIZATION_HEADER, accessToken.bearer());
    }

    default M withContentType(final String contentType) {
      return withHeader("Content-Type", contentType);
    }

    default M acceptJson() {
      return withHeader("Accept", "application/json");
    }

    M withHeader(final String headerName, final String headerValue);
  }

  interface Get extends HeaderConfigurer<Get>, ResponseExtractor {

    ResponseExtractor withQueryParameters(final QueryParameters queryParameters);
  }

  interface Post<P extends Post<P>> extends ResponseExtractor {}

  interface PostForm extends HeaderConfigurer<PostForm>, Post<PostForm> {

    PostForm withForms(final FormData forms);
  }

  interface PostMultipart extends HeaderConfigurer<PostMultipart>, Post<PostMultipart> {

    PostMultipart withMultiparts(final MultipartData multiparts);
  }
}

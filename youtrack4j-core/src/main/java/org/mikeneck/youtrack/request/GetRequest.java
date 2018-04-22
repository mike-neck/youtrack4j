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
import org.mikeneck.youtrack.request.http.GetUrl;
import org.mikeneck.youtrack.request.http.HttpClient;
import org.mikeneck.youtrack.request.http.HttpResponse;
import org.mikeneck.youtrack.request.http.QueryParameters;

public abstract class GetRequest<R> implements ApiRequest<R> {

  private final HttpClient client;

  private final AccessToken accessToken;

  private final GetUrl getUrl;

  public GetRequest(final HttpClient client, final AccessToken accessToken, final GetUrl getUrl) {
    this.client = client;
    this.accessToken = accessToken;
    this.getUrl = getUrl;
  }

  protected HttpClient client() {
    return client;
  }

  protected AccessToken accessToken() {
    return accessToken;
  }

  protected GetUrl getUrl() {
    return getUrl;
  }

  protected abstract QueryParameters queryParameters();

  protected abstract Optional<R> extractResult(final HttpResponse response);

  @Override
  public ApiResponse<R> executeRequest() {
    return client
        .forGet(getUrl)
        .withAccessToken(accessToken)
        .acceptJson()
        .withQueryParameters(queryParameters())
        .executeRequest(this::extractResult);
  }
}

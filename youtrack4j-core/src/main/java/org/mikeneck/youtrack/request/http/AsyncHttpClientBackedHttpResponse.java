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

import java.nio.charset.StandardCharsets;
import org.asynchttpclient.Response;

public class AsyncHttpClientBackedHttpResponse implements HttpResponse {

  private final Response response;

  public AsyncHttpClientBackedHttpResponse(final Response response) {
    this.response = response;
  }

  @Override
  public int getStatusCode() {
    return response.getStatusCode();
  }

  @Override
  public String getBody() {
    return response.getResponseBody(StandardCharsets.UTF_8);
  }

  @Override
  public Iterable<String> header(final String headerName) {
    return response.getHeaders(headerName);
  }
}

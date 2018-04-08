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

import org.mikeneck.youtrack.request.http.FailureResponse;

public class ApiException extends RuntimeException implements FailureResponse {

  private static final long serialVersionUID = -370990231766752L;

  private final int statusCode;
  private final String responseBody;

  public ApiException(final int statusCode, final String responseBody) {
    super(String.format("%d: %s", statusCode, responseBody));
    this.statusCode = statusCode;
    this.responseBody = responseBody;
  }

  @Override
  public int getStatusCode() {
    return statusCode;
  }

  @Override
  public String getBody() {
    return responseBody;
  }
}

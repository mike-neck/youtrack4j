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

import org.mikeneck.youtrack.request.ApiResponse;
import org.mikeneck.youtrack.request.Handler;

public interface HttpClient {

  Get forGet(final GetUrl getUrl);

  interface Get {

    Get withHeader(final String headerName, final String headerValue);

    Get withQueryParameters(final QueryParameters queryParameters);

    Get withQueryParameter(final String queryName, final String queryValue);

    <R> ApiResponse<R> executeRequest(final Handler.BodyHandler<R> extractor);
  }
}

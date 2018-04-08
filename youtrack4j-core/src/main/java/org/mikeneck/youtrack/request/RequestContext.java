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

import org.asynchttpclient.AsyncHttpClient;

public class RequestContext {

    private final AsyncHttpClient client;
    private final AccessToken accessToken;
    private final BaseUrl baseUrl;

    public RequestContext(final AsyncHttpClient client, final AccessToken accessToken, final BaseUrl baseUrl) {
        this.client = client;
        this.accessToken = accessToken;
        this.baseUrl = baseUrl;
    }
}

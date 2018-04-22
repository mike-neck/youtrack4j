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

import org.asynchttpclient.*;
import org.eclipse.collections.impl.factory.Maps;

public class AsyncHttpClientBackedHttpClient implements HttpClient {

  private final AsyncHttpClient client;

  private AsyncHttpClientBackedHttpClient(final AsyncHttpClient client) {
    this.client = client;
  }

  public static HttpClient with(final AsyncHttpClient client) {
    return new AsyncHttpClientBackedHttpClient(client);
  }

  @Override
  public HeaderConfigurer<Get> forGet(final GetUrl getUrl) {
    return new GetImpl(
        getUrl, client::executeRequest, Maps.immutable.empty(), Maps.immutable.empty());
  }

  @Override
  public HeaderConfigurer<PostForm> forPostForm(PostUrl postUrl) {
    return new PostFormImpl(postUrl, client::executeRequest);
  }

  @Override
  public HeaderConfigurer<PostMultipart> forPostMultipart(PostUrl postUrl) {
    return new PostMultipartImpl(postUrl, client::executeRequest);
  }

  @Override
  public void close() throws Exception {
    client.close();
  }

  interface RequestExecutor {
    ListenableFuture<Response> executeRequest(final Request request);
  }
}

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
package org.mikeneck.youtrack.project;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.mikeneck.youtrack.request.*;
import org.mikeneck.youtrack.util.Codec;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public interface GetAccessibleProjects extends ApiRequest<List<YouTrackProject>> {

  ApiRequest<List<YouTrackProject>> verbose();

  class NoVerbose extends GetRequest<List<YouTrackProject>> implements GetAccessibleProjects {

    NoVerbose(final AsyncHttpClient client, final AccessToken accessToken, final GetUrl getUrl) {
      super(client, accessToken, getUrl);
    }

    @Override
    public ApiRequest<List<YouTrackProject>> verbose() {
      return new Verbose(client(), accessToken(), getUrl());
    }

    @Override
    protected QueryParameters queryParameters() {
      return QueryParameters.queryKey("verbose").value(false);
    }

    @Override
    protected Optional<List<YouTrackProject>> extractResult(final Response response) {
      if (response.getStatusCode() != 200) {
        return Optional.empty();
      }
      final String body = response.getResponseBody(StandardCharsets.UTF_8);
      final List<YouTrackProject> youTrackProjects =
          Codec.instance()
              .deserialize(new Codec.TypeRef<List<ShortYouTrackProject.Json>>() {}, body);
      return Optional.of(youTrackProjects);
    }
  }

  class Verbose extends GetRequest<List<YouTrackProject>> {

    Verbose(final AsyncHttpClient client, final AccessToken accessToken, final GetUrl getUrl) {
      super(client, accessToken, getUrl);
    }

    @Override
    protected QueryParameters queryParameters() {
      return QueryParameters.queryKey("verbose").value(true);
    }

    @Override
    protected Optional<List<YouTrackProject>> extractResult(final Response response) {
      if (response.getStatusCode() != 200) {
        return Optional.empty();
      }
      final String body = response.getResponseBody(StandardCharsets.UTF_8);
      final List<YouTrackProject> youTrackProjects =
          Codec.instance()
              .deserialize(new Codec.TypeRef<List<LongYouTrackProject.Json>>() {}, body);
      return Optional.of(youTrackProjects);
    }
  }
}

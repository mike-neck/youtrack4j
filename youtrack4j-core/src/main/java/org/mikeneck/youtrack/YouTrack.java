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
package org.mikeneck.youtrack;

import org.asynchttpclient.AsyncHttpClient;
import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.project.GetAccessibleProjects;

import static org.asynchttpclient.Dsl.asyncHttpClient;

public final class YouTrack {

  private final AsyncHttpClient client;

  private final YouTrackConfiguration configuration;

  private YouTrack(
      @NotNull final AsyncHttpClient client, @NotNull final YouTrackConfiguration configuration) {
    this.client = client;
    this.configuration = configuration;
  }

  private YouTrack() {
    this(asyncHttpClient(), YouTrackConfiguration.load());
  }

  @NotNull
  public static YouTrack getInstance() {
    return new YouTrack();
  }

  @NotNull
  public GetAccessibleProjects getAccessibleProjects() {
    return null;
  }
}
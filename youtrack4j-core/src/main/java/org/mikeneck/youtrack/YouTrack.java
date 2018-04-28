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

import static org.asynchttpclient.Dsl.asyncHttpClient;

import org.asynchttpclient.AsyncHttpClient;
import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.api.issue.create.CreateNewIssue;
import org.mikeneck.youtrack.api.project.GetAccessibleProjects;
import org.mikeneck.youtrack.api.project.YouTrackProject;
import org.mikeneck.youtrack.request.RequestContext;
import org.mikeneck.youtrack.request.http.AsyncHttpClientBackedHttpClient;
import org.mikeneck.youtrack.request.http.HttpClient;

public final class YouTrack implements AutoCloseable {

  private final RequestContext context;

  private YouTrack(
      @NotNull final HttpClient client, @NotNull final YouTrackConfiguration configuration) {
    this.context =
        new RequestContext(client, configuration.getAccessToken(), configuration.getBaseUrl());
  }

  private YouTrack(
      @NotNull final AsyncHttpClient client, @NotNull final YouTrackConfiguration configuration) {
    this(AsyncHttpClientBackedHttpClient.with(client), configuration);
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
    return GetAccessibleProjects.noVerbose(context);
  }

  @NotNull
  public CreateNewIssue.Builder createNewIssueInProject(final YouTrackProject project) {
    return CreateNewIssue.newIssueBuilder(context, project);
  }

  @NotNull
  public CreateNewIssue.Builder createNewIssueInProject(final String projectId) {
    return CreateNewIssue.newIssueBuilder(context, projectId);
  }

  @Override
  public void close() throws Exception {
    context.close();
  }
}

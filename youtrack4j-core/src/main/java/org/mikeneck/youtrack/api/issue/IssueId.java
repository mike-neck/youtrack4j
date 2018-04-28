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
package org.mikeneck.youtrack.api.issue;

import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.api.project.ProjectId;

public interface IssueId {

  ProjectId projectId();

  int number();

  String issueId();

  @NotNull
  static IssueId fromUrl(@NotNull final String url) {
    return new IssueIdByLocation(url);
  }

  @NotNull
  static IssueId fromId(@NotNull String issueId) {
    return new IssueIdByString(issueId);
  }

  default boolean isSameIssue(final IssueId other) {
    return projectId().equals(other.projectId()) && number() == other.number();
  }
}

class IssueIdByLocation implements IssueId {
  private final String resourceUri;

  IssueIdByLocation(String resourceUri) {
    this.resourceUri = resourceUri;
  }

  @Override
  public ProjectId projectId() {
    final String issueId = issueId();
    final int hyphen = issueId.lastIndexOf('-');
    final String projectId = issueId.substring(0, hyphen);
    return ProjectId.of(projectId);
  }

  private int hyphen() {
    return resourceUri.lastIndexOf('-');
  }

  @Override
  public int number() {
    final int hyphen = hyphen();
    final String number = resourceUri.substring(hyphen + 1);
    return Integer.parseInt(number);
  }

  @Override
  public String issueId() {
    final int startIndex = resourceUri.lastIndexOf('/');
    return resourceUri.substring(startIndex + 1, resourceUri.length());
  }

  @Override
  public String toString() {
    return "IssueId[id: " + issueId() + ", url: " + resourceUri + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IssueIdByLocation)) return false;

    IssueIdByLocation that = (IssueIdByLocation) o;

    return resourceUri.equals(that.resourceUri);
  }

  @Override
  public int hashCode() {
    return resourceUri.hashCode();
  }
}

class IssueIdByString implements IssueId {

  private final String id;

  IssueIdByString(String id) {
    if (!id.contains("-")) {
      throw new IllegalArgumentException(
          String.format("The issue id would contains '-'. input: %s", id));
    }
    this.id = id;
  }

  @Override
  public ProjectId projectId() {
    final String project = id.split("-")[0];
    return ProjectId.of(project);
  }

  @Override
  public int number() {
    final String number = id.split("-")[1];
    return Integer.parseInt(number);
  }

  @Override
  public String issueId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IssueIdByString)) return false;

    IssueIdByString that = (IssueIdByString) o;

    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}

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
package org.mikeneck.youtrack.api.issue.create;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eclipse.collections.impl.factory.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mikeneck.youtrack.api.group.Group;
import org.mikeneck.youtrack.api.issue.Issue;
import org.mikeneck.youtrack.api.project.ProjectId;
import org.mikeneck.youtrack.api.project.YouTrackProject;
import org.mikeneck.youtrack.request.*;
import org.mikeneck.youtrack.request.http.FormData;
import org.mikeneck.youtrack.request.http.HttpResponse;
import org.mikeneck.youtrack.util.Codec;

public interface CreateNewIssue extends ApiRequest<Issue> {

  static Builder newIssueBuilder(
      @NotNull final RequestContext context, @NotNull final YouTrackProject project) {
    return newIssueBuilder(context, project.projectId());
  }

  static Builder newIssueBuilder(@NotNull final RequestContext context, @NotNull String projectId) {
    final ProjectId id = ProjectId.of(projectId);
    return newIssueBuilder(context, id);
  }

  static Builder newIssueBuilder(
      @NotNull final RequestContext context, @NotNull final ProjectId projectId) {
    return summary -> new CreateNewIssueForm(context.post("/issue"), projectId, summary);
  }

  interface Builder {
    Simple summary(@NotNull final String summary);
  }

  interface Simple extends WithDescription {
    WithDescription description(@NotNull final String description);
  }

  interface WithDescription extends WithAttachments {
    WithAttachments attachments(@NotNull final Path... files);
  }

  interface WithAttachments extends CreateNewIssue {
    CreateNewIssue permittedTo(@NotNull final Group permittedGroup);
  }

  default Optional<Issue> defaultExtractor(final HttpResponse response) {
    if (response.getStatusCode() == 200) {
      final Issue.Json issueJson =
          Codec.instance().deserialize(Issue.Json.class, response.getBody());
      final Issue issue = issueJson.immutable();
      return Optional.of(issue);
    } else {
      return Optional.empty();
    }
  }
}

class CreateNewIssueForm extends PostFormRequest<Issue> implements CreateNewIssue.Simple {

  private final PostRequestContext context;

  private final ProjectId projectId;
  private final String summary;

  @Nullable private final String description;

  @Nullable private final Group group;

  CreateNewIssueForm(
      @NotNull final PostRequestContext context,
      @NotNull final ProjectId projectId,
      @NotNull final String summary) {
    this(context, projectId, summary, null, null);
  }

  private CreateNewIssueForm(
      @NotNull final PostRequestContext context,
      @NotNull final ProjectId projectId,
      @NotNull final String summary,
      @Nullable final String description) {
    this(context, projectId, summary, description, null);
  }

  private CreateNewIssueForm(
      @NotNull final PostRequestContext context,
      @NotNull final ProjectId projectId,
      @NotNull final String summary,
      @NotNull final Group group) {
    this(context, projectId, summary, null, group);
  }

  private CreateNewIssueForm(
      @NotNull final PostRequestContext context,
      @NotNull final ProjectId projectId,
      @NotNull final String summary,
      @Nullable final String description,
      @Nullable final Group group) {
    super(context);
    this.context = context;
    this.projectId = projectId;
    this.summary = summary;
    this.description = description;
    this.group = group;
  }

  @Override
  public WithDescription description(@NotNull String description) {
    return new CreateNewIssueForm(context, projectId, summary, description);
  }

  @Override
  public WithAttachments attachments(@NotNull Path... files) {
    return new CreateNewIssueMultipart(
        context,
        projectId,
        summary,
        description,
        Lists.immutable.of(files).castToCollection(),
        group);
  }

  @Override
  public CreateNewIssue permittedTo(@NotNull Group permittedGroup) {
    throw new UnsupportedOperationException("permitted not implemented.");
    //    return new CreateNewIssueForm(context, projectId, summary, permittedGroup);
  }

  @Override
  protected FormData formData() {
    return FormData.empty()
        .form("project")
        .value(projectId.getValue())
        .form("summary")
        .value(summary)
        .form("description")
        .value(
            description == null ? Collections.emptyList() : Collections.singletonList(description));
  }

  @Override
  public Optional<Issue> extractResult(HttpResponse response) {
    return defaultExtractor(response);
  }
}

class CreateNewIssueMultipart extends PostMultipartRequest<Issue>
    implements CreateNewIssue.WithAttachments {
  private final PostRequestContext context;

  @NotNull private final ProjectId projectId;
  @NotNull private final String summary;

  @Nullable private final String description;

  @NotNull private final Collection<Path> files;

  @Nullable private final Group group;

  CreateNewIssueMultipart(
      @NotNull final PostRequestContext context,
      @NotNull final ProjectId projectId,
      @NotNull final String summary,
      @Nullable final String description,
      @NotNull final Collection<Path> files,
      @Nullable final Group group) {
    super(context);
    this.context = context;
    this.projectId = projectId;
    this.summary = summary;
    this.description = description;
    this.files = files;
    this.group = group;
  }

  @Override
  public CreateNewIssue permittedTo(@NotNull Group permittedGroup) {
    return new CreateNewIssueMultipart(
        context, projectId, summary, description, files, permittedGroup);
  }

  @Override
  protected MultipartData multipartData() {
    final List<MultipartEntry> entries =
        files.stream().map(MultipartEntry::file).collect(Collectors.toList());
    return MultipartData.empty()
        .form("project")
        .value(projectId.getValue())
        .form("summary")
        .value(summary)
        .form("description")
        .values(
            description == null
                ? Collections.emptyList()
                : Collections.singleton(MultipartEntry.string(description)))
        .form("attachments")
        .values(entries);
  }

  @Override
  public Optional<Issue> extractResult(HttpResponse response) {
    return defaultExtractor(response);
  }
}

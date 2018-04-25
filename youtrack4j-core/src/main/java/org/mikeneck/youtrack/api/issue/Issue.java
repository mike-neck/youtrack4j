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

import java.time.*;
import java.util.Date;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mikeneck.youtrack.util.JsonForm;

public final class Issue {

  private final String priority;
  private final String type;
  private final String state;
  private final String subsystem;
  private final String id;
  private final String projectShortName;
  private final String assigneeName;
  private final String reporterName;
  private final String updaterName;
  private final String fixedInBuild;
  private final int commentsCount;
  private final int numberInProject;
  private final String summary;
  private final String description;
  private final Long created;
  private final Long updated;
  private final Long historyUpdated;
  private final int votes;

  public Issue(
      String priority,
      String type,
      String state,
      String subsystem,
      String id,
      String projectShortName,
      String assigneeName,
      String reporterName,
      String updaterName,
      String fixedInBuild,
      int commentsCount,
      int numberInProject,
      String summary,
      String description,
      Long created,
      Long updated,
      Long historyUpdated,
      int votes) {
    this.priority = priority;
    this.type = type;
    this.state = state;
    this.subsystem = subsystem;
    this.id = id;
    this.projectShortName = projectShortName;
    this.assigneeName = assigneeName;
    this.reporterName = reporterName;
    this.updaterName = updaterName;
    this.fixedInBuild = fixedInBuild;
    this.commentsCount = commentsCount;
    this.numberInProject = numberInProject;
    this.summary = summary;
    this.description = description;
    this.created = created;
    this.updated = updated;
    this.historyUpdated = historyUpdated;
    this.votes = votes;
  }

  public String getPriority() {
    return priority;
  }

  public String getType() {
    return type;
  }

  public String getState() {
    return state;
  }

  public String getSubsystem() {
    return subsystem;
  }

  public IssueId getId() {
    return IssueId.fromId(id);
  }

  public String getProjectShortName() {
    return projectShortName;
  }

  public String getAssigneeName() {
    return assigneeName;
  }

  public String getReporterName() {
    return reporterName;
  }

  public String getUpdaterName() {
    return updaterName;
  }

  public String getFixedInBuild() {
    return fixedInBuild;
  }

  public int getCommentsCount() {
    return commentsCount;
  }

  public int getNumberInProject() {
    return numberInProject;
  }

  public String getSummary() {
    return summary;
  }

  public String getDescription() {
    return description;
  }

  @Nullable
  private Date returnNullIfNullOrCreateDate(@Nullable Long created) {
    if (created == null) {
      return null;
    }
    return new Date(created);
  }

  @Nullable
  public Date getCreated() {
    return returnNullIfNullOrCreateDate(created);
  }

  public Date getUpdated() {
    return returnNullIfNullOrCreateDate(updated);
  }

  public Date getHistoryUpdated() {
    return returnNullIfNullOrCreateDate(historyUpdated);
  }

  @Nullable
  public OffsetDateTime getCreatedAtOffset(@NotNull final ZoneOffset offset) {
    final Date date = getCreated();
    return returnNullIfNullOrToOffsetDateTime(offset, date);
  }

  private OffsetDateTime returnNullIfNullOrToOffsetDateTime(
      @NotNull final ZoneOffset offset, @Nullable final Date date) {
    if (date == null) {
      return null;
    }
    return date.toInstant().atOffset(offset);
  }

  public OffsetDateTime getUpdatedAtOffset(final ZoneOffset offset) {
    final Date date = getUpdated();
    return returnNullIfNullOrToOffsetDateTime(offset, date);
  }

  public OffsetDateTime getHistoryUpdatedAtOffset(final ZoneOffset offset) {
    final Date date = getHistoryUpdated();
    return returnNullIfNullOrToOffsetDateTime(offset, date);
  }

  public int getVotes() {
    return votes;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Issue{");
    sb.append("priority='").append(priority).append('\'');
    sb.append(", type='").append(type).append('\'');
    sb.append(", state='").append(state).append('\'');
    sb.append(", subsystem='").append(subsystem).append('\'');
    sb.append(", id='").append(id).append('\'');
    sb.append(", projectShortName='").append(projectShortName).append('\'');
    sb.append(", assigneeName='").append(assigneeName).append('\'');
    sb.append(", reporterName='").append(reporterName).append('\'');
    sb.append(", updaterName='").append(updaterName).append('\'');
    sb.append(", fixedInBuild='").append(fixedInBuild).append('\'');
    sb.append(", commentsCount=").append(commentsCount);
    sb.append(", numberInProject=").append(numberInProject);
    sb.append(", summary='").append(summary).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", created=").append(created);
    sb.append(", updated=").append(updated);
    sb.append(", historyUpdated=").append(historyUpdated);
    sb.append(", votes=").append(votes);
    sb.append('}');
    return sb.toString();
  }

  public static class Json implements JsonForm<Issue> {
    private String priority;
    private String type;
    private String state;
    private String subsystem;
    private String id;
    private String projectShortName;
    private String assigneeName;
    private String reporterName;
    private String updaterName;
    private String fixedInBuild;
    private int commentsCount;
    private int numberInProject;
    private String summary;
    private String description;
    private Long created;
    private Long updated;
    private Long historyUpdated;
    private int votes;

    @Override
    public Issue immutable() {
      return new Issue(
          priority,
          type,
          state,
          subsystem,
          id,
          projectShortName,
          assigneeName,
          reporterName,
          updaterName,
          fixedInBuild,
          commentsCount,
          numberInProject,
          summary,
          description,
          created,
          updated,
          historyUpdated,
          votes);
    }

    public Json(
        String priority,
        String type,
        String state,
        String subsystem,
        String id,
        String projectShortName,
        String assigneeName,
        String reporterName,
        String updaterName,
        String fixedInBuild,
        int commentsCount,
        int numberInProject,
        String summary,
        String description,
        Long created,
        Long updated,
        Long historyUpdated,
        int votes) {
      this.priority = priority;
      this.type = type;
      this.state = state;
      this.subsystem = subsystem;
      this.id = id;
      this.projectShortName = projectShortName;
      this.assigneeName = assigneeName;
      this.reporterName = reporterName;
      this.updaterName = updaterName;
      this.fixedInBuild = fixedInBuild;
      this.commentsCount = commentsCount;
      this.numberInProject = numberInProject;
      this.summary = summary;
      this.description = description;
      this.created = created;
      this.updated = updated;
      this.historyUpdated = historyUpdated;
      this.votes = votes;
    }

    public Json() {}

    public String getPriority() {
      return priority;
    }

    public void setPriority(String priority) {
      this.priority = priority;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

    public String getSubsystem() {
      return subsystem;
    }

    public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getProjectShortName() {
      return projectShortName;
    }

    public void setProjectShortName(String projectShortName) {
      this.projectShortName = projectShortName;
    }

    public String getAssigneeName() {
      return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
      this.assigneeName = assigneeName;
    }

    public String getReporterName() {
      return reporterName;
    }

    public void setReporterName(String reporterName) {
      this.reporterName = reporterName;
    }

    public String getUpdaterName() {
      return updaterName;
    }

    public void setUpdaterName(String updaterName) {
      this.updaterName = updaterName;
    }

    public String getFixedInBuild() {
      return fixedInBuild;
    }

    public void setFixedInBuild(String fixedInBuild) {
      this.fixedInBuild = fixedInBuild;
    }

    public int getCommentsCount() {
      return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
      this.commentsCount = commentsCount;
    }

    public int getNumberInProject() {
      return numberInProject;
    }

    public void setNumberInProject(int numberInProject) {
      this.numberInProject = numberInProject;
    }

    public String getSummary() {
      return summary;
    }

    public void setSummary(String summary) {
      this.summary = summary;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public Long getCreated() {
      return created;
    }

    public void setCreated(Long created) {
      this.created = created;
    }

    public Long getUpdated() {
      return updated;
    }

    public void setUpdated(Long updated) {
      this.updated = updated;
    }

    public Long getHistoryUpdated() {
      return historyUpdated;
    }

    public void setHistoryUpdated(Long historyUpdated) {
      this.historyUpdated = historyUpdated;
    }

    public int getVotes() {
      return votes;
    }

    public void setVotes(int votes) {
      this.votes = votes;
    }
  }
}

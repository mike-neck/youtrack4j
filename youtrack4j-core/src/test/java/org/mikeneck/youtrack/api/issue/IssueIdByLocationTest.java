package org.mikeneck.youtrack.api.issue;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mikeneck.youtrack.api.project.ProjectId;

class IssueIdByLocationTest {

  private final String location = "https://test.example.com/youtrack/rest/issue/TEST-200";

  private final IssueId issueId = new IssueIdByLocation(location);

  @Test
  void numberReturns200() {
    assertThat(issueId.number()).isEqualTo(200);
  }

  @Test
  void projectIdReturnsTEST() {
    assertThat(issueId.projectId()).isEqualTo(ProjectId.of("TEST"));
  }

  @Test
  void resourceUriReturnsOriginalLocation() {
    assertThat(issueId.resourceUri()).isEqualTo(location);
  }

  @Test
  void issuIdReturnsTEST200() {
    assertThat(issueId.issueId()).isEqualTo("TEST-200");
  }
}

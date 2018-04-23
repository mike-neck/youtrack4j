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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mikeneck.youtrack.api.project.ProjectId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IssueByStringTest {

  @Test
  void creatingInstanceWithoutHyphenIdWillCauseException() {
    assertThrows(IllegalArgumentException.class, () -> IssueId.fromId("TEST"));
  }

  @Nested
  @DisplayName("TEST-201")
  class IdTest201 {

    final IssueId issueId = IssueId.fromId("TEST-201");

    @Test
    void projectIsTEST() {
      assertThat(issueId.projectId()).isEqualTo(ProjectId.of("TEST"));
    }

    @Test
    void numberIs201() {
      assertThat(issueId.number()).isEqualTo(201);
    }
  }
}

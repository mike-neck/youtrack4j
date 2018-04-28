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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mikeneck.youtrack.Json.getText;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.mikeneck.youtrack.util.Codec;

class IssueTest {

  private final Codec codec = Codec.instance();

  @Test
  void jsonParse() {
    final String json = getText("issue.json");
    final Issue.Json deserialize = codec.deserialize(Issue.Json.class, json);
    final Issue issue = deserialize.immutable();
    System.out.println(issue);
    System.out.println(issue.getCreatedAtOffset(ZoneOffset.ofHours(9)));
    assertAll(
        () -> assertThat(issue.getPriority()).isEqualTo("3"),
        () -> assertThat(issue.getNumberInProject()).isEqualTo(16112),
        () ->
            assertThat(issue.getCreatedAtOffset(ZoneOffset.ofHours(9)))
                .isEqualTo(
                    LocalDateTime.of(2018, Month.APRIL, 23, 22, 50, 11, 987_000_000)
                        .atOffset(ZoneOffset.ofHours(9))));
  }
}

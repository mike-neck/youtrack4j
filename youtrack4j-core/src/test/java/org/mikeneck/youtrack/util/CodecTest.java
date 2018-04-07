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
package org.mikeneck.youtrack.util;

import org.junit.jupiter.api.Test;
import org.mikeneck.youtrack.project.AssigneeName;
import org.mikeneck.youtrack.project.YouTrackProject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CodecTest {

  private final ClassLoader loader = CodecTest.class.getClassLoader();

  private String json(final String jsonFile) {
    final URL url = loader.getResource(jsonFile);
    if (url == null) {
      throw new IllegalArgumentException("not found: " + jsonFile);
    }
    try (final Stream<String> stream =
        new BufferedReader(
                new InputStreamReader(loader.getResourceAsStream(jsonFile), StandardCharsets.UTF_8))
            .lines()) {
      return stream.collect(Collectors.joining());
    }
  }

  private final Codec codec = Codec.instance();

  @Test
  void projectListJson() {
    final String projectJson = json("project-list.json");
    final List<YouTrackProject.Json> youTrackProjects =
        codec.deserialize(new Codec.TypeRef<List<YouTrackProject.Json>>() {}, projectJson);
    assertThat(youTrackProjects).hasSize(4);
  }

  @Test
  void projectJson() {
    final String projectJson = json("project.json");
    final YouTrackProject.Json youTrackProject =
        codec.deserialize(YouTrackProject.Json.class, projectJson);
    //noinspection ResultOfMethodCallIgnored
    assertAll(
        () -> assertThat(youTrackProject.immutable()).isNotNull(),
        () ->
            assertThat(youTrackProject.getAssigneesFullName())
                .contains(new AssigneeName.Json("jon doe"), new AssigneeName.Json("guest")),
        () -> assertThat(youTrackProject.getName()).isEqualTo("DEMO project"));
  }
}

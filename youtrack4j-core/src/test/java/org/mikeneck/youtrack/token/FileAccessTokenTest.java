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
package org.mikeneck.youtrack.token;

import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FileAccessTokenTest {

  private static Path resolveTestFile(final String testFileName) {
    final Path file = Paths.get("test", testFileName);
    if (!Files.exists(file)) {
      final Path parent = Paths.get("test").toAbsolutePath().getParent();
      if (parent.endsWith("youtrack4j-core")) {
        return parent.resolve("test").resolve(testFileName);
      } else if (parent.endsWith("youtrack4j")) {
        return parent.resolve("youtrack4j-core").resolve("test").resolve(testFileName);
      } else {
        throw new TestAbortedException(String.format("test/%s is missing.", testFileName));
      }
    }
    return file;
  }

  @Test
  void nonExistingFile() {
    final FileAccessToken fileAccessToken =
        FileAccessToken.of(Paths.get("not-existing.properties"));
    final Optional<AccessToken> accessToken = fileAccessToken.get();
    assertThat(accessToken).isEmpty();
  }

  @Test
  void invalidFile() {
    final Path file = resolveTestFile("test.txt");
    final FileAccessToken fileAccessToken = FileAccessToken.of(file);
    final Optional<AccessToken> accessToken = fileAccessToken.get();
    assertThat(accessToken).isEmpty();
  }

  @Test
  void validFile() {
    final Path file = resolveTestFile("test.properties");
    final FileAccessToken fileAccessToken = FileAccessToken.of(file);
    final Optional<AccessToken> accessToken = fileAccessToken.get();
    assertThat(accessToken).contains(AccessToken.of("test-access-token"));
  }
}

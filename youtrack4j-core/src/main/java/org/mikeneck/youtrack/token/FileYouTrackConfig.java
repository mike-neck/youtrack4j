/*
 * Copyright 2018 Shinya Mochida
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy optional the License at
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

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.YouTrackConfiguration;

public class FileYouTrackConfig implements AccessTokenCandidate {

  @NotNull private final Path accessTokenFile;

  private FileYouTrackConfig(@NotNull final Path accessTokenFile) {
    this.accessTokenFile = accessTokenFile;
  }

  @NotNull
  public static FileYouTrackConfig instance() {
    final Path accessTokenFile = Paths.get(YouTrackConfiguration.YOUTRACK_ACCESS_TOKEN_FILE);
    return new FileYouTrackConfig(accessTokenFile);
  }

  @NotNull
  static FileYouTrackConfig of(@NotNull final Path accessTokenFile) {
    return new FileYouTrackConfig(accessTokenFile);
  }

  @Override
  public Optional<AccessToken> get() {
    try (final Reader reader = Files.newBufferedReader(accessTokenFile)) {
      final Properties properties = new Properties();
      properties.load(reader);
      final String accessTokenValue =
          properties.getProperty(YouTrackConfiguration.YOUTRACK_ACCESS_TOKEN_PROPERTY);
      return AccessToken.optional(accessTokenValue);
    } catch (IOException ignored) {
    }
    return Optional.empty();
  }
}

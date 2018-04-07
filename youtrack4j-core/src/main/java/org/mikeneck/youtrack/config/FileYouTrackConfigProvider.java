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
package org.mikeneck.youtrack.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.YouTrackConfiguration;

public class FileYouTrackConfigProvider extends PropertyBasedYouTrackConfig {

    private FileYouTrackConfigProvider(@NotNull final Properties properties) {
        super(properties);
    }

  private static FileYouTrackConfigProvider loadFromFile(final Path file) {
    try (final Reader reader = Files.newBufferedReader(file)) {
      final Properties properties = new Properties();
      properties.load(reader);
      return new FileYouTrackConfigProvider(properties);
    } catch (IOException ignored) {
      return new FileYouTrackConfigProvider(new Properties());
    }
  }

  @NotNull
  public static FileYouTrackConfigProvider instance() {
    final Path file = Paths.get(YouTrackConfiguration.YOUTRACK_PROPERTIES_FILE);
    return loadFromFile(file);
  }

  @NotNull
  static FileYouTrackConfigProvider of(@NotNull final Path file) {
    return loadFromFile(file);
  }

}

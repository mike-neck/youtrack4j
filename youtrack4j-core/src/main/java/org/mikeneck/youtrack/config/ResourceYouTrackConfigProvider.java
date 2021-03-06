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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.YouTrackConfiguration;

public class ResourceYouTrackConfigProvider extends PropertyBasedYouTrackConfig {

  private static final ClassLoader classLoader =
      ResourceYouTrackConfigProvider.class.getClassLoader();

  private ResourceYouTrackConfigProvider(@NotNull final Properties properties) {
    super(properties);
  }

  public static ResourceYouTrackConfigProvider instance() {

    return of(YouTrackConfiguration.YOUTRACK_PROPERTIES_FILE);
  }

  public static ResourceYouTrackConfigProvider of(final String resourceName) {
    final URL resource = classLoader.getResource(resourceName);
    final Properties properties = new Properties();
    if (resource == null) {
      return new ResourceYouTrackConfigProvider(properties);
    }
    try (final Reader reader =
        new InputStreamReader(
            classLoader.getResourceAsStream(resourceName), StandardCharsets.UTF_8)) {
      properties.load(reader);
      return new ResourceYouTrackConfigProvider(properties);
    } catch (IOException ignored) {
    }
    return new ResourceYouTrackConfigProvider(properties);
  }
}

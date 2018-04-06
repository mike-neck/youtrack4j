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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;
import org.mikeneck.youtrack.YouTrackAccessToken;

public class ResourceAccessToken implements AccessTokenCandidate {

  private final ClassLoader classLoader;

  private ResourceAccessToken() {
    this.classLoader = ResourceAccessToken.class.getClassLoader();
  }

  public static ResourceAccessToken instance() {
    return new ResourceAccessToken();
  }

  @Override
  public Optional<AccessToken> get() {
    final URL resource = classLoader.getResource(YouTrackAccessToken.YOUTRACK_ACCESS_TOKEN_FILE);
    if (resource == null) {
      return Optional.empty();
    }
    try (final Reader reader =
        new InputStreamReader(
            classLoader.getResourceAsStream(YouTrackAccessToken.YOUTRACK_ACCESS_TOKEN_FILE),
            StandardCharsets.UTF_8)) {
      final Properties properties = new Properties();
      properties.load(reader);
      final String property =
          properties.getProperty(YouTrackAccessToken.YOUTRACK_ACCESS_TOKEN_PROPERTY);
      return AccessToken.optional(property);
    } catch (IOException ignored) {
    }
    return Optional.empty();
  }
}

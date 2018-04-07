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
package org.mikeneck.youtrack;

import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.token.AccessToken;
import org.mikeneck.youtrack.token.FileYouTrackConfig;
import org.mikeneck.youtrack.token.ResourceYouTrackConfig;
import org.mikeneck.youtrack.token.SystemPropertyYouTrackConfig;
import org.mikeneck.youtrack.util.First;

public class YouTrackConfiguration {

  public static String YOUTRACK_ACCESS_TOKEN_PROPERTY = "youtrack.access.token";

  public static String YOUTRACK_ACCESS_TOKEN_FILE = "youtrack.properties";

  private final AccessToken accessToken;

  private YouTrackConfiguration(final AccessToken accessToken) {
    this.accessToken = accessToken;
  }

  public static YouTrackConfiguration load() {
      final SystemPropertyYouTrackConfig systemPropertyYouTrackConfig = SystemPropertyYouTrackConfig.instance();
      final FileYouTrackConfig fileYouTrackConfig = FileYouTrackConfig.instance();
      final ResourceYouTrackConfig resourceYouTrackConfig = ResourceYouTrackConfig.instance();
      final AccessToken accessToken =
        First.tryGet(systemPropertyYouTrackConfig)
            .tryNext(fileYouTrackConfig)
            .tryNext(resourceYouTrackConfig)
            .orElseThrow();
    return new YouTrackConfiguration(accessToken);
  }

  @NotNull
  AccessToken getAccessToken() {
    return accessToken;
  }
}

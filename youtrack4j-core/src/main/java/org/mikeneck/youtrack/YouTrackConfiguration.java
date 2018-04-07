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
import org.mikeneck.youtrack.config.*;
import org.mikeneck.youtrack.util.First;

public final class YouTrackConfiguration {

  public static String YOUTRACK_PROPERTIES_FILE = "youtrack.properties";

  public static String YOUTRACK_ACCESS_TOKEN_PROPERTY = "youtrack.access.token";

  public static String YOUTRACK_BASE_URL_PROPERTY = "youtrack.base.url";

  private final AccessToken accessToken;

  private YouTrackConfiguration(final AccessToken accessToken) {
    this.accessToken = accessToken;
  }

  public static YouTrackConfiguration load() {
    final SystemPropertyYouTrackConfigProvider systemPropertyYouTrackConfigProvider =
        SystemPropertyYouTrackConfigProvider.instance();
    final PropertyBasedYouTrackConfig fileYouTrackConfigProvider = FileYouTrackConfigProvider.instance();
    final ResourceYouTrackConfigProvider resourceYouTrackConfigProvider = ResourceYouTrackConfigProvider.instance();
    final AccessToken accessToken =
        First.tryGet(systemPropertyYouTrackConfigProvider::accessToken)
            .tryNext(fileYouTrackConfigProvider::accessToken)
            .tryNext(resourceYouTrackConfigProvider::accessToken)
            .orElseThrow();
    return new YouTrackConfiguration(accessToken);
  }

  @NotNull
  AccessToken getAccessToken() {
    return accessToken;
  }

  private static final class Builder {
      
  }
}

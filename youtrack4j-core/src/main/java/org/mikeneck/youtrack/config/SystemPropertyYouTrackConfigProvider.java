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

import java.util.Optional;
import org.mikeneck.youtrack.YouTrackConfiguration;

public class SystemPropertyYouTrackConfigProvider implements YouTrackConfigProvider {

  public static SystemPropertyYouTrackConfigProvider instance() {
    return new SystemPropertyYouTrackConfigProvider();
  }

  @Override
  public Optional<AccessToken> accessToken() {
    final String property = System.getProperty(YouTrackConfiguration.YOUTRACK_ACCESS_TOKEN_PROPERTY);
    return AccessToken.optional(property);
  }

    @Override
    public Optional<BaseUrl> baseUrl() {
        return Optional.empty();
    }
}

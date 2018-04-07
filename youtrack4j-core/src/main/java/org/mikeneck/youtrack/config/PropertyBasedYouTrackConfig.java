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
package org.mikeneck.youtrack.config;

import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.YouTrackConfiguration;

import java.util.Optional;
import java.util.Properties;

public class PropertyBasedYouTrackConfig implements YouTrackConfigProvider {
    @NotNull
    protected final Properties properties;

    public PropertyBasedYouTrackConfig(@NotNull final Properties properties) {
        this.properties = properties;
    }

    @Override
    public Optional<AccessToken> accessToken() {
      return Optional.ofNullable(
              properties.getProperty(YouTrackConfiguration.YOUTRACK_ACCESS_TOKEN_PROPERTY))
          .map(AccessToken::of);
    }

    @Override
    public Optional<BaseUrl> baseUrl() {
      return Optional.ofNullable(
              properties.getProperty(YouTrackConfiguration.YOUTRACK_BASE_URL_PROPERTY))
          .flatMap(BaseUrl::optional);
    }
}

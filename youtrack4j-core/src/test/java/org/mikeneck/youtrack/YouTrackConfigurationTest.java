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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mikeneck.youtrack.request.AccessToken;
import org.mikeneck.youtrack.request.BaseUrl;

class YouTrackConfigurationTest {

  @BeforeEach
  void setup() {
    System.setProperty("youtrack.base.url", "https://localhost:8080/youtrack");
  }

  @Test
  void resourceAccessTokenWillBeUsed() {
    final YouTrackConfiguration youTrackConfiguration = YouTrackConfiguration.load();
    assertThat(youTrackConfiguration.getAccessToken())
        .isEqualTo(AccessToken.of("test-access-token"));
  }

  @Test
  void systemBaseUrlWillBeUsed() {
    final YouTrackConfiguration youTrackConfiguration = YouTrackConfiguration.load();
    assertThat(youTrackConfiguration.getBaseUrl())
        .isEqualTo(BaseUrl.of("https://localhost:8080/youtrack"));
  }
}

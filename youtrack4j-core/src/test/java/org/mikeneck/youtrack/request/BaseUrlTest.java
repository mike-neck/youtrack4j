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
package org.mikeneck.youtrack.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class BaseUrlTest {

  @Test
  void nullParameterForOptionalMethod() {
    final Optional<BaseUrl> baseUrl = BaseUrl.optional(null);
    assertThat(baseUrl).isEmpty();
  }

  @Test
  void emptyParameterForOptionalMethod() {
    final Optional<BaseUrl> baseUrl = BaseUrl.optional("");
    assertThat(baseUrl).isEmpty();
  }

  @Test
  void invalidStringForOptionalMethod() {
    final Optional<BaseUrl> baseUrl = BaseUrl.optional("acbc");
    assertThat(baseUrl).isEmpty();
  }

  @Test
  void validUrlForOptionalMethod() {
    final Optional<BaseUrl> baseUrl = BaseUrl.optional("https://foo-bar.myjetbrains.com/youtrack");
    assertThat(baseUrl)
        .isNotEmpty()
        .contains(BaseUrl.of("https://foo-bar.myjetbrains.com/youtrack"));
  }

  @Test
  void usingLocalhost8080() {
    final Optional<BaseUrl> baseUrl = BaseUrl.optional("http://localhost:8080/youtrack");
    assertThat(baseUrl).isNotEmpty().contains(BaseUrl.of("http://localhost:8080/youtrack"));
  }
}

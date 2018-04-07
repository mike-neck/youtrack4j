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

import java.io.Serializable;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AccessToken implements Serializable {

  private static final long serialVersionUID = 79239078435084L;

  private final String accessToken;

  private AccessToken(@NotNull final String accessToken) {
    this.accessToken = accessToken;
  }

  @NotNull
  public static AccessToken of(@NotNull final String accessToken) {
    return new AccessToken(accessToken);
  }

  @NotNull
  static Optional<AccessToken> optional(@Nullable final String accessToken) {
    if (accessToken == null || accessToken.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(new AccessToken(accessToken));
  }

  @Override
  public String toString() {
    @SuppressWarnings("StringBufferReplaceableByString")
    final StringBuilder sb = new StringBuilder("AccessToken{");
    sb.append("accessToken='").append(accessToken).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof AccessToken)) return false;

    final AccessToken that = (AccessToken) o;

    return accessToken.equals(that.accessToken);
  }

  @Override
  public int hashCode() {
    return accessToken.hashCode();
  }
}

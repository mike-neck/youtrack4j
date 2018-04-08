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

import java.io.Serializable;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mikeneck.youtrack.request.http.GetUrl;

public class BaseUrl implements Serializable {

  private static final long serialVersionUID = -533297824357249L;

  private static final Pattern PATTERN =
      Pattern.compile("https?://[a-zA-Z0-9\\-_]+(.[a-zA-Z0-9\\-_]+)*(:[0-9]+)?/youtrack");

  private final String baseUrl;

  private BaseUrl(@NotNull final String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public GetUrl get(final String path) {
    final String request = URI.create(baseUrl).resolve("rest").resolve(path).toASCIIString();
    return new GetUrl(request);
  }

  public static Optional<BaseUrl> optional(@Nullable final String baseUrl) {
    if (baseUrl == null || baseUrl.isEmpty()) {
      return Optional.empty();
    }
    if (!PATTERN.matcher(baseUrl).matches()) {
      return Optional.empty();
    }
    return Optional.of(new BaseUrl(baseUrl));
  }

  public static BaseUrl of(@NotNull final String baseUrl) {
    if (!PATTERN.matcher(baseUrl).matches()) {
      throw new IllegalArgumentException("invalid: " + baseUrl);
    }
    return new BaseUrl(baseUrl);
  }

  @Override
  public String toString() {
    return "BaseUrl{" + "baseUrl='" + baseUrl + '\'' + '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof BaseUrl)) return false;

    final BaseUrl baseUrl1 = (BaseUrl) o;

    return baseUrl.equals(baseUrl1.baseUrl);
  }

  @Override
  public int hashCode() {
    return baseUrl.hashCode();
  }
}

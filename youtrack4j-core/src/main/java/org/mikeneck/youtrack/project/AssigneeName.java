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
package org.mikeneck.youtrack.project;

import java.util.Objects;
import org.mikeneck.youtrack.util.SingleJsonForm;

public class AssigneeName {

  private final String value;

  @SuppressWarnings("WeakerAccess")
  public AssigneeName(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    //noinspection StringBufferReplaceableByString
    final StringBuilder sb = new StringBuilder("AssigneeName{");
    sb.append("value='").append(value).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public static class Json extends SingleJsonForm<AssigneeName> {

    public Json() {}

    public Json(final String value) {
      super(value);
    }

    @Override
    public boolean equals(final Object other) {
      if (other == null) {
        return false;
      }
      if (!(other instanceof Json)) {
        return false;
      }
      final Json json = (Json) other;
      return value.equals(json.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(Json.class, value);
    }

    @Override
    public AssigneeName immutable() {
      return new AssigneeName(value);
    }
  }
}

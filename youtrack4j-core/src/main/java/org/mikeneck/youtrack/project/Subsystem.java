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

import org.mikeneck.youtrack.util.SingleJsonForm;

import java.util.Objects;

public class Subsystem {

  private final String value;

  public Subsystem(final String value) {
    this.value = value;
  }

  public static class Json extends SingleJsonForm<Subsystem> {

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
    public Subsystem immutable() {
      return new Subsystem(value);
    }
  }
}

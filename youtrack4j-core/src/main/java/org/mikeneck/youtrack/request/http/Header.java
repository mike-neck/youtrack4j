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
package org.mikeneck.youtrack.request.http;

public class Header {

  private final String name;
  private final String value;

  public Header(final String name, final String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Header: " + name + ": " + value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof Header)) return false;

    final Header header = (Header) o;

    if (!name.equals(header.name)) return false;
    return value != null ? value.equals(header.value) : header.value == null;
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }
}

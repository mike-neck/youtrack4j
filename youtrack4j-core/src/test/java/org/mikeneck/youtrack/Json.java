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
package org.mikeneck.youtrack;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public final class Json {

  private static final ClassLoader LOADER = Json.class.getClassLoader();

  private static Stream<String> getResource(final String file) {
    final InputStream resource = LOADER.getResourceAsStream(file);
    final InputStreamReader reader = new InputStreamReader(resource, StandardCharsets.UTF_8);
    return new BufferedReader(reader).lines();
  }

  public static String getText(final String file) {
    try (final Stream<String> stream = getResource(file)) {
      return stream.collect(joining());
    }
  }
}

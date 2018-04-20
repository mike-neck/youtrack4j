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

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public interface Parameters<V> {

  @NotNull
  RichIterable<Pair<String, V>> parameters();

  @NotNull
  default <C> C configureParameters(
      @NotNull final C configuration, @NotNull final Configurer<C, ? super V> configurer) {
    return parameters()
        .injectInto(
            configuration,
            (conf, pair) -> configurer.configure(conf, pair.getOne(), pair.getTwo()));
  }

  interface Configurer<C, V> {

    C configure(final C configuration, final String key, final V value);
  }
}

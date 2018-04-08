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

import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.impl.factory.Maps;
import org.jetbrains.annotations.NotNull;

public final class QueryParameters {

  private final ImmutableMap<String, String> queries;

  public QueryParameters() {
    this.queries = Maps.immutable.empty();
  }

  private QueryParameters(final ImmutableMap<String, String> queries) {
    this.queries = queries;
  }

  <C> C configureParameter(final C configuration, final QueryConfigurer<C> configurer) {
    return queries
        .keyValuesView()
        .injectInto(
            configuration,
            (conf, pair) -> configurer.configure(conf, pair.getOne(), pair.getTwo()));
  }

  @NotNull
  public static QueryValue queryKey(@NotNull final String key) {
    return value -> new QueryParameters(Maps.immutable.of(key, value));
  }

  @NotNull
  public QueryValue withKey(@NotNull final String key) {
    return value -> new QueryParameters(queries.newWithKeyValue(key, value));
  }

  @FunctionalInterface
  interface QueryConfigurer<C> {
    C configure(final C configuration, final String key, final String value);
  }

  public interface QueryValue {
    default QueryParameters value(final int value) {
      final String v = Integer.toString(value);
      return value(v);
    }

    default QueryParameters value(final boolean value) {
      final String v = Boolean.toString(value);
      return value(v);
    }

    QueryParameters value(@NotNull final String value);
  }
}

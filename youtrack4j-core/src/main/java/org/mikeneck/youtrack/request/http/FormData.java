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

import java.util.List;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.jetbrains.annotations.NotNull;

public final class FormData implements Parameters<List<String>> {

  private final ImmutableMap<String, List<String>> form;

  public FormData() {
    this.form = Maps.immutable.empty();
  }

  private FormData(ImmutableMap<String, List<String>> form) {
    this.form = form;
  }

  @Override
  @NotNull
  public RichIterable<Pair<String, List<String>>> parameters() {
    return form.keyValuesView();
  }

  public FormValue form(@NotNull final String key) {
    return value -> new FormData(form.newWithKeyValue(key, value));
  }

  public interface FormValue {
    default FormData value(final int value) {
      final String v = Integer.toString(value);
      return value(v);
    }

    default FormData value(final boolean value) {
      final String v = Boolean.toString(value);
      return value(v);
    }

    default FormData value(final String value) {
      return value(Lists.immutable.of(value).castToList());
    }

    FormData value(final List<String> value);
  }
}

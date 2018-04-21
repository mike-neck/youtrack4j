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

import java.nio.file.Path;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.tuple.Tuples;
import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.request.http.Parameters;

public final class MultipartData implements Parameters<Iterable<MultipartEntry>> {

  private final ImmutableList<Pair<String, MultipartEntry>> multipart;

  private MultipartData() {
    this.multipart = Lists.immutable.empty();
  }

  private MultipartData(ImmutableList<Pair<String, MultipartEntry>> multipart) {
    this.multipart = multipart;
  }

  @NotNull
  @Override
  public RichIterable<Pair<String, Iterable<MultipartEntry>>> parameters() {
    return multipart
        .groupBy(Pair::getOne)
        .collectValues(Pair::getTwo)
        .keyMultiValuePairsView()
        .asLazy()
        .collect(pair -> Tuples.pair(pair.getOne(), pair.getTwo()));
  }

  public MultipartValue form(@NotNull final String key) {
    return value -> new MultipartData(multipart.newWith(Tuples.pair(key, value)));
  }

  public interface MultipartValue {

    default MultipartData value(final String value) {
      return value(MultipartEntry.string(value));
    }

    default MultipartData value(final int value) {
      return value(MultipartEntry.integer(value));
    }

    default MultipartData value(final boolean value) {
      return value(MultipartEntry.bool(value));
    }

    default MultipartData value(final Path value) {
      return value(MultipartEntry.file(value));
    }

    MultipartData value(final MultipartEntry value);
  }
}

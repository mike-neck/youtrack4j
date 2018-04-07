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
package org.mikeneck.youtrack.util;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.mikeneck.youtrack.config.AccessToken;

public interface First<T> {

  static <T> First<T> notYet() {
    return new FirstNotYet<>();
  }

  @NotNull
  First<T> tryNext(final Supplier<? extends Optional<T>> next);

  @NotNull
  T orElse(final Supplier<? extends T> candidate);

  @NotNull
  T orElseThrow() throws NoSuchElementException;

  @NotNull
  static <T> First<T> tryGet(@NotNull final Supplier<? extends Optional<T>> firstTry) {
    return firstTry.get().<First<T>>map(FirstHasItem::new).orElseGet(FirstNotYet::new);
  }
}

class FirstHasItem<T> implements First<T> {

  private final T item;

  FirstHasItem(@NotNull final T item) {
    this.item = item;
  }

  @NotNull
  @Override
  public First<T> tryNext(final Supplier<? extends Optional<T>> next) {
    return this;
  }

  @NotNull
  @Override
  public T orElse(final Supplier<? extends T> candidate) {
    return item;
  }

  @NotNull
  @Override
  public T orElseThrow() {
    return item;
  }
}

class FirstNotYet<T> implements First<T> {

  @NotNull
  @Override
  public First<T> tryNext(final Supplier<? extends Optional<T>> next) {
    return next.get().<First<T>>map(FirstHasItem::new).orElse(this);
  }

  @NotNull
  @Override
  public T orElse(final Supplier<? extends T> candidate) {
    final T t = candidate.get();
    if (t == null) {
      throw new NoSuchElementException();
    }
    return t;
  }

  @NotNull
  @Override
  public T orElseThrow() {
    throw new NoSuchElementException();
  }
}

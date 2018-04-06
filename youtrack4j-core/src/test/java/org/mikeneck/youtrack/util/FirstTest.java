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
package org.mikeneck.youtrack.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FirstTest {

  @Nested
  class FirstTryHasNoItem {

    First<String> first;

    @BeforeEach
    void setup() {
      first = First.tryGet(Optional::empty);
    }

    @Test
    void test() {
      assertThat(first).isInstanceOf(FirstNotYet.class);
    }

    @Test
    void nextTryHasItem() {
      final First<String> second = first.tryNext(() -> Optional.of("foo"));
      assertThat(second).isInstanceOf(FirstHasItem.class);
    }

    @Test
      void nextTryHasNoItem() {
        final First<String> second = first.tryNext(Optional::empty);
        //noinspection ResultOfMethodCallIgnored
        assertThatThrownBy(second::orElseThrow);
    }
  }

  @Nested
  class FirstTryHasItem {

      First<String> first;

      @BeforeEach
      void setup() {
          first = First.tryGet(() -> Optional.of("foo"));
      }

      @Test
      void test() {
          assertThat(first).isInstanceOf(FirstHasItem.class);
      }

      @Test
      void nextTryHasItem() {
          final First<String> second = first.tryNext(() -> Optional.of("bar"));
          assertThat(second.orElseThrow()).isEqualTo("foo");
      }

      @Test
      void nextTryHasNoItem() {
          final First<String> second = first.tryNext(Optional::empty);
          assertThat(second.orElseThrow()).isEqualTo("foo");
      }
  }
}

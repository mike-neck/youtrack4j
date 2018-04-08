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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Codec {

  private Codec() {}

  private static final Codec CODEC = new Codec();

  public static Codec instance() {
    return CODEC;
  }

  private final ObjectMapper objectMapper =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

  public <T> T deserialize(final Class<T> klass, final String json) {
    try {
      return objectMapper.readValue(json, klass);
    } catch (IOException e) {
      throw new CodecException(json, "deserialization", e);
    }
  }

  public <E, J extends JsonForm<E>, C extends Collection<J>> List<E> deserialize(
      final TypeRef<C> typeRef, final String json) {
    try {
      final C collection = objectMapper.readValue(json, typeRef);
      final List<E> list =
          collection.stream().map(JsonForm::immutable).collect(Collectors.toList());
      return list;
    } catch (IOException e) {
      throw new CodecException(json, "deserialization for collection", e);
    }
  }

  public abstract static class TypeRef<T> extends TypeReference<T> {}
}

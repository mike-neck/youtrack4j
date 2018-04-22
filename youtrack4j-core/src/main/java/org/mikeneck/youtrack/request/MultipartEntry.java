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
import java.util.function.Function;

import org.asynchttpclient.request.body.multipart.FilePart;
import org.asynchttpclient.request.body.multipart.Part;
import org.asynchttpclient.request.body.multipart.StringPart;
import org.jetbrains.annotations.NotNull;

public interface MultipartEntry {

  Function<String, Part> part();

  @NotNull
  static MultipartEntry string(@NotNull final String value) {
    return new MultipartValue(value);
  }

  @NotNull
  static MultipartEntry integer(final int value) {
    final String string = Integer.toString(value);
    return string(string);
  }

  @NotNull
  static MultipartEntry bool(final boolean value) {
    final String string = Boolean.toString(value);
    return string(string);
  }

  @NotNull
  static MultipartEntry file(@NotNull final Path file) {
    return new MultipartFile(file);
  }
}

class MultipartValue implements MultipartEntry {

  private final String value;

  MultipartValue(String value) {
    this.value = value;
  }

  @Override
  public Function<String, Part> part() {
    return name -> new StringPart(name, value);
  }
}

class MultipartFile implements MultipartEntry {

  private final Path file;

  MultipartFile(Path file) {
    this.file = file;
  }

  @Override
  public Function<String, Part> part() {
    return name -> new FilePart(name, file.toAbsolutePath().toFile());
  }
}

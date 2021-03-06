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

import java.util.Optional;
import org.mikeneck.youtrack.request.http.FailureResponse;
import org.mikeneck.youtrack.request.http.HttpResponse;

public final class Handler {

  private Handler() {}

  public interface ResponseHandler<R> {
    Optional<R> handle(final HttpResponse response);
  }

  public interface SuccessHandler<R> {
    void handle(final R result);
  }

  public interface SuccessMapper<R, S> {
    S handle(final R result);
  }

  public interface NextRequest<R, S> {
    ApiResponse<S> execute(final R result);
  }

  public interface FailureHandler {
    void handle(final FailureResponse failureResponse);

    default void handleException(final Throwable exception) {
      if (exception instanceof ApiException) {
        handle(((ApiException) exception));
      }
    }
  }
}

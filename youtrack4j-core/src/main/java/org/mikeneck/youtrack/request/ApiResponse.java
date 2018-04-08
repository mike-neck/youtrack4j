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

public interface ApiResponse<R> {

  ApiResponse<R> onSuccess(final Handler.SuccessHandler<? super R> successHandler);

  <S> ApiResponse<S> map(final Handler.SuccessMapper<? super R, ? extends S> successMapper);

  <S> ApiResponse<S> next(final Handler.NextRequest<? super R, ? extends S> nextRequest);

  ApiResponse<R> onFailure(final Handler.FailureHandler failureHandler);
}

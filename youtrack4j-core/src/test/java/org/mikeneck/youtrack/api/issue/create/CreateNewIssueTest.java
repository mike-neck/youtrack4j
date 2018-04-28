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
package org.mikeneck.youtrack.api.issue.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mikeneck.youtrack.Json.getText;

import java.util.Optional;
import java.util.function.Function;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mikeneck.youtrack.api.issue.Issue;
import org.mikeneck.youtrack.api.issue.IssueId;
import org.mikeneck.youtrack.request.ApiResponse;
import org.mikeneck.youtrack.request.http.Header;
import org.mikeneck.youtrack.request.http.HttpResponse;
import org.mikeneck.youtrack.util.CodecException;

class CreateNewIssueTest {

  private final CreateNewIssue instance = new CreateNewIssueImpl();

  @Nested
  class OnStatusCode400 {
    @Test
    void defaultExtractorReturnsEmpty() {
      final Response response = response(400).apply("");
      final Optional<Issue> issue = instance.defaultExtractor(response);
      assertThat(issue).isEmpty();
    }
  }

  @Nested
  class OnStatusCode200 {

    final Function<String, Response> response = response(200);

    @Nested
    class IfBodyIsBroken {

      final Response res = response.apply("{\"test\":\"test\"");

      @Test
      void codecExceptionIsThrown() {
        assertThatThrownBy(() -> instance.defaultExtractor(res)).isInstanceOf(CodecException.class);
      }
    }

    @Nested
    class SuccessCase {

      final Response res = response.apply(getText("issue.json"));

      @Test
      void issueIsAvailable() {
        final Optional<Issue> issue = instance.defaultExtractor(res);
        assertAll(
            () -> assertThat(issue).isNotEmpty(),
            () ->
                assertThat(issue)
                    .map(Issue::getId)
                    .hasValueSatisfying(
                        issueId -> IssueId.fromId("DEMO-16112").isSameIssue(issueId)));
      }
    }
  }

  private static Function<String, Response> response(final int statusCode) {
    return body -> new Response(statusCode, body);
  }

  static class Response implements HttpResponse {

    private final int statusCode;
    private final String body;
    private final ImmutableList<Header> headers;

    Response(int statusCode, String body) {
      this(statusCode, body, Lists.immutable.empty());
    }

    Response(int statusCode, String body, ImmutableList<Header> headers) {
      this.statusCode = statusCode;
      this.body = body;
      this.headers = headers;
    }

    @Override
    public int getStatusCode() {
      return statusCode;
    }

    @Override
    public String getBody() {
      return body;
    }

    @Override
    public Iterable<Header> headers() {
      return headers;
    }

    @Override
    public Iterable<String> header(String headerName) {
      return headers
          .select(header -> header.getName().equals(headerName))
          .collect(Header::getValue);
    }
  }

  static class CreateNewIssueImpl implements CreateNewIssue {
    @Override
    public ApiResponse<Issue> executeRequest() {
      return null;
    }
  }
}

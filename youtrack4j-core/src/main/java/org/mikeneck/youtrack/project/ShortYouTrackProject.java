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
package org.mikeneck.youtrack.project;

import org.mikeneck.youtrack.util.JsonForm;

public class ShortYouTrackProject implements YouTrackProject {

  private final String name;
  private final String shortName;

  public ShortYouTrackProject(final String name, final String shortName) {
    this.name = name;
    this.shortName = shortName;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String id() {
    return shortName;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ShortYouTrackProject{");
    sb.append("name='").append(name).append('\'');
    sb.append(", shortName='").append(shortName).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public static class Json implements JsonForm<YouTrackProject> {
    private String name;
    private String shortName;

    @Override
    public YouTrackProject immutable() {
      return new ShortYouTrackProject(name, shortName);
    }

    public String getName() {
      return name;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public String getShortName() {
      return shortName;
    }

    public void setShortName(final String shortName) {
      this.shortName = shortName;
    }
  }
}

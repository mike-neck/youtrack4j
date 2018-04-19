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

import java.util.List;
import java.util.stream.Collectors;
import org.mikeneck.youtrack.util.JsonForm;
import org.mikeneck.youtrack.util.SingleJsonForm;

public class LongYouTrackProject implements YouTrackProject {

  private final String name;
  private final String shortName;
  private final String description;
  private final boolean isImporting;
  private final List<Subsystem> subsystems;
  private final List<AssigneeLogin> assigneesLogin;
  private final List<AssigneeName> assigneesFullName;

  @Override
  public String toString() {
    //noinspection StringBufferReplaceableByString
    final StringBuilder sb = new StringBuilder("LongYouTrackProject{");
    sb.append("name='").append(name).append('\'');
    sb.append(", shortName='").append(shortName).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", isImporting=").append(isImporting);
    sb.append(", subsystems=").append(subsystems);
    sb.append(", assigneesLogin=").append(assigneesLogin);
    sb.append(", assigneesFullName=").append(assigneesFullName);
    sb.append('}');
    return sb.toString();
  }

  @SuppressWarnings("WeakerAccess")
  public LongYouTrackProject(
      final String name,
      final String shortName,
      final String description,
      final boolean isImporting,
      final List<Subsystem> subsystems,
      final List<AssigneeLogin> assigneesLogin,
      final List<AssigneeName> assigneesFullName) {
    this.name = name;
    this.shortName = shortName;
    this.description = description;
    this.isImporting = isImporting;
    this.subsystems = subsystems;
    this.assigneesLogin = assigneesLogin;
    this.assigneesFullName = assigneesFullName;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String id() {
    return shortName;
  }

  public static class Json implements JsonForm<YouTrackProject> {
    private String name;
    private String shortName;
    private String description;
    private boolean isImporting;
    private List<Subsystem.Json> subsystems;
    private List<AssigneeLogin.Json> assigneesLogin;
    private List<AssigneeName.Json> assigneesFullName;

    @Override
    public YouTrackProject immutable() {
      return new LongYouTrackProject(
          name,
          shortName,
          description,
          isImporting,
          subsystems.stream().map(SingleJsonForm::immutable).collect(Collectors.toList()),
          assigneesLogin.stream().map(SingleJsonForm::immutable).collect(Collectors.toList()),
          assigneesFullName.stream().map(SingleJsonForm::immutable).collect(Collectors.toList()));
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

    public String getDescription() {
      return description;
    }

    public void setDescription(final String description) {
      this.description = description;
    }

    public boolean isImporting() {
      return isImporting;
    }

    public void setImporting(final boolean importing) {
      isImporting = importing;
    }

    public List<Subsystem.Json> getSubsystems() {
      return subsystems;
    }

    public void setSubsystems(final List<Subsystem.Json> subsystems) {
      this.subsystems = subsystems;
    }

    public List<AssigneeLogin.Json> getAssigneesLogin() {
      return assigneesLogin;
    }

    public void setAssigneesLogin(final List<AssigneeLogin.Json> assigneesLogin) {
      this.assigneesLogin = assigneesLogin;
    }

    public List<AssigneeName.Json> getAssigneesFullName() {
      return assigneesFullName;
    }

    public void setAssigneesFullName(final List<AssigneeName.Json> assigneesFullName) {
      this.assigneesFullName = assigneesFullName;
    }
  }
}

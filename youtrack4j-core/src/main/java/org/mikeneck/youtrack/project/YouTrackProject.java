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

public class YouTrackProject {

  private final String name;
  private final String shortName;
  private final String description;
  private final boolean isImporting;
  private final List<Subsystem> subsystems;
  private final List<AssigneeLogin> assigneesLogin;
  private final List<AssigneeName> assigneesFullName;

  public YouTrackProject(
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

  public static class Json {
    private String name;
    private String shortName;
    private String description;
    private boolean isImporting;
    private List<Subsystem.Json> subsystems;
    private List<AssigneeLogin.Json> assigneesLogin;
    private List<AssigneeName.Json> assigneesFullName;

    public YouTrackProject immutable() {
      return new YouTrackProject(
          name,
          shortName,
          description,
          isImporting,
          subsystems.stream().map(JsonForm::immutable).collect(Collectors.toList()),
          assigneesLogin.stream().map(JsonForm::immutable).collect(Collectors.toList()),
          assigneesFullName.stream().map(JsonForm::immutable).collect(Collectors.toList()));
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

YouTrack4J
===

This is an unofficial [YouTrack](https://www.jetbrains.com/youtrack/) client for Java Language.


---

Configuration
===

This client requires 2 properties.

property key|property value
:--|:--
`youtrack.access.token`|Access Token for YouTrack.
`youtrack.base.url`|[Base URL](https://www.jetbrains.com/help/youtrack/standalone/YouTrack-REST-API-Reference.html) for your YouTrack instance, with finishing with path `/youtrack`.

These properties can be given via JVM parameters(`java -jar your.jar -Dyoutrack.access.token=xxxx`),
a file with name `youtrack.properties` at working directory
or a resource file(the name is `youtrack.properties` too).

The order of file to be loaded is determined as follows.

1. JVM parameters.
1. `youtrack.properties` file on a working directory.
1. `youtrack-test.properties` file on classpath resource.
1. `youtrack.properties` file on classpath resource.

Usages
===

This client currently supports these APIs.

* `getAccessibleProjects`
* `createNewIssue`

### `getAccessibleProjects`

Requests `GET /projects/all` and returns `List<YouTrackProject>`.

```java
public class Sample {
  public static void main(String[] args){
    try (final YouTrack youTrack = YouTrack.getInstance()) {
      youTrack.getAccessibleProjects()
        .executeRequest()
        .onSuccess(projects -> projects.forEach(System.out::println))
        .block();
    }
  }
}
// -> ShortYouTrackProject{name='Project Name', shortName='PN'}
```

### `createNewIssue`

Requests `POST /issue` and returns `Issue`

```java
public class Sample {
  public static void main(String[] args) {
    try (final YouTrack youTrack = YouTrack.getInstance()) {
      youTrack.createNewIssueInProject("PN")
        .summary("The style of UserName in front page is broken.")
        .executeRequest()
        .map(Issue::getId)
        .onSuccess(System.out::println)
        .block();
    }
  }
}
// -> IssueId[id: PN-123065]
```

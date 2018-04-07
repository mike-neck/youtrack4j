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



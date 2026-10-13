# JPA Hibernate Derby Demo and Learning Application

This application demonstrates JPA (Java Persistence API) with Hibernate ORM and Apache Derby.
A JavaFX client performs basic create, read, update, and delete (CRUD) operations. Watch the console for application and database logs.
The application uses the Java Platform Module System (JPMS).

## Requirements and versions

Stable releases checked on 2026-10-13:

| Component | Version |
| --- | --- |
| Java (JDK) | 27 |
| Gradle wrapper | 9.8.1 |
| JavaFX | 27 |
| JavaFX Gradle plugin | 0.1.0 |
| FreeFair Lombok plugin | 9.8.0 |
| Lombok | 1.18.48 |
| Jakarta Persistence | 3.2.0 |
| Jakarta Interceptors | 2.2.0 |
| Jakarta CDI API (runtime module dependency) | 5.0.0 |
| Hibernate ORM and community dialects | 7.4.13.Final |
| Apache Derby | 10.17.1.0 |
| SLF4J | 2.0.20 |
| Apache Log4j | 2.26.1 |
| JUnit Jupiter | 6.1.3 |

Install [JDK 27](https://jdk.java.net/27/) and set `JAVA_HOME` to its installation directory.
In IntelliJ IDEA, select JDK 27 as the project SDK and Gradle JVM, and use the project's Gradle wrapper.
A separate Gradle installation is unnecessary.

## Build and run

On Windows (PowerShell):

```powershell
.\gradlew.bat clean build
.\gradlew.bat run
```

On Linux or macOS:

```sh
./gradlew clean build
./gradlew run
```

The build includes a Derby integration test covering persistence, reads, updates, and deletes in a temporary database.
JavaFX's native access is enabled by the application configuration for `run` and generated launch scripts.
The launcher also resolves Hibernate's runtime dependencies on the module path.

**The demo drops and recreates its database tables on every startup.** It stores the embedded database in `animals.db`
relative to the working directory. To retain data, change `jakarta.persistence.schema-generation.database.action`
from `drop-and-create` to `none` in `src/main/resources/META-INF/persistence.xml` after creating the schema.

## JPA (Java Persistence API)

JPA defines a standard API for mapping Java objects to relational database tables and managing their persistence.
This demo shows entity mapping and basic CRUD operations using Hibernate as the persistence provider.

The current dependencies use [Jakarta Persistence](https://jakarta.ee/specifications/persistence/3.2/), the successor to JPA.
Entity annotations and API imports use `jakarta.persistence`; the configuration uses the Jakarta Persistence 3.2 XML namespace.

[Hibernate ORM](https://hibernate.org/orm/releases/7.4/) implements the API. Derby's dialect comes from the matching
`hibernate-community-dialects` dependency.

[Apache Derby](https://db.apache.org/derby/) is the embedded relational database. Its JDBC driver is discovered automatically.

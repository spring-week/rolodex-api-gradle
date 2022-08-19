# Rolodex API: Gradle Edition :elephant:

### To Recreate this:
1. Access Spring Initiliazer at [start.spring.io](https://start.spring.io)

2. Select the following properties: 
    - Gradle Project
    - Java
    - Spring Boot: 2.7.3
    - Group: `com.revature`
    - Artifact: `rolodex-api-gradle`
    - Package name: `com.revature`
    - Packaging: Jar
    - Java: 8
    - Spring Starter Validation

3.  Dependencies:
    - Spring Web
    - Spring Data JPA
    - H2 Driver
    - Lombok
    - Dev Tools
    - Spring Boot Actuator

4.  Click **Generate**, extract the downloaded zip file to a directory. Import it into your IDE:
    - For **SpringToolSuites**: go to Import > Gradle project.
    - For IntelliJ, just go to File > Open

> *In the case that you need to add a new dependency to the `build.gradle` file, navigate to the Maven Central Repository and select the **Gradle (Short)** version.  Paste it beneath the other `dependencies`, then run `gradlew build --refresh-dependencies` to rebuild the project.*

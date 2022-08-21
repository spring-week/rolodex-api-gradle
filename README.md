# Rolodex API: Gradle Edition :elephant:

<details>
    <summary>How to Recreate this App</summary>
    
<br>

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

</details>


<details>
    <summary>How to Dockerize & Run this App</summary>

<br>

*The following Dockerfile assumes that you have already gnerated the artifact with the commands listed in the first step.  If you would like to explore more options, please use [this resource](https://codefresh.io/docs/docs/learn-by-example/java/gradle/).*

1. In your `build.gradle` file, please add the following to name the build JAR as the name of your artifact and not the "artifact-1.0.0-SNAPSHOT.jar"

```groovy
bootJar {
   archiveName = "$baseName.$extension"
}
```

2. Build the JAR within the root directory of the project.
 
```
./gradlew clean bootJar
```

    *The JAR will now live in **/build/libs/your-project-name.jar***

3. Add the following Dockerfile in the root project directory.

```Dockerfile
# declare the base image - here is a light weight JDK 8 setup
FROM openjdk:8-jdk-alpine

# copy the generated JAR (from gradle) into the container to run
COPY build/libs/*.jar rolodex-api-gradle.jar

# Expose port 5000 of the container
EXPOSE 5000

# Run the JAR when we run the container, thus executing the app
ENTRYPOINT ["java", "-jar", "rolodex-api-gradle.jar"]
```

4. Build the image and run the container run these commands within the root directory of the project

```
docker build -t my-api:auto .

docker run -d -p 5000:5000 my-api:auto
```

:tada: *It should now be up and running at http://localhost:5000/api*
</details>


<details>
    <summary>How to Enable SonarCloud</summary>

<br>

1. Go to [SonarCloud.io](https://sonarcloud.io/) and import your repository.

2. Once you import it, click at the top to configure CI-based analysis.

3. Add the `SONAR TOKEN` and secret key to the Secrets section of the repository.

4. Add the following to the `plugins {...}` portion of your `build.gradle` file.

```groovy
plugins {
    ...
    id "org.sonarqube" version "3.4.0.2513"
}
```

5. Add the SonarQube properties SonarCloud tells you to add at the *bottom* of your `build.gradle` file. 

6. Before you `git push`, edit the file permissions of `./gradlew` like so - this is so that the SonarQube platform can build & run the app with the gradle wrapper:

```git
git update-index --chmod=+x gradlew
git add .
git commit -m "Changing permission of gradlew"
git push
```

7. `git add .`, `git commit -m"added sonar cloud plugin`, `git push` your changes!

8. Now, go to your repository on github.com.  Click **Add file** in the root of your project.

9. Name it `.github/workflows/build.yml`, which will create the appropriate .github/workflows directory for the build.yml.

10. SonarCloud will provision you with the yml file to paste in there.  The only thing you need to change is the `branches` section from `master` to **`main`**

```yml
on:
  push:
    branches:
      - main
```

</details>
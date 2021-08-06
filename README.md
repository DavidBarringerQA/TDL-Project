# TDL Project
A To-do list website build using Spring Boot and HTML with JavaScript's fetch

## Getting Started
This is a guide to getting the project working for development and testing purposes. Ensure that you have installed all of the prerequisites.

### Prerequisites
* [Java JDK 11+](https://www.oracle.com/java/technologies/javase-downloads.html)
* [MySQL 8.0+](https://dev.mysql.com/downloads/installer/)
* [Apache Maven 3.8](https://maven.apache.org/download.cgi)

You will need to make sure that Java and Maven are correctly added to your path as well [(e.g. for Java)](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/)
To check that Java and Maven are installed and added to the path correctly, run `java -version` and `mvn -version` in a terminal of your choice.

### Installing
1. Fork this repository
2. Clone your repository using `git clone <url>`
3. Update the application-prod.properties (located in src/main/resources) file to the credentials of your database, you can also check that the port given in the application.properties file is not currently in use
4. To run the project, either open the project in Eclipse and run the project from there, or use `mvn compile` to compile you project, move to the target/classes folder in your terminal and type `java com.qa.tdl.Runner`
5. To test that everything is running properly, open the index.html file (located in src/main/html/) in your browser and click the add item button, fill in the form, click submit. The data may show up in the list, but make sure you try refreshing the page to check that the database works.

**Note** if the port was changed in the application.properties file, it will need updating in the fetch requests in the index.js file (src/main/html/js/)

## Running tests
Before running tests, ensure that Spring Boot devtools are commented out/removed from the pom.xml file e.g.:
```
<!-- <dependency> -->
	<!-- 	<groupId>org.springframework.boot</groupId> -->
	<!-- 	<artifactId>spring-boot-devtools</artifactId> -->
	<!-- 	<scope>runtime</scope> -->
	<!-- 	<optional>true</optional> -->
<!-- </dependency> -->
```
The full test suite can be run using `mvn test`

### Unit tests
Unit tests are written using JUnit and Mockito, they can be run as a JUnit test in an IDE (such as Eclipse)

### Integration tests
Integration tests are written for the Spring Boot controller using Spring Boot's owns testing suite, 
using MockMvc to imitate HTTP requests. They can be run as a JUnit test in an IDE

### Front-end tests
Tests for the front-end are written using Selenium with JUnit and Spring Boot's testing suite.
Before running, ensure that the URL in the TDLPage.java file is set to the correct path. They can be run using an IDE.

## Deployment
Before building the project, make sure that the server.port value in application.properties is not in use and that the database credentials in application-prod.properties are correct.

A packaged version of the project can be created by running `mvn package` in the terminal, this will also run the test suite.
The resulting .jar file can be found in the target folder, to run the packaged file, run `java -jar path/to/file.jar` in the terminal. 

**Note:** this does start hosting for the webpage, the webpage can be accessed by opening the index.html file in your browser, or by hosting seperately.

## Built With
* [Maven](https://maven.apache.org/) - Dependency and build tool
* [Spring Boot](https://spring.io/projects/spring-boot) - Handling HTTP requests

## Author
David Barringer

## Jira Roadmap
[https://dbarringer.atlassian.net/jira/software/projects/TDL/boards/4/roadmap](Roadmap)

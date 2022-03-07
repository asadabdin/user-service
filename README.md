# User Service Application with github integration
This project has 2 responsibility
- Curd endpoints on User 
- Call github to fetch repos and languages

## Prerequisite
This Project can be build by maven and it has dockerfile to create the image.
To build using Maven you need `maven` and `JDK-11` installed.
And to create the Docker Image, you need `docker` installed in your build environment


## Build Information
### Maven
````maven
mvn clean package
````
Above command will create the executable jar

## Build Information
### Docker
#### Build Docker image
To build the project please execute
````shell
docker image build -t user-service-homework:latest .
````
This will create a docker image with name `user-service-homework` and tag `latest`

#### Run the docker image
To run the image please execute
````shell
docker run -d --restart=always -p 8081:8081 user-service-homework:latest
````

### Maven
#### Build Maven
To build the project please execute
````mvn
mvn clean package
````

#### Run the docker image
To run the image please execute
````
java -jar target/user-service-homework-0.0.1-SNAPSHOT.jar
````

## Test
Application is configured to pen on port 8081
so, open below url on any browser and you can test the api's using swagger

[Test Api](http://localhost:8081/swagger-ui/index.html)

## Reports
Test report are available on
- Code coverage can be seen by clicking -> [Jacoco Report](http://localhost:63342/user-service-homework/target/site/jacoco/index.html)



***
## Patch
Sample request
````
[
    {"op":"replace","path":"/firstname","value":"HGR"},
    {"op":"replace","path":"/surname","value":"KJRT"}
]
````


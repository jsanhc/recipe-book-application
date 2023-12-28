# Grandma recipes application

> This Application has been developed following a specification document provided by a company as technical test.

> The specification did not provide any application skeleton or structure base.

> The specification did not provide any suggestion about which technologies/frameworks must be used.

> This application has been build totally from scratch with the idea to provide an approach how use different java technologies to build an auto-contained microservice.


## Motivation

An energetic grandmother, who really enjoys cooking, specially for her family, due to personal reasons,
she can't update her recipe collection manually anymore,
This is an electronic recipe book 'eRecipe' to easily manage all the precious family heirlooms.

- [specification document](specs/README.md)

## Built by General rules

- SOLID principles
- TDD
- Structure and code the solution as if it were a big application
- The microservice should be auto-contained. Don't depend on any external services being run.
- Uses `HSQLDB` as in-memory data storage
- Resolve ambiguous requirements defined by the [specification document](specs/README.md)

## Assumptions

- Implement a `family member logic` as defined below:

  - An entity `Member` with a `Many-to-One` relationship to `Recipe` to have a list of members linked with a recipe.

**Member Entity (json representation)**
```json
{
  "members": [
    {
      "member": "GRANDSON"
    },
    {
      "member": "HUSBAND"
    },
    {
      "member": "DAUGHTER"
    }
  ]
}
```

To typify the family members I have created an enum with the below initial values:

  - `HUSBAND`
  - `GRANDSON`
  - `DAUGHTER`

**Recipe payload sample**
```json
{
  "name": "Bittman Chinese Chicken With Bok Choy",
  "link": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
  "portions": 10,
  "prepare_time": 2,
  "meal": "lunch",
  "ingredients": [
    {
      "name": "Chicken Breast",
      "amount": 2,
      "unit": "units"
    },
    {
      "name": "Bok Choy",
      "amount": 1,
      "unit": "units"
    },
    {
      "name": "Sauce",
      "amount": 1,
      "unit": "deciliters"
    }
  ],
  "members": [
    {
      "member": "GRANDSON"
    },
    {
      "member": "HUSBAND"
    },
    {
      "member": "DAUGHTER"
    }
  ],
  "instructions": "Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over."
}
```

> You will find more samples in `samples` folder.

This solution will help me to build a easy implementation solution for `family business rules` please read [rules module readme](recipe-book-application-rules/README.md)

## Libraries and Tools
- [Web Framework] [`SparkJava`](http://sparkjava.com/)
- [DI Framework] [`Google Guice`](https://github.com/google/guice)
- [ORM Framework] [`Hibernate`](https://hibernate.org/)
- [JPA Layer] [`Google Guice-Repository`](https://code.google.com/archive/p/guice-repository/wikis/DevGuide.wiki) which uses [`Spring Data JPA`](https://spring.io/projects/spring-data-jpa) as the underlying data abstraction layer
- [In-Memory Database] [`HSQLDB`](http://hsqldb.org/)
- [Gson] [`Google Gson repository`](`https://github.com/google/gson`)

- Testing frameworks and tools
  - [JUnit](https://junit.org/junit5/)
  - [Assertj](https://assertj.github.io/doc/)
  - [Mockito](https://site.mockito.org/)

## Application modules
- [Recipe book application api](recipe-book-application-api/README.md)
- [Recipe book application datamodel](recipe-book-application-datamodel/README.md)
- [Recipe book application error](recipe-book-application-error/README.md)
- [Recipe book application rules](recipe-book-application-rules/README.md)
- [Recipe book application Server](recipe-book-application-server/README.md)

## Application folders
- `samples` folder: contains a set of payloads for testing purposes
- `specs` folder: contains the original [README](specs/README.md) file provided with the specs to build de application.
- [`postman` folder](postman/README.md): contains the collection descriptor and environment descriptor to be imported in a postman app.

## How it works
This application implements a simple electronic recipe Rest API, it has a simple data model of:

- A `Recipe` with an `One-to-Many` relationship to `Ingredient` and `One-to-Many` with `Member`.

Through Rest API you are able to request for any `CRUD` operation implemented, some of them are not implemented yet.
The `Transaction` annotation provided by `Google Guice repository` all the CRUD operations are ACID-enable.

- `A`tomicity
- `C`onsistency
- `I`solation
- `D`urability

You can find a discovery endpoint to check the Rest APIs published by the `recipe book application`:

- Discovery endpoint [`http://localhost:8080/discovery`](http://localhost:8080/discovery)

### Configuration
The application provides a simple way, using an `application.properties` file as resource, to configure simple parameters to run the application:

| parameter key    | description                                                         | current value |
|------------------|---------------------------------------------------------------------|---------------|
| server.port      | port number to binding the server listener                          | 8080          |
| persistence.unit | persistence unit name, configuration details for the entity manager | recipeBookDb  |

> You will find the `application.properties` file in [Recipe book application server](recipe-book-application-server/README.md)/src/main/resources

## Build the application
This application is built by maven.

**Requisites**

- `Jdk 11` or `Jdk 17`, _if possible use only_ JDK LTS.
- `Maven 3.x.x` or use the maven wrapper provided in the project.

### maven build

_Simple artifacts build (no docker image)_
_maven wrapper_
```shell
./mvnw clean package
```
_maven installed_
```shell
mvn clean package
```
_Build artifacts and docker image_
_maven wrapper_
```shell
./mvnw clean install
```
_maven installed_
```shell
mvn clean install
```
> The process to build the image will run the image to check the health of the built image

## How to run

**As a Java JAR application**
> Build the application first as defined above!!
```shell
java -jar java -jar recipe-book-application-server/target/*-jar-with-dependencies.jar
```

- Go to [welcome page](http://localhost:8080) in the browser

**As a docker container**

> Your development environment needs to support docker containerization, in macOS you can install 
> [Docker desktop](https://desktop.docker.com/mac/main/amd64/Docker.dmg?utm_source=docker&utm_medium=webreferral&utm_campaign=dd-smartbutton&utm_location=module)

In the module [Recipe book application server](recipe-book-application-server/README.md) you will find a `docker` folder,
where lives the `DockerFile` descriptor to build the application image container.

### building the docker container
The docker application image container is build using the maven plugin `docker-maven-plugin` from [`io.fabric`](https://dmp.fabric8.io/)

Use optional goals `site site:stage` if you want to generate the site of the project

_maven wrapper_
```shell
./mvnw clean install
```
_maven installed_
```shell
mvn clean install
```
```shell
[INFO] --- docker-maven-plugin:0.40.3:build (start) @ recipe-book-application-server ---
[INFO] Building tar: /Users/jorge.sanchez-perez/Documents/forgerock/git/gofore/recipes-java-senior-jsp/recipe-book-application-server/target/docker/recipe-book-application-server/tmp/docker-build.tar
[INFO] DOCKER> [recipe-book-application-server:latest]: Created docker-build.tar in 461 milliseconds
[INFO] DOCKER> [recipe-book-application-server:latest]: Built image sha256:a821e
[INFO] DOCKER> recipe-book-application-server: Removed dangling image sha256:67b63
[INFO] 
[INFO] --- docker-maven-plugin:0.40.3:start (start) @ recipe-book-application-server ---
[INFO] DOCKER> [recipe-book-application-server:latest]: Start container 8cf7e2379652
[INFO] DOCKER> [recipe-book-application-server:latest]: Waiting on url http://localhost:8080/health.
[INFO] DOCKER> [recipe-book-application-server:latest]: Waited on url http://localhost:8080/health 5268 ms
[INFO] 
[INFO] --- docker-maven-plugin:0.40.3:stop (stop) @ recipe-book-application-server ---
[INFO] DOCKER> [recipe-book-application-server:latest]: Stop and removed container 8cf7e2379652 after 0 ms
```

**Build only the image: maven goals**
```shell
./mvnw docker:build

[INFO] DOCKER> [recipe-book-application-server:latest]: Created docker-build.tar in 358 milliseconds
[INFO] DOCKER> [recipe-book-application-server:latest]: Built image sha256:a821e
```
```shell
./mvnw docker:start

[INFO] --- docker-maven-plugin:0.40.3:start (default-cli) @ recipe-book-application-server ---
[INFO] DOCKER> [recipe-book-application-server:latest]: Start container 504ae1935544
[INFO] DOCKER> [recipe-book-application-server:latest]: Waiting on url http://localhost:8080/health.
[INFO] DOCKER> [recipe-book-application-server:latest]: Waited on url http://localhost:8080/health 4779 ms
```

> For more information please visit [`dmp.fabric8.io`](https://dmp.fabric8.io/)

_check the local images in your local docker context_
```shell
docker images
```

| REPOSITORY                     | TAG    | IMAGE ID       |
|--------------------------------|--------|----------------|
| recipe-book-application-server | latest | `67b6386757a2` |


> The process to build the image will run the image to check the health of the built image

_Run the container: NO DETACH MODE_
```shell
docker run -p 8080:8080 recipe-book-application-server:latest .
```

- Go to [welcome page](http://localhost:8080) in the browser

_Run the container DETACH_MODE_
```shell
docker run -d -p 8080:8080 recipe-book-application-server:latest .
```
```shell
docker ps
```

| CONTAINER ID   | IMAGE                                 | COMMAND                | CREATED        | STATUS        | PORTS                  | NAMES          |
|----------------|---------------------------------------|------------------------|----------------|---------------|------------------------|----------------|
| `be6b4c61170d` | recipe-book-application-server:latest | "/bin/sh -c 'java -j…" | 47 seconds ago | Up 46 seconds | 0.0.0.0:8080->8080/tcp | friendly_jones |

```shell
docker logs -f be6b4c61170d
```
```shell
........
23:57:20.468 [main] INFO  o.h.e.t.j.p.i.JtaPlatformInitiator - HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
23:57:20.556 [main] INFO  o.j.r.a.c.g.ApplicationGuiceModule$1 - QueryDSL is disabled
23:57:20.577 [main] INFO  o.j.r.a.c.g.ApplicationGuiceModule$1 - Binding repositories...
23:57:20.577 [main] DEBUG o.j.r.a.c.g.ApplicationGuiceModule - Persistence unit name to bond the repositories: recipeBookDb
23:57:20.580 [main] INFO  o.j.r.a.c.g.ApplicationGuiceModule$1 - [org.jsanchez.recipe.application.repositories.RecipeRepository]  attached to [recipeBookDb] and available for injection
23:57:20.581 [main] INFO  o.j.r.a.c.g.ApplicationGuiceModule$1 - [org.jsanchez.recipe.application.repositories.IngredientRepository]  attached to [recipeBookDb] and available for injection
23:57:20.581 [main] INFO  o.j.r.a.c.g.ApplicationGuiceModule$1 - [org.jsanchez.recipe.application.configuration.guice.ApplicationGuiceModule$1] configured
23:57:20.956 [main] INFO  o.j.r.a.w.s.BootstrapApplicationServer - Server starter on http://localhost:8080
23:57:20.990 [Thread-1] INFO  org.eclipse.jetty.util.log - Logging initialized @4838ms to org.eclipse.jetty.util.log.Slf4jLog
23:57:21.073 [Thread-1] INFO  s.e.jetty.EmbeddedJettyServer - == Spark has ignited ...
23:57:21.073 [Thread-1] INFO  s.e.jetty.EmbeddedJettyServer - >> Listening on 0.0.0.0:8080
23:57:21.076 [Thread-1] INFO  org.eclipse.jetty.server.Server - jetty-9.4.48.v20220622; built: 2022-06-21T20:42:25.880Z; git: 6b67c5719d1f4371b33655ff2d047d24e171e49a; jvm 17.0.2+8-86
23:57:21.103 [Thread-1] INFO  org.eclipse.jetty.server.session - DefaultSessionIdManager workerName=node0
23:57:21.103 [Thread-1] INFO  org.eclipse.jetty.server.session - No SessionScavenger set, using defaults
23:57:21.105 [Thread-1] INFO  org.eclipse.jetty.server.session - node0 Scavenging every 660000ms
23:57:21.132 [Thread-1] INFO  o.e.jetty.server.AbstractConnector - Started ServerConnector@53bdcf94{HTTP/1.1, (http/1.1)}{0.0.0.0:8080}
23:57:21.132 [Thread-1] INFO  org.eclipse.jetty.server.Server - Started @4980ms
```
- Go to [welcome page](http://localhost:8080) in the browser

_Stop the application container_
```shell
docker stop [CONTAINER-ID]
```
_Stop all containers_
```shell
docker stop $(docker ps -a -q)
```
# Run the postman test collection

- Go to [Postman folder](postman/README.md)

# Generate site for the project

```shell
./mvnw site site:stage
```

The above command will generate the site including the project's report that were configured in the POM.

- Open the target/stage/index.html in your browser to navigate in the site
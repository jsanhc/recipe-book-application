[Back](../README.md)

# Recipe book application server
This module contains all implementations regarding the application server:

- Application entry point
- Application server bootstrap
- Application configuration
  - Application properties configuration
  - Application persistence configuration
- Controller implementations
  - Generic controller implementation
  - Filter controller implementation
  - Recipe controller implementation
  - Ingredient controller implementation
  - Rules controller implementation
  - Discovery controller implementation
- Requests validation implementation
- Dependency Injection module IoC

## Endpoints
**Application API**

> Discovery endpoint [`http://localhost:8080`](http://localhost:8080)

| api           | operation | path                      | description                                                                                                                      |
|---------------|-----------|---------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| genericApi    | `GET`     | `/`                       | Welcome                                                                                                                          |
| genericApi    | `GET`     | `/health`                 | Health                                                                                                                           |
| discoveryApi  | `GET`     | `/discovery`              | Discovery                                                                                                                        |
| discoveryApi  | `POST`    | `/discovery`              | Discovery                                                                                                                        |
| rulesApi      | `GET`     | `/rules`                  | It is worth rules (simple family rules) see [rules module](../recipe-book-application-rules/README.md)                           |
| recipeApi     | `GET`     | `/recipes/filter`         | Get all recipes filter by partial name order by `prepare time` ascendant as default, optional parameter to sort them `sort=desc` |
| recipeApi     | `POST`    | `/recipes/add`            | Add a recipe                                                                                                                     |
| recipeApi     | `POST`    | `/recipes/update/:id`     | Update a recipe by id                                                                                                            |
| recipeApi     | `DELETE`  | `/recipes/delete/:id`     | Delete a recipe by id                                                                                                            |
| recipeApi     | `DELETE`  | `/recipes/delete/all`     | Delete all recipes                                                                                                               |
| ingredientApi | `GET`     | `/ingredients/all`        | Get all ingredients                                                                                                              |
| ingredientApi | `GET`     | `/ingredients/filter`     | Get all ingredients filter by partial name order by `name` ascendant as default, optional parameter to sort them `sort=desc`     |
| ingredientApi | `POST`    | `/ingredients/add`        | Add a recipe                                                                                                                     |
| ingredientApi | `POST`    | `/ingredients/update/:id` | Update a recipe by id                                                                                                            |
| ingredientApi | `DELETE`  | `/ingredients/delete/:id` | Delete a recipe by id                                                                                                            |
| ingredientApi | `DELETE`  | `/ingredients/delete/all` | Delete all recipes                                                                                                               |

## Configuration
The application provides a simple way, using an `application.properties` file as resource, to configure simple parameters to run the application:

| parameter key    | description                                                         | current value |
|------------------|---------------------------------------------------------------------|---------------|
| server.port      | port number to binding the server listener                          | 8080          |
| persistence.unit | persistence unit name, configuration details for the entity manager | recipeBookDb  |

You will find the `application.properties` file in /src/main/resources

[Back](../README.md)
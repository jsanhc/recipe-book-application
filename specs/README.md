
# Grandma recipes


## Introduction

An energetic grandmother, who really enjoys cooking, specially for her family.

Due to personal reasons, though, she can't update her recipe collection manually anymore.

She has asked to implement an electronic recipe book 'eRecipe' to easily manage all the precious family heirlooms.

## Goal of the project

The goal of this project is to create a microservice using Java and any framework that you think it is appropriate which meets the requirements described below.

We will forward it to grandma. You shouldn't really use more time than a few hours for this, since its for grandma's internal use only.

Also, please add a file named 'README.md' detailing how to run/test the code and anything else that you'd like to share with us.

## General rules

- Apply SOLID principles
- Do TDD
- The requirements have been kept simple on purpose but structure and code the solution as if it were a big application
- The microservice should be auto-contained. Don't depend on any external services being run (ex. if needed, use an in-memory data storage)
- Some requirements have been left ambiguous on purpose so, if you make any assumption, please add a comment

## Requirements

Build a microservice that will handle grandma's recipes. In order to do that you'll need to create the following endpoints.

### Create recipe

This endpoint will receive a recipe and store it into the system.

A recipe must include link, instructions or both. A recipe which have link and instructions empty is not allowed.

Payload:

```json
{
  "reference": "12345A",
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
  "instructions": "Make sauce. Steam bok choy and chicken. Carry on steaming. Pour sauce over."
}
```

* reference (optional): The recipe unique reference number in our system. If not present, the system will generate one.
* name (mandatory): The name of the recipe.
* link (optional): Link to YouTube source.
* portions (optional): Number of portions that produces the recipe.
* prepare_time (required): Time, expressed in hours, estimated to elaborate the recipe.
* meal (required): In which meal should be included this recipe. It's values can be: breakfast, lunch or dinner.
* ingredients (mandatory): List of ingredients required to elaborate the recipe. Each ingredient is structured in the payload as:
  * name (mandatory): The name of the ingredient.
  * amount: Number of units required of this ingredient.
  * unit: Unit used to measure the amount.
* instructions (optional): Description of the steps to follow during the elaboration.

### Search recipe

This endpoint searches for recipes and should be able to:

* Filter by partial name (ex: "chicken").
* Sort by prepare_time (ascending/descending)

### Worth it?

This endpoint, based on the payload and some business rules, will return a value telling you if it's worth the time spent and additional information for a specific recipe.

Payload:

```json
{
  "reference": "12345A",
  "who": "HUSBAND"
}
```

* reference (mandatory): The recipe reference number
* who (optional): The name of the group which is going to eat the food. It can be any of these values: GRANDSON, DAUGHTER, HUSBAND.

Response:

```json
{
  "reference": "12345A",
  "worth": "meh",
  "portions": 10,
  "prepare_time": 2,
}
```

#### Business Rules

A)

```
Given: A recipe that is not stored in our system
When: I check if it's worth from any person
Then: The system returns the worth value 'INVALID'
```

Example payload:

```json
{
  "reference": "XXXXXX",
  "who": "GRANDSON"
}
```

Example response:

```json
{
  "reference": "XXXXXX",
  "worth": "INVALID"
}
```

B)

```
Given: A recipe that is stored in our system
When: the prepare_time is less than 2
  And I check if it's worth for GRANDSON or DAUGHTER
Then: The system returns the worth value 'yeah'
  And the prepare_time substracting 15 minutes (because of the extra motivation)
  And the portions
```

Example payload:

```json
{
  "reference": "12345A",
  "who": "GRANDSON"
}
```

Example response:

```json
{
  "reference": "12345A",
  "worth": "yeah",
  "prepare_time": 1.45,
  "portions": 10
}
```

C)

```
Given: A recipe that is stored in our system
When: the prepare_time is less than 2
  And I check if it's worth for HUSBAND
Then: The system returns the worth value 'yeah'
  And the prepare_time
```

Example payload:

```json
{
  "reference": "12345A",
  "who": "HUSBAND"
}
```

Example response:

```json
{
  "reference": "12345A",
  "worth": "yeah",
  "prepare_time": 2
}
```


D)

```
Given: A recipe that is stored in our system
When: the prepare_time is equals to 2
  And I check if it's worth for GRANDSON or DAUGHTER
Then: The system returns the worth value 'yeah'
  And the prepare_time substracting 10 minutes
  And the portions
```

Example payload:

```json
{
  "reference": "12345A",
  "who": "DAUGHTER"
}
```

Example response:

```json
{
  "reference": "12345A",
  "worth": "yeah",
  "prepare_time": 1.50,
  "portions": 10
}
```

E)

```
Given: A recipe that is stored in our system
When: the prepare_time is equals to 2
  And I check if it's worth for HUSBAND
Then: The system returns the worth value 'meh'
  And the prepare_time adding 10 minutes
```

Example payload:

```json
{
  "reference": "12345A",
  "who": "HUSBAND"
}
```

Example response:

```json
{
  "reference": "12345A",
  "worth": "meh",
  "prepare_time": 2.10,
}
```

F)

```
Given: A recipe that is stored in our system
When: the prepare_time is greater than 2
  And I check if it's worth for GRANDSON
Then: The system returns the worth value 'yeah'
  And the prepare_time substracting 5 minutes
```

Example payload:

```json
{
  "reference": "12345A",
  "who": "GRANDSON"
}
```

Example response:

```json
{
  "reference": "12345A",
  "worth": "yeah",
  "prepare_time": 1.55,
}
```

G)

```
Given: A recipe that is stored in our system
When: the prepare_time is greater than 2
  And I check if it's worth for DAUGHTER
Then: The system returns the worth value 'meh'
  And the prepare_time
```

Example payload:

```json
{
  "reference": "12345A",
  "who": "DAUGHTER"
}
```

Example response:

```json
{
  "reference": "12345A",
  "worth": "meh",
  "prepare_time": 2,
}
```

H)

```
Given: A recipe that is stored in our system
When: the prepare_time is greater than 2
  And I check if it's worth for HUSBAND
Then: The system returns the worth value 'nah'
```

Example payload:

```json
{
  "reference": "12345A",
  "who": "HUSBAND"
}
```

Example response:

```json
{
  "reference": "12345A",
  "worth": "nah"
}
```
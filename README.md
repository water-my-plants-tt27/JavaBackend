# JavaBackend tt-27

**API documentation**

BaseURL:  https://watermyplantsapi.herokuapp.com/api

List of Endpoints:

**Users (auth)**

BaseURL/auth/register (POST) - register a user

(test user is in the example)

Parameters:
| Name         | Type   | Required |
| ------------ | ------ | -------- |
| name         | string | yes      |
| email        | string | yes      |
| phone_number | string | yes      |
| password     | string | yes      |

Example of what to use:

{
name: 'testName',
email: 'test@gmail.com',
phone_number: 'testPhoneNumber',
password: 'testPassword',
}


BaseURL/auth/login (POST) - login a user

(test user is in the example)

Parameters:
| Name     | Type   | Required |
| -------- | ------ | -------- |
| email    | string | yes      |
| password | string | yes      |

Example of what to use:

{
username: "test@gmail.com",
password: "testPassword"
}


**Users**

BaseURL/users (GET) - returns all users

BaseURL/users/:id (PUT) - updates a user

Parameters:
| Name         | Type    | Required | Notes                        |
| ------------ | ------- | -------- | ---------------------------- |
| user_id      | integer | yes      | The id of the user           |
| name         | string  | yes      | The name of the user         |
| phone_number | string  | yes      | The phone number of the user |
| email        | string  | yes      | The email of the user        |



**Plants**

BaseURL/plants (GET) - returns all plants

BaseURL/plants/:id (GET) - gets an individual plant

BaseURL/plants (POST) - add a plant

BaseURL/plants/:id (PUT) - update a plant

BaseURL/plants/:id (DELETE) - delete a plant

Parameters:
| Name           | Type    | Required | Notes                           |
| -------------- | ------- | -------- | ------------------------------- |
| plant_id       | integer | yes      | The id of the plant             |
| plant_name     | string  | yes      | The name of the plant           |
| species_name   | string  | yes      | The name of the species         |
| water_schedule | string  | yes      | frequency of the water schedule |
| light_level    | string  | yes      | light level                     |
| plant_image    | string  | yes      | Image of the plant              |



**My Plants**

BaseURL/my-plants/:id (GET) - returns all my_plants from a user

BaseURL/my-plants (POST) - adds a my_plant

BaseURL/my-plants/:id (PUT) - updates a my_plant

BaseURL/my-plants/:id (DELETE) - removes a my_plant

Parameters:
| Name           | Type    | Required | Notes                           |
| -------------- | ------- | -------- | ------------------------------- |
| my_plant_id    | integer | yes      | The id of my_plants             |
| user_id        | integer | yes      | The id of the user of the plant |
| plant_name     | string  | yes      | The name of the plant           |
| species_name   | string  | yes      | The name of the species         |
| water_schedule | string  | yes      | frequency of the water schedule |
| light_level    | string  | yes      | light level                     |
| week_day_name  | string  | yes      | day of the week                 |

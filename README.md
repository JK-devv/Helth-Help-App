### This is a Health & Help application built using HATEOAS principles

The entry point is : /**start**

Authentication implemented via jwt token

#### Flow:
The entry point to the API is /**start**, and the application returns HAL JSON, which includes hypermedia. The available links depend on the user's role and access token

#### How to use:
You need to get token from /**login** endpoint and after all request will be available for you with token

#### Users:
There is only 2 users with roles :
- MANAGER
- DOCTOR

#### Available endpoints:

- GET: /start
- GET: /stock
- POST: /login - RequestBody:

`{
  "name" : "name",
  "password" : "password"
  }`

- GET: /item/{item-id}

#### For DOCTOR :

- GET: /list-of-patients
- GET: /patient/{patient-id}
- POST: /patient

#### For MANAGER:

- GET: /list-of-food
- GET: /food/{food-id}
- POST: /food

#### Technologies:
- java20
- Spring boot
- Spring Security via jwt
- Spring boot data Jpa
- PostgresDb
- Docker
- HATEOAS
- Junit5# Helth-Help-App

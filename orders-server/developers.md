# Orders Server
This server combines the restaurants, carts, and orders services into a single server.
Each of these should have their own path prefix (e.g. /restaurants), services, controllers, and repositories. 

## Tips
- This server will depend on other services, so during development, you should use ```docker compose up``` in the main 
  repository directory.
- A development docker compose file has been created in this directory, which creates the postgres database you'll need 
  to connect to. The connection detais can be found in the .env file in this directory. 

## Testing authenticated endpoints with postman

Many endpoints within in this server require an authenticated user to be attached to requests. E.g. Initiating an order. 
This server shouldn't need to be involved in any authentication/login flows, and should instead receive a JWT in a 
header, and once configured, spring boot will handle validating JWTs.

At this stage in development, we don't have a real frontend, making it difficult to test authenticated endpoints. To 
solve this, we've got a static testing user for development, which allows us to easily request JWTs for this user. In the 
postman collection json file, authentication is already setup, so import it into postman, and it'll help test 
authenticated endpoints.

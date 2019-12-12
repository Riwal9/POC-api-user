API-USER created with Javalin : https://github.com/tipsy/javalin
Usages : 
- Handle users from a social network with subscriptions
- You can create/modify/delete users or subscriptions

Installation : 
You'll need a .env file at the root of your app precising the database credentials, adress and port. 
The keycloak integration is a Work In Progress.

API routes :  
- GET /users  : get all users
- GET /users/:uuid : get one user identified by :uuid
- PUT /users : update one user
- DELETE /users/:uuid : delete one user identified by :uuid
- GET /users/:uuid/subscriptions : get subscriptions from user identified by :uuid
- GET /users/:uuid/subscriptions/:uuidSubscribedUser : get a specific subscription from user specified by :uuid 
- POST /users/:uuid/subscribe : subscribe one user to another user

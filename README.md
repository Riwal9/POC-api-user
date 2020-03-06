Suivez l'équipe sur Twitter !
<p>
<a href="https://twitter.com/intent/follow?screen_name=LeFaou">
        <img src="https://img.shields.io/twitter/follow/LeFaou?style=social&logo=twitter"
            alt="follow on Twitter" target="_blank"></a>
<a href="https://twitter.com/intent/follow?screen_name=manon_rambaud_1">
    <img src="https://img.shields.io/twitter/follow/manon_rambaud_1?style=social&logo=twitter"
        alt="follow on Twitter"></a>
<a href="https://twitter.com/intent/follow?screen_name=SPerols">
    <img src="https://img.shields.io/twitter/follow/SPerols?style=social&logo=twitter"
        alt="follow on Twitter"></a>
<a href="https://twitter.com/intent/follow?screen_name=martin_gadan">
    <img src="https://img.shields.io/twitter/follow/martin_gadan?style=social&logo=twitter"
        alt="follow on Twitter"></a>
</p>

API-USER created with Javalin : https://github.com/tipsy/javalin and Kotlin : https://kotlinlang.org/

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

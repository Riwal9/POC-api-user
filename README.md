API-USER
Utilisation : 
- gérer les utilisateurs d'instalitre
- gérer les souscriptions des utilisateurs d'instalitre

Routes de l'API : 
- GET /users  : récupère tous les utilisateurs
- GET /users/:uuid : recupère un utilisateur en particulier
- PUT /users : insert un utilisateur
- DELETE /users/:uuid : supprime un utilisateur
- GET /users/:uuid/subscriptions : récupère les souscriptions d'un utilisateur
- GET /users/:uuid/subscriptions/:uuidSubscribedUser : récupère une souscription d'un utilisateur en particulier 
- POST /users/:uuid/subscribe : abonne un utilisateur à un autre utilisateur
- GET /users/check/:token : récupère le token keycloak et le renvoie validé ou non au front
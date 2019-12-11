package utils

import io.github.cdimascio.dotenv.Dotenv

var dotenv = Dotenv.load()

//BDD
val DATABASE_USERNAME = dotenv.get("DATABASE_USERNAME").toString()
val DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD").toString()
val DATABASE_HOST = dotenv.get("DATABASE_HOST").toString()
val DATABASE_PORT = dotenv.get("DATABASE_PORT").toString()
val DATABASE_NAME = dotenv.get("DATABASE_NAME").toString()

//KEYCLOAK
val KEYCLOAK_SERVER_URL = dotenv.get("KEYCLOAK_SERVER_URL").toString()
val KEYCLOAK_REALM = dotenv.get("KEYCLOAK_REALM").toString()
val KEYCLOAK_CLIENT_ID = dotenv.get("KEYCLOAK_CLIENT_ID").toString()
val KEYCLOAK_CLIENT_SECRET = dotenv.get("KEYCLOAK_CLIENT_SECRET").toString()
val KEYCLOAK_USERNAME = dotenv.get("KEYCLOAK_USERNAME").toString()
val KEYCLOAK_PASSWORD = dotenv.get("KEYCLOAK_PASSWORD").toString()

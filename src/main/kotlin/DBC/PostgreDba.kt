package DBC

import java.sql.*
import java.util.Properties

/**
 * Program to list databases in MySQL using Kotlin
 */
object PostgreConnection {
    private lateinit var conn: Connection
    private const val username = "admin" // provide the username
    private const val password = "Mifb}KEO6H5I4>`1Jy(Z" // provide the corresponding password
    private const val databaseHost = "51.159.27.223"
    private const val databasePort = "5156"
    private const val databaseName = "user"

    @JvmStatic
    fun main(args: Array<String>) {
        getConnection()
        executePostgresQuery()
    }

    private fun executePostgresQuery() {
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        stmt = conn.createStatement()
        resultset = stmt!!.executeQuery("SELECT current_database();")
        if (stmt.execute("SELECT current_database();")) {
            resultset = stmt.resultSet
        }
        while (resultset!!.next()) {
            println(resultset.getString("current_database"))
        }
        // release resources
        if (resultset != null) resultset.close() else throw SQLException()
        if (stmt != null) stmt.close() else throw SQLException()
        if (conn != null) conn.close() else throw SQLException()

    }

    //Make Postgre connection
    private fun getConnection() {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        Class.forName("org.postgresql.Driver")
        println("jdbc:postgresql://$username:$password@$databaseHost:$databasePort/${databaseName}")
        conn = DriverManager.getConnection(
            "jdbc:postgresql://$databaseHost:$databasePort/$databaseName",
            connectionProps
        )
    }

    private fun failSql(message: String): Nothing = throw SQLException()
}
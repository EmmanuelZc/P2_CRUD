package com.example.utils

import groovy.sql.Sql
import org.springframework.stereotype.Component

/**
 * Clase de utilidad para interactuar con la base de datos mediante JDBC.
 * Proporciona una conexión a la base de datos MySQL configurada para realizar operaciones SQL.
 */
@Component
class DatabaseUtils {

    /** URL de la base de datos MySQL */
    private final String dbUrl = 'jdbc:mysql://localhost:3306/crud_groovy'

    /** Usuario de la base de datos */
    private final String dbUser = 'root'

    /** Contraseña de la base de datos */
    private final String dbPassword = 'root'

    /** Driver JDBC de MySQL */
    private final String dbDriver = 'com.mysql.cj.jdbc.Driver'

    /**
     * Crea y devuelve una instancia de conexión SQL utilizando las credenciales de configuración.
     *
     * @return una instancia de {@link Sql} para realizar consultas a la base de datos.
     */
    Sql getSql() {
        return Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)
    }
}

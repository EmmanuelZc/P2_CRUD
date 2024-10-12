package com.example.utils
import groovy.sql.Sql
class DatabaseUtils {

    static Sql getSqlInstance() {
        def dbUrl = 'jdbc:mysql://localhost:3306/crud_groovy'
        def dbUser = 'root'
        def dbPassword = 'root'
        def dbDriver = 'com.mysql.cj.jdbc.Driver'
        return Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)
    }

}


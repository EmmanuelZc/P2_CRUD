package com.example.utils

import groovy.sql.Sql
import org.springframework.stereotype.Component

@Component
class DatabaseUtils {

    private final String dbUrl = 'jdbc:mysql://localhost:3306/crud_groovy'
    private final String dbUser = 'root'
    private final String dbPassword = 'root'
    private final String dbDriver = 'com.mysql.cj.jdbc.Driver'

    Sql getSql() {
        return Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)
    }
}

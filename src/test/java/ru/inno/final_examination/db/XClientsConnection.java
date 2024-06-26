package ru.inno.final_examination.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class XClientsConnection {
    @Value("${connection_string}")
    private String connectionString;
    @Value("${user}")
    private String user;
    @Value("${pass}")
    private String pass;

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, user, pass);
    }
}
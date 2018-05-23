package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConeccion {

    private final String url = "jdbc:postgresql://localhost/crm";
    private final String user = "postgres";
    private final String password = "/*INSERTAR AQUI LA CONSTRASEÑA DE POSGRESQL*/";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}

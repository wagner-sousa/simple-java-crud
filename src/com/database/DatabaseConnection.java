package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.exceptions.ConnectionException;

public class DatabaseConnection {
    public static final String DATABASE_NAME = "sesisenai";
    public static final String DATABASE_USER = "sesisenai";
    public static final String DATABASE_PASSWORD = "V0BnbmVyMTIz";
    public static final String DATABASE_HOST = "www.db4free.net";
    public static final String DATABASE_PORT = "3306";
    public static final String DATABASE_URL = "jdbc:mysql://";

    public static final String DATABASE_PREFIX = "sa_";

    public static String error = "";

    public String getDatabaseName() {
        return System.getProperty("database.name");
    }

    /**
     * Opens a connection to the database.
     *
     * @return the connection object
     */
    public static Connection open() throws ConnectionException {
        try {
            return DriverManager.getConnection(getUrlConnection(), DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            error = e.getMessage();
            throw new ConnectionException("\nErro ao realizar conexão com o banco de dados");
        }
    }

    /**
     * Opens a connection to the database.
     *
     * @param debug set to true to enable debug mode
     * @return the database connection
     */
    public static Connection open(Boolean debug) throws Exception {
        if (debug) {
            System.out.println("MODO DEBUG ATIVADO");
            System.out.println("- CONEXÃO UTILIZADA: " + getUrlConnection());
            System.out.println("- USUÁRIO: " + DATABASE_USER);
            System.out.println("- SENHA: " + DATABASE_PASSWORD);

            System.out.println("\n\nConectando ao banco de dados...");
        }

        Connection connection = null;

        try {

            connection = open();

            System.out.println("Conexão realizada com sucesso!");

        } catch (Exception e) {

            System.err.println("\nMais detalhes: " + error);
            
        }

        return connection;
    }

    /**
     * Returns the URL connection string for the database.
     *
     * @return the URL connection string for the database
     */
    public static String getUrlConnection() {
        return DATABASE_URL + DATABASE_HOST + ":" + DATABASE_PORT + "/" + DATABASE_NAME;
    }

    /**
     * Retrieves the prefix used for the database.
     *
     * @return The prefix used for the database.
     */
    public static String getPrefix() {
        return DATABASE_PREFIX;
    }

}

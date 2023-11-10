package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.exceptions.ConnectionException;
import com.properties.ApplicationProperties;

public class DatabaseConnection {

    public static String error = "";

    /**
     * Opens a connection to the database.
     *
     * @return the connection object
     */
    public static Connection open() throws ConnectionException {
        try {
            return DriverManager.getConnection(getUrlConnection(), getDatabaseUser(), getDatabasePassword());
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
            System.out.println("- USUÁRIO: " + getDatabaseUser());
            System.out.println("- SENHA: " + getDatabasePassword());

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

    public static String getDatabaseName() {
        return ApplicationProperties.getProp("database.name");
    }

    private static String getDatabaseUser() {
        return ApplicationProperties.getProp("database.user");
    }

    private static String getDatabasePassword() {
        return ApplicationProperties.getProp("database.password");
    }

    public static String getDatabasePrefix() {
        return ApplicationProperties.getProp("database.prefix");
    }

    private static String getDatabaseHost() {
        return ApplicationProperties.getProp("database.host");
    }

    private static String getDatabasePort() {
        return ApplicationProperties.getProp("database.port");
    }

    private static String getDatabaseConnection() {
        return ApplicationProperties.getProp("database.connection");
    }


    /**
     * Returns the URL connection string for the database.
     *
     * @return the URL connection string for the database
     */
    public static String getUrlConnection() {
        return getDatabaseConnection() + "://" + getDatabaseHost() + ":" + getDatabasePort() + "/" + getDatabaseName();
    }

    public static String mountTableName(String table) {
        return getDatabasePrefix() + table;
    }
}

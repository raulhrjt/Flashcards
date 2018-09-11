package com.queens.flashcards;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that handles Database functions required across the application.
 */
public class Databases {

    //region Constants

    /**
     * String for setting up HSQLDB connections.
     */
    public final static String HSQLDB_CONN_STRING = "jdbc:hsqldb:file:";

    /**
     * Default user ID for HSQLDB connections.
     */
    public final static String HSQLDB_USER = "SA";

    /**
     * Default password for HSQLDB connections.
     */
    public final static String HSQLDB_PASSWORD = "";

    //endregion

    //region Class Members

    private static String dbName = "flashcard_db.script";

    //endregion

    //region Public Methods

    /**
     * Initializes the database and sets the file path name.
     * @param name The file path to the database.
     */
    public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbName = name;
    }

    /**
     * Gets the path to the database file.
     * @return The path to the database file.
     */
    public static String getDBPathName() {
        return dbName;
    }

    /**
     * Initializes all tables in the database.
     * @throws SQLException Thrown when SQL cries.
     * @throws IOException Thrown when the file system cries.
     */
    public static void initDBSchema(Context context, String schemaPath) throws SQLException, IOException {

        try (final Connection c = DriverManager.getConnection(HSQLDB_CONN_STRING + dbName, HSQLDB_USER, HSQLDB_PASSWORD)) {
            String sql = readFileToString(schemaPath, context);
            Statement s = c.createStatement();
            s.executeUpdate(sql);
        }

    }

    //endregion

    //region Helper Methods

    /**
     * Loads the contents of the file into a string.
     * @param filepath The path to the file to read.
     * @return The contents of the file as a string.
     * @throws IOException Thrown when the file system cries.
     */
    private static String readFileToString(String filepath, Context context) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filepath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        }

        return sb.toString();
    }

    //endregion

}

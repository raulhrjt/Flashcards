package com.queens.flashcards;

import android.content.Context;
import android.provider.MediaStore;

import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestUtils {
    //private static final File DB_SRC = new File("flashcard_db.script.script");
    private static final File DB_SRC = new File("src/main/assets/db/flashcard_db.script");
    private static final File DB_SCHEMA = new File("src/main/assets/db/schema.sql");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        final File targetSql = File.createTempFile("temp-schema", ".sql");
        Files.copy(DB_SRC, target);
        Files.copy(DB_SCHEMA, targetSql);
        Databases.setDBPathName(target.getAbsolutePath().replace(".script", ""));

        try {
            initDBSchema(DB_SCHEMA);
        } catch (SQLException | IOException e) {
            System.out.println("Failed to initialize data\n\nMessage:\n" + e.getMessage());
        }

        return target;
    }

    /**
     * Initializes all tables in the database.
     * @throws SQLException Thrown when SQL cries.
     * @throws IOException Thrown when the file system cries.
     */
    public static void initDBSchema(File schemaFile) throws SQLException, IOException {

        try (final Connection c = DriverManager.getConnection(Databases.HSQLDB_CONN_STRING + Databases.getDBPathName(), Databases.HSQLDB_USER, Databases.HSQLDB_PASSWORD)) {
            String sql = readFileToString(schemaFile);
            Statement s = c.createStatement();
            s.executeUpdate(sql);
        }

    }

    //endregion

    //region Helper Methods

    /**
     * Loads the contents of the file into a string.
     * @param file The file to read.
     * @return The contents of the file as a string.
     * @throws IOException Thrown when the file system cries.
     */
    private static String readFileToString(File file) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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

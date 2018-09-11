package com.queens.flashcards.Persistence.hsqldb;

import android.provider.ContactsContract;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.queens.flashcards.Databases;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Persistence.Exception.DuplicateRecordException;
import com.queens.flashcards.Persistence.Exception.PersistenceException;
import com.queens.flashcards.Persistence.Interfaces.CategoryPersistence;

public class CategoryPersistenceHSQLDB implements CategoryPersistence {

    //region Constants

    private static final String TBL_CATEGORY = "Category";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";

    //endregion

    //region Members

    private final Connection c;

    //endregion

    //region Constructors

    /**
     * Initializes a new instance of a CategoryPersistenceHSQLDB.
     * @param dbPath The file path to the database file.
     */
    public CategoryPersistenceHSQLDB(String dbPath) {
        try {
            this.c = DriverManager.getConnection(Databases.HSQLDB_CONN_STRING + dbPath, Databases.HSQLDB_USER, Databases.HSQLDB_PASSWORD);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    //endregion

    //region CategoryPersistence Methods

    /**
     * Queries the database for all records in the Category table.
     * @return The list of all Categories in the database.
     */
    @Override
    public List<Category> getAllCategories() {
        final List<Category> categories = new ArrayList<>();

        try {
            final Statement s = c.createStatement();
            final ResultSet rs = s.executeQuery("SELECT * FROM " + TBL_CATEGORY);

            while (rs.next()) {
                final Category category = resultSetToCategory(rs);
                categories.add(category);
            }

            rs.close();
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return categories;
    }

    /**
     * Queries the database for a Category with the specified ID.
     * @param id The ID of the Category to search for.
     * @return The Category with the specified ID.
     */
    @Override
    public Category getCategoryById(long id) {
        Category category = null;

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_CATEGORY + " WHERE " + COL_ID + "=?");
            s.setString(1, String.valueOf(id));

            // Build the Category object
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                category = resultSetToCategory(rs);

                // Ensure only one Category was found (ID is unique)
                if (!rs.isLast())
                    throw new PersistenceException(new DuplicateRecordException());
            }

            rs.close();
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return category;
    }

    /**
     * Queries the database for a Category with the specified name.
     * @param name The name of the Category to search for.
     * @return The Category with the specified name.
     */
    @Override
    public Category getCategoryByName(String name) {
        Category category = null;

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_CATEGORY + " WHERE " + COL_NAME + "=?");
            s.setString(1, name);

            // Build the Category object & ensure uniqueness
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                category = resultSetToCategory(rs);

                // Ensure only one Category was found (name is unique)
                if (!rs.isLast())
                    throw new PersistenceException(new DuplicateRecordException());
            }

            rs.close();
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return category;
    }

    /**
     * Inserts a new Category record in the Category table.
     * @param category The Category to persist.
     * @return The inserted Category with an updated ID.
     */
    @Override
    public Category createCategory(Category category) {
        try {
            // Insert the new record
            PreparedStatement s = c.prepareStatement("INSERT INTO " + TBL_CATEGORY + "(" + COL_NAME + ") VALUES(?)", new String[] {COL_ID});
            s.setString(1, category.getName());
            s.executeUpdate();

            // Get the new ID of the Category
            ResultSet rs = s.getGeneratedKeys();
            if (rs != null && rs.next()) {
                category.setId(rs.getLong(COL_ID));
                rs.close();
            }
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return category;
    }

    /**
     * Deletes the a record from the Category table with ID of the specified Category.
     * @param categoryToRemove The Category to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteCategory(Category categoryToRemove) {
        boolean deleted;

        try {
            PreparedStatement s = c.prepareStatement("DELETE FROM " + TBL_CATEGORY + " WHERE " + COL_ID + "=?");
            s.setLong(1, categoryToRemove.getId());

            deleted = s.executeUpdate() > 0;
            s.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return deleted;
    }

    /**
     * Updates the existing Category record in the database.
     * @param category The Category to update.
     * @return True if the update was successful, false otherwise.
     */
    @Override
    public boolean updateCategory(Category category) {
        boolean updated;

        try {
            PreparedStatement s = c.prepareStatement("UPDATE " + TBL_CATEGORY +
                                                        " SET " + COL_NAME + "=? WHERE " + COL_ID + "=?");
            s.setString(1, category.getName());
            s.setLong(2, category.getId());

            updated = s.executeUpdate() > 0;
            s.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return updated;
    }

    //endregion

    //region Helper Methods

    /**
     * Creates a Category from the data in a ResultSet.
     * @param rs The ResultSet to pull Category data from.
     * @return A Category object with the ResultSet data.
     * @throws SQLException Thrown when the ResultSet contains invalid data.
     */
    private Category resultSetToCategory(final ResultSet rs) throws SQLException {
        final long id = rs.getLong(COL_ID);
        final String name = rs.getString(COL_NAME);
        return new Category(name, id);
    }

    //endregion

}

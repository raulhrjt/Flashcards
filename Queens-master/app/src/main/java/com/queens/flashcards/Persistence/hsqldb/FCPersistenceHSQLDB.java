package com.queens.flashcards.Persistence.hsqldb;

import com.queens.flashcards.Databases;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.FlashcardCategory;
import com.queens.flashcards.Persistence.Exception.PersistenceException;
import com.queens.flashcards.Persistence.Interfaces.FCPersistence;
import com.queens.flashcards.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FCPersistenceHSQLDB implements FCPersistence {

    //region Constants

    // Flashcard_Category table
    private static final String TBL_FC = "Flashcard_Category";
    private static final String COL_ID = "ID";
    private static final String COL_FLASHCARDID = "FlashcardID";
    private static final String COL_CATEGORYID = "CategoryID";

    //endregion

    //region Members

    private final Connection c;

    //endregion

    //region Constructors

    public FCPersistenceHSQLDB(String dbPath) {
        try {
            this.c = DriverManager.getConnection(Databases.HSQLDB_CONN_STRING + dbPath, Databases.HSQLDB_USER, Databases.HSQLDB_PASSWORD);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    //endregion

    //region FCPersistence Methods

    @Override
    public List<FlashcardCategory> getAllFlashcardCategories() {
        List<FlashcardCategory> flashcardCategories = new ArrayList<>();

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_FC);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                final long flashcardId = rs.getLong(COL_FLASHCARDID);
                final long categoryId = rs.getLong(COL_CATEGORYID);
                flashcardCategories.add(new FlashcardCategory(flashcardId, categoryId));
            }

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return flashcardCategories;
    }

    @Override
    public List<Long> getFlashcardsInCategory(long category) {
        List<Long> flashcardIds = new ArrayList<>();

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_FC + " WHERE " + COL_CATEGORYID + "=?");
            s.setLong(1, category);

            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                final long id = rs.getLong(COL_FLASHCARDID);
                flashcardIds.add(id);
            }

            rs.close();
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return flashcardIds;
    }

    @Override
    public List<Long> getCategoriesWithFlashcard(long flashcard) {
        List<Long> categoryIds = new ArrayList<>();

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_FC + " WHERE " + COL_FLASHCARDID + "=?");
            s.setLong(1, flashcard);

            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                final long id = rs.getLong(COL_CATEGORYID);
                categoryIds.add(id);
            }

            rs.close();
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return categoryIds;
    }

    @Override
    public boolean createFlashcardCategory(long flashcard, long category) {
        boolean added;

        try {
            PreparedStatement s = c.prepareStatement("INSERT INTO " + TBL_FC + "("
                                                        + COL_FLASHCARDID + ", "
                                                        + COL_CATEGORYID + ") VALUES (?, ?)");
            s.setLong(1, flashcard);
            s.setLong(2, category);

            added = s.executeUpdate() > 0;
            s.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return added;
    }

    @Override
    public boolean deleteFlashcardCategory(long flashcard, long category) {
        boolean removed;

        try {
            PreparedStatement s = c.prepareStatement("DELETE FROM " + TBL_FC + " WHERE "
                                                        + COL_FLASHCARDID + "=? AND "
                                                        + COL_CATEGORYID + "=?");
            s.setLong(1, flashcard);
            s.setLong(2, category);

            removed = s.executeUpdate() > 0;
            s.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return removed;
    }

    @Override
    public int removeFlashcard(long flashcard) {
        int removed;

        try {
            PreparedStatement s = c.prepareStatement("DELETE FROM " + TBL_FC + " WHERE " + COL_FLASHCARDID + "=?");
            s.setLong(1, flashcard);

            removed = s.executeUpdate();
            s.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return removed;
    }

    @Override
    public int removeCategory(long category) {
        int removed;

        try {
            PreparedStatement s = c.prepareStatement("DELETE FROM " + TBL_FC + " WHERE " + COL_CATEGORYID + "=?");
            s.setLong(1, category);

            removed = s.executeUpdate();
            s.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return removed;
    }

    //endregion

}

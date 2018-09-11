package com.queens.flashcards.Persistence.hsqldb;

import com.queens.flashcards.Databases;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Persistence.Exception.DuplicateRecordException;
import com.queens.flashcards.Persistence.Exception.PersistenceException;
import com.queens.flashcards.Persistence.Interfaces.FATextPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FATextPersistenceHSQLDB implements FATextPersistence {

    //region Constants

    private static final String TBL_FLASHCARDTEXTANSWER = "FlashcardTextAnswer";
    private static final String COL_ID = "ID";
    private static final String COL_FLASHCARDID = "FlashcardID";
    private static final String COL_ANSWER = "Answer";

    //endregion

    //region Members

    private final Connection c;

    //endregion

    //region Constructors

    public FATextPersistenceHSQLDB(String dbPath) {
        try {
            this.c = DriverManager.getConnection(Databases.HSQLDB_CONN_STRING + dbPath, Databases.HSQLDB_USER, Databases.HSQLDB_PASSWORD);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    //endregion

    //region FATextPersistence Methods

    @Override
    public FlashcardTextAnswer getTextAnswerFor(Flashcard flashcard) {
        FlashcardTextAnswer answer = null;

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_FLASHCARDTEXTANSWER + " WHERE " + COL_FLASHCARDID + "=?");
            s.setLong(1, flashcard.getId());

            // Run query and ensure only one exists
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                answer = resultSetToTextAnswer(rs);
                if (!rs.isLast())
                    throw new PersistenceException(new DuplicateRecordException());
            }

            rs.close();
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return answer;
    }

    @Override
    public FlashcardTextAnswer createTextAnswerFor(FlashcardTextAnswer answer, Flashcard flashcard) {
        try {
            PreparedStatement s = c.prepareStatement("INSERT INTO " + TBL_FLASHCARDTEXTANSWER + "("
                                                        + COL_FLASHCARDID + ", "
                                                        + COL_ANSWER + ") VALUES(?, ?)", new String[] {COL_ID});
            s.setLong(1, flashcard.getId());
            s.setString(2, answer.getAnswer());

            // Run insert and get new answer ID
            s.executeUpdate();
            ResultSet rs = s.getGeneratedKeys();
            if (rs.next()) {
                final long id = rs.getLong(COL_ID);
                answer.setId(id);
                rs.close();
            }
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return answer;
    }

    @Override
    public boolean deleteTextAnswerFor(Flashcard flashcard) {
        boolean deleted;

        try {
            PreparedStatement s = c.prepareStatement("DELETE FROM " + TBL_FLASHCARDTEXTANSWER + " WHERE " + COL_FLASHCARDID + "=?");
            s.setLong(1, flashcard.getId());

            deleted = s.executeUpdate() > 0;
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return deleted;
    }

    @Override
    public boolean updateTextAnswerFor(FlashcardTextAnswer answer, Flashcard flashcard) {
        boolean updated;

        try {
            PreparedStatement s = c.prepareStatement("UPDATE " + TBL_FLASHCARDTEXTANSWER + " SET "
                                                        + COL_ANSWER + "=? WHERE " + COL_FLASHCARDID  + "=?");
            s.setString(1, answer.getAnswer());
            s.setLong(2, flashcard.getId());

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
     * Creates a FlashcardTextAnswer from the data in a ResultSet.
     * @param rs The ResultSet to pull FlashcardTextAnswer data from.
     * @return A FlashcardTextAnswer object with the ResultSet data.
     * @throws SQLException Thrown when the ResultSet contains invalid data.
     */
    private FlashcardTextAnswer resultSetToTextAnswer(ResultSet rs) throws SQLException {
        final long id = rs.getLong(COL_ID);
        final String answer = rs.getString(COL_ANSWER);
        return new FlashcardTextAnswer(answer, id);
    }

    //endregion

}

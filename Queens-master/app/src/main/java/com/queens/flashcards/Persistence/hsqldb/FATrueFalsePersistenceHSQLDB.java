package com.queens.flashcards.Persistence.hsqldb;

import com.queens.flashcards.Databases;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Persistence.Exception.DuplicateRecordException;
import com.queens.flashcards.Persistence.Exception.PersistenceException;
import com.queens.flashcards.Persistence.Interfaces.FATrueFalsePersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FATrueFalsePersistenceHSQLDB implements FATrueFalsePersistence {

    //region Constants

    private static final String TBL_FLASHCARDTFANSWER = "FlashcardTFAnswer";
    private static final String COL_ID = "ID";
    private static final String COL_FLASHCARDID = "FlashcardID";
    private static final String COL_ANSWER = "Answer";

    //endregion

    //region Members

    private final Connection c;

    //endregion

    //region Constructors

    public FATrueFalsePersistenceHSQLDB(String dbPath) {
        try {
            this.c = DriverManager.getConnection(Databases.HSQLDB_CONN_STRING + dbPath, Databases.HSQLDB_USER, Databases.HSQLDB_PASSWORD);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    //endregion

    //region FATrueFalsePersistence Methods

    @Override
    public FlashcardTFAnswer getTFAnswerFor(Flashcard flashcard) {
        FlashcardTFAnswer answer = null;

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_FLASHCARDTFANSWER + " WHERE " + COL_FLASHCARDID + "=?");
            s.setLong(1, flashcard.getId());

            // Run query and ensure only one exists
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                answer = resultSetToTFAnswer(rs);
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
    public FlashcardTFAnswer createTFAnswerFor(FlashcardTFAnswer answer, Flashcard flashcard) {
        try {
            PreparedStatement s = c.prepareStatement("INSERT INTO " + TBL_FLASHCARDTFANSWER + "("
                                                        + COL_FLASHCARDID + ", "
                                                        + COL_ANSWER +") VALUES(?, ?)", new String[] {COL_ID});
            s.setLong(1, flashcard.getId());
            s.setBoolean(2, answer.getAnswerIsTrue());

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
    public boolean deleteTFAnswerFor(Flashcard flashcard) {
        boolean deleted;

        try {
            PreparedStatement s = c.prepareStatement("DELETE FROM " + TBL_FLASHCARDTFANSWER + " WHERE " + COL_FLASHCARDID + "=?");
            s.setLong(1, flashcard.getId());

            deleted = s.executeUpdate() > 0;
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return deleted;
    }

    @Override
    public boolean updateTFAnswerFor(FlashcardTFAnswer answer, Flashcard flashcard) {
        boolean updated;

        try {
            PreparedStatement s = c.prepareStatement("UPDATE " + TBL_FLASHCARDTFANSWER + " SET "
                    + COL_ANSWER + "=? WHERE " + COL_FLASHCARDID  + "=?");
            s.setBoolean(1, answer.getAnswerIsTrue());
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
     * Creates a FlashcardTFAnswer from the data in a ResultSet.
     * @param rs The ResultSet to pull FlashcardTFAnswer data from.
     * @return A FlashcardTFAnswer object with the ResultSet data.
     * @throws SQLException Thrown when the ResultSet contains invalid data.
     */
    private FlashcardTFAnswer resultSetToTFAnswer(ResultSet rs) throws SQLException {
        final long id = rs.getLong(COL_ID);
        final boolean answer = rs.getBoolean(COL_ANSWER);
        return new FlashcardTFAnswer(answer, id);
    }

    //endregion

}

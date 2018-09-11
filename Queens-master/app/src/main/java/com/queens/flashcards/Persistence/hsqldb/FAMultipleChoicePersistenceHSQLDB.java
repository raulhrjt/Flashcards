package com.queens.flashcards.Persistence.hsqldb;

import com.queens.flashcards.Databases;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Persistence.Exception.DuplicateRecordException;
import com.queens.flashcards.Persistence.Exception.PersistenceException;
import com.queens.flashcards.Persistence.Interfaces.FAMultipleChoicePersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FAMultipleChoicePersistenceHSQLDB implements FAMultipleChoicePersistence {

    //region Constants

    private static final String TBL_FLASHCARDMCANSWER = "FlashcardMCAnswer";
    private static final String COL_ID = "ID";
    private static final String COL_FLASHCARDID = "FlashcardID";
    private static final String COL_ANSWER = "Answer";

    //endregion

    //region Members

    private final Connection c;
    private WrongAnswerPersistence wrongAnswerPersistence;

    //endregion

    //region Constructors

    public FAMultipleChoicePersistenceHSQLDB(String dbPath) {
        try {
            this.c = DriverManager.getConnection(Databases.HSQLDB_CONN_STRING + dbPath, Databases.HSQLDB_USER, Databases.HSQLDB_PASSWORD);
            this.wrongAnswerPersistence = new WrongAnswerPersistence(dbPath);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    //endregion

    //region FAMultipleChoicePersistence Methods

    @Override
    public FlashcardMCAnswer getMCAnswerFor(Flashcard flashcard) {
        FlashcardMCAnswer answer = null;

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_FLASHCARDMCANSWER + " WHERE " + COL_FLASHCARDID + "=?");
            s.setLong(1, flashcard.getId());

            // Run query and ensure only one exists
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                answer = resultSetToMCAnswer(rs);
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
    public FlashcardMCAnswer createMCAnswerFor(FlashcardMCAnswer answer, Flashcard flashcard) {
        try {
            PreparedStatement s = c.prepareStatement("INSERT INTO " + TBL_FLASHCARDMCANSWER + "("
                                                        + COL_FLASHCARDID  + ", "
                                                        + COL_ANSWER + ") VALUES(?, ?)", new String[] {COL_ID});
            s.setLong(1, flashcard.getId());
            s.setString(2, answer.getAnswer());

            // Run insert
            s.executeUpdate();

            // Get new answer ID
            ResultSet rs = s.getGeneratedKeys();
            if (rs.next()) {
                final long id = rs.getLong(COL_ID);
                answer.setId(id);
                rs.close();
            }
            s.close();

            // Add wrong answers
            this.wrongAnswerPersistence.createWrongAnswersFor(answer.getWrongAnswers(), answer.getId());

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return answer;
    }

    @Override
    public boolean deleteMCAnswerFor(Flashcard flashcard) {
        boolean deleted;

        try {
            PreparedStatement s = c.prepareStatement("DELETE FROM " + TBL_FLASHCARDMCANSWER + " WHERE " + COL_FLASHCARDID + "=?");
            s.setLong(1, flashcard.getId());

            // Delete from wrong answers table
            FlashcardAnswer answer = flashcard.getAnswer();
            if (answer != null)
                this.wrongAnswerPersistence.deleteWrongAnswersFor(answer.getId());

            deleted = s.executeUpdate() > 0;
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return deleted;
    }

    @Override
    public boolean updateMCAnswerFor(FlashcardMCAnswer answer, Flashcard flashcard) {
        boolean updated;

        try {
            PreparedStatement s = c.prepareStatement("UPDATE " + TBL_FLASHCARDMCANSWER + " SET "
                    + COL_ANSWER + "=? WHERE " + COL_FLASHCARDID  + "=?");
            s.setString(1, answer.getAnswer());
            s.setLong(2, flashcard.getId());

            // Update wrong answers table
            this.wrongAnswerPersistence.deleteWrongAnswersFor(answer.getId());
            this.wrongAnswerPersistence.createWrongAnswersFor(answer.getWrongAnswers(), answer.getId());

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
     * Creates a FlashcardMCAnswer from the data in a ResultSet.
     * @param rs The ResultSet to pull FlashcardMCAnswer data from.
     * @return A FlashcardMCAnswer object with the ResultSet data.
     * @throws SQLException Thrown when the ResultSet contains invalid data.
     */
    private FlashcardMCAnswer resultSetToMCAnswer(ResultSet rs) throws SQLException {
        final long id = rs.getLong(COL_ID);
        final String answer = rs.getString(COL_ANSWER);
        final List<String> wrongAnswers = this.wrongAnswerPersistence.getWrongAnswersFor(id);
        return new FlashcardMCAnswer(answer, wrongAnswers, id);
    }

    //endregion

    /**
     * Inner class to manage the table of associated wrong MC answers.
     */
    class WrongAnswerPersistence {

        //region Constants

        private static final String TBL_WRONGANSWERS = "FlashcardMCWrongAnswers";
        private static final String COL_ID = "ID";
        private static final String COL_FLASHCARDANSWERID = "FlashcardMCAnswerID";
        private static final String COL_ANSWER = "Answer";

        //endregion

        //region Members

        private final Connection c;

        //endregion

        //region Constructors

        public WrongAnswerPersistence(String dbPath) {
            try {
                this.c = DriverManager.getConnection(Databases.HSQLDB_CONN_STRING + dbPath, Databases.HSQLDB_USER, Databases.HSQLDB_PASSWORD);
            } catch (final SQLException e) {
                throw new PersistenceException(e);
            }
        }

        //endregion

        //region Public Methods

        public List<String> getWrongAnswersFor(long answerID) {
            List<String> wrongAnswers;

            try {
                PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_WRONGANSWERS + " WHERE " + COL_FLASHCARDANSWERID + "=?");
                s.setLong(1, answerID);

                ResultSet rs = s.executeQuery();
                wrongAnswers = resultSetToWrongAnswers(rs);

                rs.close();
                s.close();

            } catch (final SQLException e) {
                throw new PersistenceException(e);
            }

            return wrongAnswers;
        }

        public List<String> createWrongAnswersFor(List<String> wrongAnswers, long answerID) {
            try {
                for (String wrongAnswer : wrongAnswers) {
                    PreparedStatement s = c.prepareStatement("INSERT INTO " + TBL_WRONGANSWERS + "("
                                                                + COL_FLASHCARDANSWERID + ", "
                                                                + COL_ANSWER + ") VALUES(?, ?)");
                    s.setLong(1, answerID);
                    s.setString(2, wrongAnswer);

                    s.executeUpdate();
                    s.close();
                }
            } catch (final SQLException e) {
                throw new PersistenceException(e);
            }

            return wrongAnswers;
        }

        public boolean deleteWrongAnswersFor(long answerID) {
            boolean deleted;

            try {
                PreparedStatement s = c.prepareStatement("DELETE FROM " + TBL_WRONGANSWERS + " WHERE " + COL_FLASHCARDANSWERID + "=?");
                s.setLong(1, answerID);

                deleted = s.executeUpdate() > 0;
                s.close();

            } catch (final SQLException e) {
                throw new PersistenceException(e);
            }

            return deleted;
        }

        //endregion

        //region Helper Methods

        /**
         * Creates a list of wrong answers from a ResultSet.
         * @param rs The ResultSet to pull wrong answers from.
         * @return A list of wrong answers.
         * @throws SQLException Thrown when the ResultSet contains invalid data.
         */
        private List<String> resultSetToWrongAnswers(ResultSet rs) throws SQLException {
            final List<String> wrongAnswers = new ArrayList<>();

            while (rs.next()) {
                final String ans = rs.getString(COL_ANSWER);
                wrongAnswers.add(ans);
            }

            return wrongAnswers;
        }

        //endregion

    }

}

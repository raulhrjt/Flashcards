package com.queens.flashcards.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.queens.flashcards.Databases;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Persistence.Exception.DuplicateRecordException;
import com.queens.flashcards.Persistence.Exception.PersistenceException;
import com.queens.flashcards.Persistence.Interfaces.FlashcardPersistence;

import org.w3c.dom.Text;

public class FlashcardPersistenceHSQLDB implements FlashcardPersistence {

    /**
     * Enum to map FlashcardAnswerType to the value of the AnswerType column.
     * <p>
     *     This is used to discern the type of FlashcardAnswer to save/create at run-time.
     * </p>
     */
    private enum FlashcardAnswerType {
        TextAnswer(0),
        TrueFalseAnswer(1),
        MultipleChoiceAnswer(2);

        private int index;

        FlashcardAnswerType(int i) {
            this.index = i;
        }

        public int getIndex() {
            return this.index;
        }

        public static FlashcardAnswerType fromInt(int i) {
            if (i == TextAnswer.index)
                return TextAnswer;

            if (i == TrueFalseAnswer.index)
                return TrueFalseAnswer;

            if (i == MultipleChoiceAnswer.index)
                return MultipleChoiceAnswer;

            throw new IllegalStateException();
        }

        public static FlashcardAnswerType fromFlashcardAnswer(FlashcardAnswer answer) {
            if (answer instanceof FlashcardTextAnswer)
                return TextAnswer;

            if (answer instanceof FlashcardTFAnswer)
                return TrueFalseAnswer;

            if (answer instanceof FlashcardMCAnswer)
                return MultipleChoiceAnswer;

            throw new IllegalStateException();
        }
    }

    //region Constants

    // Flashcard Table
    private static final String TBL_FLASHCARD = "Flashcard";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_QUESTION = "Question";
    private static final String COL_ANSWERTYPE = "AnswerType";
    private static final String COL_IMAGEFILE = "ImageFile";

    //endregion

    //region Members

    private final Connection c;

    //endregion

    //region Constructors

    /**
     * Initializes a new instance of a CategoryPersistenceHSQLDB.
     * @param dbPath The file path to the database file.
     */
    public FlashcardPersistenceHSQLDB(String dbPath) {
        try {
            this.c = DriverManager.getConnection(Databases.HSQLDB_CONN_STRING + dbPath, Databases.HSQLDB_USER, Databases.HSQLDB_PASSWORD);
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    //endregion

    //region FlashcardPersistence Methods

    /**
     * Queries the database for all Flashcard records in the Flashcard table.
     * @return The list of all Flashcards in the database.
     */
    @Override
    public List<Flashcard> getAllFlashcards() {
        List<Flashcard> flashcards = new ArrayList<>();

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_FLASHCARD);

            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                final Flashcard flashcard = resultSetToFlashcard(rs);
                flashcards.add(flashcard);
            }

            rs.close();
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return flashcards;
    }

    @Override
    public Flashcard getFlashcardById(long id) {
        Flashcard flashcard = null;

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_FLASHCARD + " WHERE " + COL_ID + "=?");
            s.setLong(1, id);

            // Build Flashcard object
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                flashcard = resultSetToFlashcard(rs);

                // Ensure only one exists
                if (!rs.isLast())
                    throw new PersistenceException(new DuplicateRecordException());
            }

            rs.close();
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return flashcard;
    }

    @Override
    public Flashcard getFlashcardByName(String name) {
        Flashcard flashcard = null;

        try {
            PreparedStatement s = c.prepareStatement("SELECT * FROM " + TBL_FLASHCARD + " WHERE " + COL_NAME + "=?");
            s.setString(1, name);

            // Build Flashcard object
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                flashcard = resultSetToFlashcard(rs);

                // Ensure only one exists
                if (!rs.isLast())
                    throw new PersistenceException(new DuplicateRecordException());
            }

            rs.close();
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return flashcard;
    }

    /**
     * Inserts a new Flashcard record into the database with the Flashcard's data.
     * @param flashcard The new Flashcard to persist.
     * @return The Flashcard inserted into the database with its new ID.
     */
    @Override
    public Flashcard createFlashcard(Flashcard flashcard) {
        try {
            // Get the card's answer type to store
            FlashcardAnswerType type = FlashcardAnswerType.fromFlashcardAnswer(flashcard.getAnswer());

            // Insert the new record
            PreparedStatement s = c.prepareStatement("INSERT INTO " + TBL_FLASHCARD + "("
                                                        + COL_NAME + ", "
                                                        + COL_QUESTION + ", "
                                                        + COL_ANSWERTYPE + ", "
                                                        + COL_IMAGEFILE + ") VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            s.setString(1, flashcard.getName());
            s.setString(2, flashcard.getQuestion());
            s.setInt(3, type.getIndex());
            s.setString(4, flashcard.getImageLocation());
            s.executeUpdate();

            // Get the new ID of the Flashcard
            ResultSet rs = s.getGeneratedKeys();
            if (rs != null && rs.next()) {
                flashcard.setId(rs.getLong(COL_ID));
                rs.close();
            }
            s.close();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return flashcard;
    }

    @Override
    public boolean deleteFlashcard(long id) {
        boolean deleted;

        try {
            // Delete from Flashcard table
            PreparedStatement s = c.prepareStatement("DELETE FROM " + TBL_FLASHCARD + " WHERE " + COL_ID + "=?");
            s.setLong(1, id);
            deleted = s.executeUpdate() > 0;

            s.close();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

        return deleted;
    }

    @Override
    public boolean updateFlashcard(Flashcard flashcard) {
        boolean updated;

        try {
            // Update Flashcard in table
            PreparedStatement s = c.prepareStatement("UPDATE " + TBL_FLASHCARD + " SET "
                                                        + COL_NAME + "=?, "
                                                        + COL_QUESTION + "=?, "
                                                        + COL_ANSWERTYPE + "=?, "
                                                        + COL_IMAGEFILE + "=? "
                                                        + " WHERE " + COL_ID + "=?");
            s.setString(1, flashcard.getName());
            s.setString(2, flashcard.getQuestion());
            s.setInt(3, FlashcardAnswerType.fromFlashcardAnswer(flashcard.getAnswer()).getIndex());
            s.setString(4, flashcard.getImageLocation());
            s.setLong(5, flashcard.getId());

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
     * Creates a Flashcard from the data in a ResultSet.
     * @param rs The ResultSet to pull Flashcard data from.
     * @return A Flashcard object with the ResultSet data.
     * @throws SQLException Thrown when the ResultSet contains invalid data.
     */
    private Flashcard resultSetToFlashcard(final ResultSet rs) throws SQLException {
        final long id = rs.getLong(COL_ID);
        final String name = rs.getString(COL_NAME);
        final String question = rs.getString(COL_QUESTION);
        final FlashcardAnswerType answerType = FlashcardAnswerType.fromInt(rs.getInt(COL_ANSWERTYPE));
        final String imageFile = rs.getString(COL_IMAGEFILE);

        // Delegate creation & attaching of FlashcardAnswer to logic layer. Null-object placeholder for now.
        final FlashcardAnswer answer = answerType == FlashcardAnswerType.TextAnswer ? new FlashcardTextAnswer() :
                                        answerType == FlashcardAnswerType.TrueFalseAnswer ? new FlashcardTFAnswer() :
                                        answerType == FlashcardAnswerType.MultipleChoiceAnswer ? new FlashcardMCAnswer() :
                                        null;

        return new Flashcard(name, question, answer, id, imageFile);
    }

    //endregion

}

package com.queens.flashcards.Persistence.stubs;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Persistence.Interfaces.FlashcardPersistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub implementation of a DB handler for Flashcard persistence.
 */
public class FlashcardPersistenceStub implements FlashcardPersistence {

    //region Members

    private ArrayList<Flashcard> flashcards;
    private Long nextFlashcardID;
    private Long nextFlashcardAnswerID;

    //endregion

    //region Constructor

    /**
     * Initializes a new instance of a FlashcardPersistenceStub with temporary default data.
     */
    public FlashcardPersistenceStub() {
        flashcards = new ArrayList<>();
        nextFlashcardID = 1l;
        nextFlashcardAnswerID = 1l;

        Flashcard flashcard;
        FlashcardAnswer flashcardAnswer;
        List<String> wrongAnswers = new ArrayList<String>();
        wrongAnswers.add("not that");
        wrongAnswers.add("not that");
        wrongAnswers.add("not that");

        flashcardAnswer = new FlashcardMCAnswer("no", wrongAnswers, nextFlashcardAnswerID);
        flashcard = new Flashcard("name", "Is it a bad question?", flashcardAnswer, nextFlashcardID);
        nextFlashcardAnswerID++;
        nextFlashcardID++;
        flashcards.add(flashcard);

        flashcardAnswer = new FlashcardTextAnswer("yes",nextFlashcardAnswerID);
        flashcard = new Flashcard("Flashcard2", "Is it a bad question?", flashcardAnswer, nextFlashcardID);
        nextFlashcardAnswerID++;
        nextFlashcardID++;
        flashcards.add(flashcard);

        flashcardAnswer = new FlashcardTextAnswer("yes",nextFlashcardAnswerID);
        flashcard = new Flashcard("Flashcard3", "potato or tomato", flashcardAnswer, nextFlashcardID);
        nextFlashcardAnswerID++;
        nextFlashcardID++;
        flashcards.add(flashcard);

        flashcardAnswer = new FlashcardTextAnswer("maybe",nextFlashcardAnswerID);
        flashcard = new Flashcard("Flashcard4", "Is it a bad question?", flashcardAnswer, nextFlashcardID);
        nextFlashcardAnswerID++;
        nextFlashcardID++;
        flashcards.add(flashcard);

        flashcardAnswer = new FlashcardTextAnswer("idek",nextFlashcardAnswerID);
        flashcard = new Flashcard("Flashcard5", "???????????????", flashcardAnswer, nextFlashcardID);
        nextFlashcardAnswerID++;
        nextFlashcardID++;
        flashcards.add(flashcard);

        flashcardAnswer = new FlashcardTextAnswer("no",nextFlashcardAnswerID);
        flashcard = new Flashcard("Flashcard6", "Is it a bad question?", flashcardAnswer, nextFlashcardID);
        nextFlashcardAnswerID++;
        nextFlashcardID++;
        flashcards.add(flashcard);

        flashcardAnswer = new FlashcardTextAnswer("7 < 8",nextFlashcardAnswerID);
        flashcard = new Flashcard("Flashcard7", "7 > 8", flashcardAnswer, nextFlashcardID);
        nextFlashcardAnswerID++;
        nextFlashcardID++;
        flashcards.add(flashcard);

        flashcardAnswer = new FlashcardTextAnswer("?",nextFlashcardAnswerID);
        flashcard = new Flashcard("Flashcard8", "P = NP?", flashcardAnswer, nextFlashcardID);
        nextFlashcardAnswerID++;
        nextFlashcardID++;
        flashcards.add(flashcard);
    }

    //endregion

    //region FlashcardPersistence Methods

    /**
     * Returns the list of all Flashcards.
     * @return The list of all Flashcards.
     */
    @Override
    public List<Flashcard> getAllFlashcards() {
        return new ArrayList<>(flashcards);
    }

    /**
     * Retrieves a Flashcard by its ID.
     * @param id The ID of the Flashcard to get.
     * @return The Flashcard with the specified ID or null if it does not exist.
     */
    @Override
    public Flashcard getFlashcardById(long id) {
        for (Flashcard f : flashcards)
            if (f.getId() == id)
                return f;

        return null;
    }

    /**
     * Searches the list of Flashcards for a Flashcard with the specified name.
     * @param name The name of the desired Flashcard.
     * @return The Flashcard with the specified name, or null if not found.
     */
    @Override
    public Flashcard getFlashcardByName(String name) {
        for (Flashcard f : flashcards)
            if (f.getName().equals(name))
                return f;

        return null;
    }

    /**
     * Adds a new Flashcard and modifies its ID to a valid ID.
     * @param flashcard The Flashcard to add.
     * @return The Flashcard added with a new, valid ID.
     */
    @Override
    public Flashcard createFlashcard(Flashcard flashcard) {
        if(flashcard != null) {
            flashcard.setId(nextFlashcardID++);
            flashcards.add(flashcard);
        }

        return flashcard;
    }

    /**
     * Deletes a Flashcard.
     * @param id The ID of the Flashcard to delete.
     * @return True if the deletion was a success, false otherwise.
     */
    @Override
    public boolean deleteFlashcard(long id) {
        for (Flashcard f : flashcards) {
            if (f.getId() == id) {
                flashcards.remove(f);
                return true;
            }
        }

        return false;
    }

    /**
     * Updates an existing Flashcard.
     * @param flashcard The Flashcard to update.
     * @return True if the update was a success, false otherwise.
     */
    @Override
    public boolean updateFlashcard(Flashcard flashcard) {
        for (Flashcard f : flashcards) {
            if (f.getId() == flashcard.getId()) {
                f.setName(flashcard.getName());
                f.setQuestion(flashcard.getQuestion());
                f.setAnswer(flashcard.getAnswer());
                f.setImageLocation(flashcard.getImageLocation());
                return true;
            }
        }

        return false;
    }

    //endregion

}

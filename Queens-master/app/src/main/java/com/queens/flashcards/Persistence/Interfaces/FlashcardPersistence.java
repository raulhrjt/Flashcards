package com.queens.flashcards.Persistence.Interfaces;

import com.queens.flashcards.Model.Flashcard.Flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for persisting Flashcard data to external storage.
 */
public interface FlashcardPersistence {

    /**
     * Gets all of the Flashcards in storage.
     * @return A list of all Flashcards.
     */
    List<Flashcard> getAllFlashcards();

    /**
     * Searches for a Flashcard with the specified ID.
     * @param id The ID of the desired Flashcard.
     * @return The Flashcard with the specified ID, or null if not found.
     */
    Flashcard getFlashcardById(long id);

    /**
     * Searches for a Flashcard with the specified Name.
     * @param name The name of the desired Flashcard.
     * @return The Flashcard with the specified name, or null if not found.
     */
    Flashcard getFlashcardByName(String name);

    /**
     * Saves the specified Flashcard.
     * <p>The Flashcard's ID is modified to a valid value.</p>
     * @param flashcard The new Flashcard to persist.
     * @return The persisted Flashcard with a valid ID.
     */
    Flashcard createFlashcard(Flashcard flashcard);

    /**
     * Deletes the Flashcard with the specified ID.
     * @param id The ID of the Flashcard to delete.
     * @return True if the Flashcard was deleted, false otherwise.
     */
    boolean deleteFlashcard(long id);

    /**
     * Updates the data for an existing Flashcard.
     * @param flashcard The Flashcard to update.
     * @return True if the Flashcard was updated, false otherwise.
     */
    boolean updateFlashcard(Flashcard flashcard);

}

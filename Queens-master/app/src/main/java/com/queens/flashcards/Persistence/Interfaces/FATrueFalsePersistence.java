package com.queens.flashcards.Persistence.Interfaces;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;

public interface FATrueFalsePersistence {

    /**
     * Gets the FlashcardTFAnswer of the specified Flashcard.
     * @param flashcard The Flashcard to get an answer for.
     * @return The FlashcardTFAnswer of the Flashcard.
     */
    FlashcardTFAnswer getTFAnswerFor(Flashcard flashcard);

    /**
     * Saves a FlashcardTFAnswer for the Flashcard.
     * @param answer The FlashcardTFAnswer to save.
     * @param flashcard The Flashcard to save the answer for.
     * @return The saved FlashcardTFAnswer.
     */
    FlashcardTFAnswer createTFAnswerFor(FlashcardTFAnswer answer, Flashcard flashcard);

    /**
     * Deletes the FlashcardTFAnswer for the specified Flashcard.
     * @param flashcard The Flashcard to have its FlashcardTFAnswer deleted.
     * @return True if the deletion was successful, false otherwise.
     */
    boolean deleteTFAnswerFor(Flashcard flashcard);

    /**
     * Updates the FlashcardTFAnswer for the specified Flashcard.
     * @param answer The FlashcardTFAnswer to update.
     * @param flashcard The Flashcard to have its FlashcardTFAnswer updated.
     * @return True if the update was successful, false otherwise.
     */
    boolean updateTFAnswerFor(FlashcardTFAnswer answer, Flashcard flashcard);

}

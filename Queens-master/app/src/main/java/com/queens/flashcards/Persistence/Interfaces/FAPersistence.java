package com.queens.flashcards.Persistence.Interfaces;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;

public interface FAPersistence {

    /**
     * Gets the FlashcardAnswer of the specified Flashcard.
     * @param flashcard The Flashcard to get an answer for.
     * @return The FlashcardAnswer of the Flashcard.
     */
    FlashcardAnswer getAnswerFor(Flashcard flashcard);

    /**
     * Saves a FlashcardAnswer for the Flashcard.
     * @param answer The FlashcardAnswer to save.
     * @param flashcard The Flashcard to save the answer for.
     * @return The saved FlashcardAnswer.
     */
    FlashcardAnswer createAnswerFor(FlashcardAnswer answer, Flashcard flashcard);

    /**
     * Deletes the FlashcardAnswer for the specified Flashcard.
     * @param flashcard The Flashcard to have its FlashcardAnswer deleted.
     * @return True if the deletion was successful, false otherwise.
     */
    boolean deleteAnswerFor(Flashcard flashcard);

    /**
     * Updates the FlashcardAnswer for the specified Flashcard.
     * @param answer The FlashcardAnswer to update.
     * @param flashcard The Flashcard to have its FlashcardAnswer updated.
     * @return True if the update was successful, false otherwise.
     */
    boolean updateAnswerFor(FlashcardAnswer answer, Flashcard flashcard);

}

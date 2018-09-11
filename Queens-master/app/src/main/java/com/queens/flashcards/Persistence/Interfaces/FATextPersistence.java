package com.queens.flashcards.Persistence.Interfaces;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;

public interface FATextPersistence {

    /**
     * Gets the FlashcardTextAnswer of the specified Flashcard.
     * @param flashcard The Flashcard to get an answer for.
     * @return The FlashcardTextAnswer of the Flashcard.
     */
    FlashcardTextAnswer getTextAnswerFor(Flashcard flashcard);

    /**
     * Saves a FlashcardTextAnswer for the Flashcard.
     * @param answer The FlashcardTextAnswer to save.
     * @param flashcard The Flashcard to save the answer for.
     * @return The saved FlashcardTextAnswer.
     */
    FlashcardTextAnswer createTextAnswerFor(FlashcardTextAnswer answer, Flashcard flashcard);

    /**
     * Deletes the FlashcardTextAnswer for the specified Flashcard.
     * @param flashcard The Flashcard to have its FlashcardTextAnswer deleted.
     * @return True if the deletion was successful, false otherwise.
     */
    boolean deleteTextAnswerFor(Flashcard flashcard);

    /**
     * Updates the FlashcardTextAnswer for the specified Flashcard.
     * @param answer The FlashcardTextAnswer to update.
     * @param flashcard The Flashcard to have its FlashcardTextAnswer updated.
     * @return True if the update was successful, false otherwise.
     */
    boolean updateTextAnswerFor(FlashcardTextAnswer answer, Flashcard flashcard);

}

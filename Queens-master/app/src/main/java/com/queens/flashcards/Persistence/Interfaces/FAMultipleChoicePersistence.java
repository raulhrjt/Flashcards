package com.queens.flashcards.Persistence.Interfaces;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;

public interface FAMultipleChoicePersistence {

    /**
     * Gets the FlashcardMCAnswer of the specified Flashcard.
     * @param flashcard The Flashcard to get an answer for.
     * @return The FlashcardMCAnswer of the Flashcard.
     */
    FlashcardMCAnswer getMCAnswerFor(Flashcard flashcard);

    /**
     * Saves a FlashcardMCAnswer for the Flashcard.
     * @param answer The FlashcardMCAnswer to save.
     * @param flashcard The Flashcard to save the answer for.
     * @return The saved FlashcardMCAnswer.
     */
    FlashcardMCAnswer createMCAnswerFor(FlashcardMCAnswer answer, Flashcard flashcard);

    /**
     * Deletes the FlashcardMCAnswer for the specified Flashcard.
     * @param flashcard The Flashcard to have its FlashcardMCAnswer deleted.
     * @return True if the deletion was successful, false otherwise.
     */
    boolean deleteMCAnswerFor(Flashcard flashcard);

    /**
     * Updates the FlashcardMCAnswer for the specified Flashcard.
     * @param answer The FlashcardMCAnswer to update.
     * @param flashcard The Flashcard to have its FlashcardMCAnswer updated.
     * @return True if the update was successful, false otherwise.
     */
    boolean updateMCAnswerFor(FlashcardMCAnswer answer, Flashcard flashcard);

}

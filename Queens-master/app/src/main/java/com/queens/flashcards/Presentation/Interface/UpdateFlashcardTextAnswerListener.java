package com.queens.flashcards.Presentation.Interface;

import com.queens.flashcards.Model.Flashcard.Flashcard;

public interface UpdateFlashcardTextAnswerListener extends UpdateFlashcardDataListener {

    /**
     * Updates the text answer of the Flashcard's answer.
     * @param answer The text answer.
     * @return The updated Flashcard.
     */
    Flashcard updateFlashcardAnswerText(String answer);

}

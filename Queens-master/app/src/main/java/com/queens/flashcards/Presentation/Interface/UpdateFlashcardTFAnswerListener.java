package com.queens.flashcards.Presentation.Interface;

import com.queens.flashcards.Model.Flashcard.Flashcard;

public interface UpdateFlashcardTFAnswerListener extends UpdateFlashcardDataListener {

    /**
     * Updates the boolean answer of the Flashcard's answer
     * @param answer - if the answer is True/False
     * @return The updated Flashcard
     */
    Flashcard updateFlashcardTrueFalseAnswer(boolean answer);


}

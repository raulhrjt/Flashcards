package com.queens.flashcards.Persistence.Factory;

import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Persistence.Interfaces.FAPersistence;

public interface FAPersistenceFactory {

    /**
     * Creates an FAPersistence for FlashcardAnswers.
     * @return An FAPersistence.
     */
    FAPersistence createFAPersistence();
}

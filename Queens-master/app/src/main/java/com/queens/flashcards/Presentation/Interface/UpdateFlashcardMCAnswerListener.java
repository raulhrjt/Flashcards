package com.queens.flashcards.Presentation.Interface;

import com.queens.flashcards.Model.Flashcard.Flashcard;

import java.util.List;

public interface UpdateFlashcardMCAnswerListener extends UpdateFlashcardDataListener {

    /**
     * Updates the valid answer of the Multiple Choice Flashcard.
     * @param answer The valid answer.
     * @return The updated Flashcard.
     */
    Flashcard updateRightMCAnswer(String answer);

    /**
     * Updates the invalid answers of the Multiple Choice Flashcard.
     * @param wrongAnswers - List of Invalid Answers.
     * @return The updated Flashcard.
     */
    Flashcard updateWrongMCAnswers(List<String> wrongAnswers);


}

package com.queens.flashcards.Presentation.Interface;

import com.queens.flashcards.Model.Flashcard.Flashcard;

public interface UpdateGuessingGame {
    void userSelectedAnswer(String answer);
    void userSelectedAnswerCorrectly(boolean correct);
    void submitAnswer(String answer, Flashcard flashcard);
    String getUserSelectedAnswer();
}

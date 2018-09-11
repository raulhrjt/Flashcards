package com.queens.flashcards.Presentation.GuessingGame.Factory;

import android.support.v4.app.Fragment;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.GuessingGame.BluntAnswerFragment;
import com.queens.flashcards.Presentation.GuessingGame.NiceAnswerFragment;

public final class AnswerFragmentFactory {

    public static Fragment createAnswerFragmentFor(Flashcard flashcard,
                                                   boolean reverseQuestionAnswer,
                                                   boolean correct,
                                                   String guessedAnswer)
    {
        FlashcardAnswer answer = flashcard.getAnswer();
        if (answer instanceof FlashcardTextAnswer)
            return NiceAnswerFragment.newInstance(flashcard, reverseQuestionAnswer);
        else if (answer instanceof FlashcardMCAnswer || answer instanceof FlashcardTFAnswer)
            return BluntAnswerFragment.newInstance(flashcard, correct, guessedAnswer);
        else
            throw new IllegalStateException();
    }

}

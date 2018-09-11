package com.queens.flashcards.Presentation.GuessingGame.Factory;

import android.support.v4.app.Fragment;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.GuessingGame.MultipleChoiceQuestionFragment;
import com.queens.flashcards.Presentation.GuessingGame.TextQuestionFragment;
import com.queens.flashcards.Presentation.GuessingGame.TrueFalseQuestionFragment;

public final class QuestionFragmentFactory {

    /**
     * Creates a question fragment for the specified Flashcard.
     * @param flashcard The Flashcard to create a question fragment for.
     * @param reverseTextAnswer Flag indicating whether to reverse the question/answer ordering in text answers. (This is ignored for answers types other than FlashcardTextAnswer)
     * @return The question fragment for the specified Flashcard.
     */
    public static Fragment createQuestionFragmentFor(Flashcard flashcard, boolean reverseTextAnswer) {
        FlashcardAnswer answer = flashcard.getAnswer();
        if (answer instanceof FlashcardTextAnswer)
            return TextQuestionFragment.newInstance(flashcard, reverseTextAnswer);
        else if (answer instanceof FlashcardTFAnswer)
            return TrueFalseQuestionFragment.newInstance(flashcard);
        else if (answer instanceof FlashcardMCAnswer)
            return MultipleChoiceQuestionFragment.newInstance(flashcard);
        else
            throw new IllegalStateException();
    }

}

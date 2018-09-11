package com.queens.flashcards.Presentation.Parcelable.Factory;

import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;

public final class FlashcardAnswerParcelableFactoryMaker {

    public static FlashcardAnswerParcelableFactory makeFactory(FlashcardAnswer a) {
        if (a instanceof FlashcardTextAnswer)
            return new FlashcardTextAnswerParcelableFactory((FlashcardTextAnswer)a);
        else if (a instanceof FlashcardTFAnswer)
            return new FlashcardTFAnswerParcelableFactory((FlashcardTFAnswer)a);
        else if (a instanceof FlashcardMCAnswer)
            return new FlashcardMCAnswerParcelableFactory((FlashcardMCAnswer)a);
        else
            throw new IllegalStateException();
    }

}

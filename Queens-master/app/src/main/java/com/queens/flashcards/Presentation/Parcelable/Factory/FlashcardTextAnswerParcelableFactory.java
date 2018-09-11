package com.queens.flashcards.Presentation.Parcelable.Factory;

import android.os.Parcelable;

import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.Parcelable.FlashcardTextAnswerParcelable;

public class FlashcardTextAnswerParcelableFactory implements FlashcardAnswerParcelableFactory {

    private FlashcardTextAnswer answer;

    public FlashcardTextAnswerParcelableFactory(FlashcardTextAnswer answer) {
        this.answer = answer;
    }

    @Override
    public Parcelable getFlashcardAnswerParcelable() {
        return new FlashcardTextAnswerParcelable(answer);
    }
}

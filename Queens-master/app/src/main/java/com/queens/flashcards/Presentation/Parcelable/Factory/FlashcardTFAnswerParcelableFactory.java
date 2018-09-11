package com.queens.flashcards.Presentation.Parcelable.Factory;

import android.os.Parcelable;

import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Presentation.Parcelable.FlashcardTFAnswerParcelable;

public class FlashcardTFAnswerParcelableFactory implements FlashcardAnswerParcelableFactory {

    private FlashcardTFAnswer answer;

    public FlashcardTFAnswerParcelableFactory(FlashcardTFAnswer answer) {
        this.answer = answer;
    }

    @Override
    public Parcelable getFlashcardAnswerParcelable() {
        return new FlashcardTFAnswerParcelable(answer);
    }
}

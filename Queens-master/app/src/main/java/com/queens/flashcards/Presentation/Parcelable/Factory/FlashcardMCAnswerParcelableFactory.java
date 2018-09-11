package com.queens.flashcards.Presentation.Parcelable.Factory;

import android.os.Parcelable;

import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Presentation.Parcelable.FlashcardMCAnswerParcelable;

public class FlashcardMCAnswerParcelableFactory implements FlashcardAnswerParcelableFactory {

    private FlashcardMCAnswer answer;

    public FlashcardMCAnswerParcelableFactory(FlashcardMCAnswer answer) {
        this.answer = answer;
    }

    @Override
    public Parcelable getFlashcardAnswerParcelable() {
        return new FlashcardMCAnswerParcelable(answer);
    }
}

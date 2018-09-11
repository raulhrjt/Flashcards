package com.queens.flashcards.Presentation.Flashcard.Factory;

import android.support.v4.app.Fragment;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.Flashcard.EditCardOptionsMCFragment;
import com.queens.flashcards.Presentation.Flashcard.EditCardOptionsTFFragment;
import com.queens.flashcards.Presentation.Flashcard.EditCardOptionsTextFragment;

public class EditCardOptionsFragmentFactory {
    public static Fragment createEditCardOptionsFragmentFor(Flashcard flashcard) {
        FlashcardAnswer answer = flashcard.getAnswer();

        if(answer instanceof FlashcardTextAnswer) {
            return EditCardOptionsTextFragment.newInstance(flashcard);
        } else if(answer instanceof FlashcardTFAnswer) {
            return EditCardOptionsTFFragment.newInstance(flashcard);
        } else if(answer instanceof FlashcardMCAnswer) {
            return EditCardOptionsMCFragment.newInstance(flashcard);
        } else {
            throw new IllegalStateException();
        }
    }
}

package com.queens.flashcards.Presentation.Flashcard.Factory;


import android.support.v4.app.Fragment;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.Flashcard.EditCardMCAnswerFragment;
import com.queens.flashcards.Presentation.Flashcard.EditCardTFAnswerFragment;
import com.queens.flashcards.Presentation.Flashcard.EditCardTextAnswerFragment;

public final class EditCardAnswerFragmentFactory {

    public static Fragment createEditCardAnswerFragmentFor(Flashcard card) {
        FlashcardAnswer answer = card.getAnswer();

        if(answer instanceof FlashcardTextAnswer) {
            return EditCardTextAnswerFragment.newInstance(card);
        } else if(answer instanceof FlashcardTFAnswer) {
            return EditCardTFAnswerFragment.newInstance(card);
        } else if(answer instanceof FlashcardMCAnswer) {
            return EditCardMCAnswerFragment.newInstance(card);
        } else {
            throw new IllegalStateException();
        }
    }

}

package com.queens.flashcards.Persistence.Wrappers;

import android.support.annotation.NonNull;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Persistence.Interfaces.FAPersistence;
import com.queens.flashcards.Persistence.Interfaces.FATextPersistence;

public class FATextPersistenceWrapper implements FAPersistence {

    //region Members

    private FATextPersistence faTextPersistence;

    //endregion

    //region Constructor

    public FATextPersistenceWrapper(@NonNull FATextPersistence faTextPersistence) {
        this.faTextPersistence = faTextPersistence;
    }

    //endregion

    //region FAPersistence Methods

    @Override
    public FlashcardAnswer getAnswerFor(Flashcard flashcard) {
        return this.faTextPersistence.getTextAnswerFor(flashcard);
    }

    @Override
    public FlashcardAnswer createAnswerFor(FlashcardAnswer answer, Flashcard flashcard) {
        if (!(answer instanceof FlashcardTextAnswer))
            throw new IllegalStateException();

        return this.faTextPersistence.createTextAnswerFor((FlashcardTextAnswer)answer, flashcard);
    }

    @Override
    public boolean deleteAnswerFor(Flashcard flashcard) {
        return this.faTextPersistence.deleteTextAnswerFor(flashcard);
    }

    @Override
    public boolean updateAnswerFor(FlashcardAnswer answer, Flashcard flashcard) {
        if (!(answer instanceof FlashcardTextAnswer))
            throw new IllegalStateException();

        return this.faTextPersistence.updateTextAnswerFor((FlashcardTextAnswer)answer, flashcard);
    }

    //endregion

}

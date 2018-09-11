package com.queens.flashcards.Persistence.Wrappers;

import android.support.annotation.NonNull;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Persistence.Interfaces.FAMultipleChoicePersistence;
import com.queens.flashcards.Persistence.Interfaces.FAPersistence;

public class FAMultipleChoicePersistenceWrapper implements FAPersistence {

    //region Members

    private FAMultipleChoicePersistence faMultipleChoicePersistence;

    //endregion

    //region Constructor

    public FAMultipleChoicePersistenceWrapper(@NonNull FAMultipleChoicePersistence faMultipleChoicePersistence) {
        this.faMultipleChoicePersistence = faMultipleChoicePersistence;
    }

    //endregion

    //region FAPersistence Methods

    @Override
    public FlashcardAnswer getAnswerFor(Flashcard flashcard) {
        return this.faMultipleChoicePersistence.getMCAnswerFor(flashcard);
    }

    @Override
    public FlashcardAnswer createAnswerFor(FlashcardAnswer answer, Flashcard flashcard) {
        if (!(answer instanceof FlashcardMCAnswer))
            throw new IllegalStateException();

        return this.faMultipleChoicePersistence.createMCAnswerFor((FlashcardMCAnswer) answer, flashcard);
    }

    @Override
    public boolean deleteAnswerFor(Flashcard flashcard) {
        return this.faMultipleChoicePersistence.deleteMCAnswerFor(flashcard);
    }

    @Override
    public boolean updateAnswerFor(FlashcardAnswer answer, Flashcard flashcard) {
        if (!(answer instanceof FlashcardMCAnswer))
            throw new IllegalStateException();

        return this.faMultipleChoicePersistence.updateMCAnswerFor((FlashcardMCAnswer) answer, flashcard);
    }

    //endregion

}

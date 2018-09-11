package com.queens.flashcards.Persistence.Wrappers;

import android.support.annotation.NonNull;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Persistence.Interfaces.FAPersistence;
import com.queens.flashcards.Persistence.Interfaces.FATrueFalsePersistence;

public class FATrueFalsePersistenceWrapper implements FAPersistence {

    //region Members

    private FATrueFalsePersistence faTrueFalsePersistence;

    //endregion

    //region Constructor

    public FATrueFalsePersistenceWrapper(@NonNull FATrueFalsePersistence faTrueFalsePersistence) {
        this.faTrueFalsePersistence = faTrueFalsePersistence;
    }

    //endregion

    //region FAPersistence Methods

    @Override
    public FlashcardAnswer getAnswerFor(Flashcard flashcard) {
        return this.faTrueFalsePersistence.getTFAnswerFor(flashcard);
    }

    @Override
    public FlashcardAnswer createAnswerFor(FlashcardAnswer answer, Flashcard flashcard) {
        if (!(answer instanceof FlashcardTFAnswer))
            throw new IllegalStateException();

        return this.faTrueFalsePersistence.createTFAnswerFor((FlashcardTFAnswer) answer, flashcard);
    }

    @Override
    public boolean deleteAnswerFor(Flashcard flashcard) {
        return this.faTrueFalsePersistence.deleteTFAnswerFor(flashcard);
    }

    @Override
    public boolean updateAnswerFor(FlashcardAnswer answer, Flashcard flashcard) {
        if (!(answer instanceof FlashcardTFAnswer))
            throw new IllegalStateException();

        return this.faTrueFalsePersistence.updateTFAnswerFor((FlashcardTFAnswer)answer, flashcard);
    }

    //endregion

}

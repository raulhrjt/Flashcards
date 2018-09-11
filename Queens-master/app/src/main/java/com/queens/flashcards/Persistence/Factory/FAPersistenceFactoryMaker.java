package com.queens.flashcards.Persistence.Factory;

import android.support.annotation.NonNull;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Persistence.Interfaces.FAMultipleChoicePersistence;
import com.queens.flashcards.Persistence.Interfaces.FATextPersistence;
import com.queens.flashcards.Persistence.Interfaces.FATrueFalsePersistence;

public final class FAPersistenceFactoryMaker {

    //region Members

    private FATextPersistence faTextPersistence;
    private FATrueFalsePersistence faTrueFalsePersistence;
    private FAMultipleChoicePersistence faMultipleChoicePersistence;

    //endregion

    //region Constructor

    public FAPersistenceFactoryMaker(@NonNull FATextPersistence faTextPersistence,
                                     @NonNull FATrueFalsePersistence faTrueFalsePersistence,
                                     @NonNull FAMultipleChoicePersistence faMultipleChoicePersistence)
    {
        this.faTextPersistence = faTextPersistence;
        this.faTrueFalsePersistence = faTrueFalsePersistence;
        this.faMultipleChoicePersistence = faMultipleChoicePersistence;
    }

    //endregion

    //region Public Methods

    /**
     * Creates the appropriate FAPersistenceFactory for the specified answer.
     * @param answer The answer to create a factory for.
     * @return An appropriate FAPersistenceFactory.
     */
    public FAPersistenceFactory makeFactory(FlashcardAnswer answer) {

        if (answer instanceof FlashcardTextAnswer)
            return new FATextPersistenceFactory(this.faTextPersistence);
        else if (answer instanceof FlashcardTFAnswer)
            return new FATrueFalsePersistenceFactory(this.faTrueFalsePersistence);
        else if (answer instanceof FlashcardMCAnswer)
            return new FAMultipleChoicePersistenceFactory(this.faMultipleChoicePersistence);
        else
            throw new IllegalStateException();

    }

    //endregion

}

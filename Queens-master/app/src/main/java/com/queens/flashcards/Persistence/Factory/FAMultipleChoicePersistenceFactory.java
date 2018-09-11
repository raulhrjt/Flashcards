package com.queens.flashcards.Persistence.Factory;

import android.support.annotation.NonNull;

import com.queens.flashcards.Persistence.Interfaces.FAMultipleChoicePersistence;
import com.queens.flashcards.Persistence.Interfaces.FAPersistence;
import com.queens.flashcards.Persistence.Wrappers.FAMultipleChoicePersistenceWrapper;

public class FAMultipleChoicePersistenceFactory implements FAPersistenceFactory {

    private FAMultipleChoicePersistence faMultipleChoicePersistence;

    public FAMultipleChoicePersistenceFactory(@NonNull FAMultipleChoicePersistence faMultipleChoicePersistence) {
        this.faMultipleChoicePersistence = faMultipleChoicePersistence;
    }

    @Override
    public FAPersistence createFAPersistence() {
        return new FAMultipleChoicePersistenceWrapper(this.faMultipleChoicePersistence);
    }
}

package com.queens.flashcards.Persistence.Factory;

import android.support.annotation.NonNull;

import com.queens.flashcards.Persistence.Interfaces.FAPersistence;
import com.queens.flashcards.Persistence.Interfaces.FATextPersistence;
import com.queens.flashcards.Persistence.Wrappers.FATextPersistenceWrapper;

public class FATextPersistenceFactory implements FAPersistenceFactory {

    private FATextPersistence faTextPersistence;

    public FATextPersistenceFactory(@NonNull FATextPersistence faTextPersistence) {
        this.faTextPersistence = faTextPersistence;
    }

    @Override
    public FAPersistence createFAPersistence() {
        return new FATextPersistenceWrapper(faTextPersistence);
    }
}

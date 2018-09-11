package com.queens.flashcards.Persistence.Factory;

import android.support.annotation.NonNull;

import com.queens.flashcards.Persistence.Interfaces.FAPersistence;
import com.queens.flashcards.Persistence.Interfaces.FATrueFalsePersistence;
import com.queens.flashcards.Persistence.Wrappers.FATrueFalsePersistenceWrapper;

public class FATrueFalsePersistenceFactory implements FAPersistenceFactory {

    private FATrueFalsePersistence faTrueFalsePersistence;

    public FATrueFalsePersistenceFactory(@NonNull FATrueFalsePersistence faTrueFalsePersistence) {
        this.faTrueFalsePersistence = faTrueFalsePersistence;
    }

    @Override
    public FAPersistence createFAPersistence() {
        return new FATrueFalsePersistenceWrapper(faTrueFalsePersistence);
    }
}

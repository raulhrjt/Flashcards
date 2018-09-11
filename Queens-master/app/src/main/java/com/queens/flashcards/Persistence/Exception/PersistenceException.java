package com.queens.flashcards.Persistence.Exception;

/**
 * A wrapper exception that is thrown when exceptions occurs in the Persistence layer.
 */
public class PersistenceException extends RuntimeException {

    /**
     * Initializes a new instance of a PersistenceException.
     * @param cause The cause of the exception.
     */
    public PersistenceException(final Exception cause) {
        super(cause);
    }

}

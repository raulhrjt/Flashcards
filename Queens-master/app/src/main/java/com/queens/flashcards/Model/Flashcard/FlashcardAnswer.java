package com.queens.flashcards.Model.Flashcard;

import java.util.Objects;

public abstract class FlashcardAnswer{

    //region Members

    private long id;

    //endregion

    //region Constructors

    public FlashcardAnswer(FlashcardAnswer other) {
        this.id = other.id;
    }

    public FlashcardAnswer(long id) {
        this.id = id;
    }

    public FlashcardAnswer() {
        this.id = 0;
    }

    //endregion

    //region Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //endregion

    //region Public Methods

    /**
     * Checks whether this FlashcardAnswer has an actual answer
     * @return True if this FlashcardAnswer is empty/null, false otherwise
     */
    public abstract boolean isEmpty();

    /** Gets the Answer, in String form*/
    public abstract String getAnswer();

    /**
     * Creates a deep copy of this FlashcardAnswer.
     * @return A deep copy of this FlashcardAnswer.
     */
    public abstract FlashcardAnswer deepCopy();

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FlashcardAnswer))
            return false;

        FlashcardAnswer other = (FlashcardAnswer) obj;

        return this.id == other.id;
    }

    //endregion
}

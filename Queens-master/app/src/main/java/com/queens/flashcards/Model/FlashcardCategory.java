package com.queens.flashcards.Model;

import java.util.Objects;

/**
 * Stores an association of a Flashcard ID to a Category ID.
 */
public class FlashcardCategory {

    //region Members

    private long flashcardId;
    private long categoryId;

    //endregion

    //region Constructors

    /**
     * Initializes a new instance of a FlashcardCategory with the specified IDs.
     * @param flashcardId The ID of the associated Flashcard.
     * @param categoryId The ID of the associated Category.
     */
    public FlashcardCategory(long flashcardId, long categoryId) {
        this.flashcardId = flashcardId;
        this.categoryId = categoryId;
    }

    //endregion

    //region Getters & Setters

    /**
     * Gets the associated Flashcard ID.
     * @return The associated Flashcard ID.
     */
    public long getFlashcardId() {
        return this.flashcardId;
    }

    /**
     * Gets the associated Category ID.
     * @return The associated Category ID.
     */
    public long getCategoryId() {
        return categoryId;
    }

    //endregion

    //region Public Methods

    @Override
    public int hashCode() {
        return Objects.hash(this.flashcardId, this.categoryId);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FlashcardCategory))
            return false;

        FlashcardCategory other = (FlashcardCategory) obj;

        return this.flashcardId == other.flashcardId &&
                this.categoryId == other.categoryId;
    }


    //endregion

}

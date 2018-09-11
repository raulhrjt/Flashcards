package com.queens.flashcards.Logic;

import android.support.annotation.NonNull;

import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.FlashcardCategory;
import com.queens.flashcards.Persistence.Interfaces.FCPersistence;
import com.queens.flashcards.Services;

import java.util.List;

public class FCManagementService {

    //region Members

    private FCPersistence fcPersistence;

    //endregion

    //region Constructors

    public FCManagementService(@NonNull FCPersistence fcPersistence) {
        this.fcPersistence = fcPersistence;
    }

    //endregion

    //region Public Methods

    /**
     * Gets a list of all Flashcard-Category ID associations.
     * @return A list of all Flashcard-Category ID associations.
     */
    public List<FlashcardCategory> getAllFlashcardCategories() {
        return this.fcPersistence.getAllFlashcardCategories();
    }

    /**
     * Gets a list of all Flashcard IDs in the specified Category.
     * @param categoryId The Category ID to search for associated Flashcards.
     * @return The list of Flashcard IDs in the specified Category.
     */
    public List<Long> getFlashcardsInCategory(long categoryId) {
        return this.fcPersistence.getFlashcardsInCategory(categoryId);
    }

    /**
     * Gets a list of all Category IDs containing the specified Flashcard.
     * @param flashcardId The Flashcard ID to search for associated Categories.
     * @return The list of all Category IDs associated with the Flashcard.
     */
    public List<Long> getCategoriesWithFlashcard(long flashcardId) {
        return this.fcPersistence.getCategoriesWithFlashcard(flashcardId);
    }

    /**
     * Adds an association between a Flashcard and a Category.
     * @param flashcardId The Flashcard ID to associate.
     * @param categoryId The Category ID to associate.
     * @return True if the addition was successful, false otherwise.
     */
    public boolean addFlashcardCategory(long flashcardId, long categoryId) {
        return this.fcPersistence.createFlashcardCategory(flashcardId, categoryId);
    }

    /**
     * Removes an association between a Flashcard and a Category.
     * @param flashcardId The Flashcard ID to dissociate.
     * @param categoryId The Category to ID dissociate.
     * @return True if the removal was successful, false otherwise.
     */
    public boolean removeFlashcardCategory(long flashcardId, long categoryId) {
        return this.fcPersistence.deleteFlashcardCategory(flashcardId, categoryId);
    }

    /**
     * Dissociates the Flashcard from all Categories.
     * @param flashcardId The Flashcard ID to fully dissociate.
     * @return The number of associations removed.
     */
    public int removeFlashcard(long flashcardId) {
        return this.fcPersistence.removeFlashcard(flashcardId);
    }

    /**
     * Dissociates the Category from all Flashcards.
     * @param categoryId The Category ID to fully dissociate.
     * @return The number of associations removed.
     */
    public int removeCategory(long categoryId) {
        return this.fcPersistence.removeCategory(categoryId);
    }

    //endregion

}

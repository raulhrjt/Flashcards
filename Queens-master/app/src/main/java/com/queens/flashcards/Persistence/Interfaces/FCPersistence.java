package com.queens.flashcards.Persistence.Interfaces;

import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.FlashcardCategory;

import java.util.List;

public interface FCPersistence {

    /**
     * Gets a list of all Flashcard-Category ID associations.
     * @return A list of all Flashcard-Category ID associations.
     */
    List<FlashcardCategory> getAllFlashcardCategories();

    /**
     * Gets a list of all Flashcard IDs in the specified Category.
     * @param categoryId The Category to search for associated Flashcards.
     * @return The list of Flashcard IDs in the specified Category.
     */
    List<Long> getFlashcardsInCategory(long categoryId);

    /**
     * Gets a list of all Category IDs containing the specified Flashcard.
     * @param flashcardId The Flashcard to search for associated Categories.
     * @return The list of all Category IDs associated with the Flashcard.
     */
    List<Long> getCategoriesWithFlashcard(long flashcardId);

    /**
     * Adds an association between a Flashcard and a Category.
     * @param flashcardId The Flashcard to associate.
     * @param categoryId The Category to associate.
     * @return True if the addition was successful, false otherwise.
     */
    boolean createFlashcardCategory(long flashcardId, long categoryId);

    /**
     * Removes an association between a Flashcard and a Category.
     * @param flashcardId The Flashcard to dissociate.
     * @param categoryId The Category to dissociate.
     * @return True if the removal was successful, false otherwise.
     */
    boolean deleteFlashcardCategory(long flashcardId, long categoryId);

    /**
     * Dissociates the Flashcard from all Categories.
     * @param flashcardId The Flashcard to fully dissociate.
     * @return The number of associations removed.
     */
    int removeFlashcard(long flashcardId);

    /**
     * Dissociates the Category from all Flashcards.
     * @param categoryId The Category to fully dissociate.
     * @return The number of associations removed.
     */
    int removeCategory(long categoryId);

}

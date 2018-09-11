package com.queens.flashcards.Persistence.Interfaces;

import com.queens.flashcards.Model.Category.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for persisting Category data to external storage.
 */
public interface CategoryPersistence {

    /**
     * Gets a List of all Categories.
     * @return A List of all Categories.
     */
    List<Category> getAllCategories();

    /**
     * Searches for a Category with the specified ID.
     * @param id The ID of the Category to search for.
     * @return The Category with the specified ID, or null if not found.
     */
    Category getCategoryById(long id);

    /**
     * Searches for a Category with the specified name.
     * @param name The name of the Category to search for.
     * @return The Category with the specified name, or null if not found.
     */
    Category getCategoryByName(String name);

    /**
     * Saves the specified Category.
     * <p>The Category's ID is modified to a valid value.</p>
     * @param category The Category to persist.
     * @return The updated Category.
     */
    Category createCategory(Category category);

    /**
     * Deletes a Category from persistence.
     * @param categoryToRemove The Category to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    boolean deleteCategory(Category categoryToRemove);

    /**
     * Updates an existing Category in persistence.
     * @param category The Category to update.
     * @return True if the update was successful, false otherwise.
     */
    boolean updateCategory(Category category);

}
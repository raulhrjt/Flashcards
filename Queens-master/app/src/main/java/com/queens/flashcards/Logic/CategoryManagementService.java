package com.queens.flashcards.Logic;

import android.support.annotation.NonNull;

import com.queens.flashcards.Logic.Exception.CategoryNotFoundException;
import com.queens.flashcards.Logic.Exception.DuplicateNameException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Logic.Validation.CategoryValidator;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Persistence.Interfaces.CategoryPersistence;
import com.queens.flashcards.Persistence.Interfaces.FCPersistence;

import java.util.List;

/**
 * Class for application logic dealing with Categories.
 */
public class CategoryManagementService {

    //region Members

    private CategoryPersistence categoryPersistence;
    private FCPersistence fcPersistence;
    private CategoryValidator categoryValidator;

    //endregion

    //region Constructor

    public CategoryManagementService(@NonNull CategoryPersistence categoryPersistence, @NonNull FCPersistence fcPersistence, @NonNull CategoryValidator categoryValidator) {
        this.categoryPersistence = categoryPersistence;
        this.fcPersistence = fcPersistence;
        this.categoryValidator = categoryValidator;
    }

    //endregion

    //region Public Methods

    /** Gets all Categories in the DB
     *
     * @return List of all Categories
     */
    public List<Category> getAllCategories() {
        List<Category> all = categoryPersistence.getAllCategories();

        for (Category c : all) {
            List<Long> flashcardIds = fcPersistence.getFlashcardsInCategory(c.getId());
            for (Long fid : flashcardIds)
                c.add(fid);
        }

        return all;
    }

    /**
     * Searches for a Category with the specified ID.
     * @param id The ID of the Category to search for.
     * @return The Category with the specified ID, or null if not found.
     */
    public Category getCategoryById(long id) {
        Category c = categoryPersistence.getCategoryById(id);

        if (c != null) {
            List<Long> flashcardIds = fcPersistence.getFlashcardsInCategory(c.getId());
            for (Long fid : flashcardIds)
                c.add(fid);
        }

        return c;
    }

    /**
     * Searches for a Category with the specified name.
     * @param name The name of the Category to search for.
     * @return The Category with the specified name, or null if not found.
     */
    public Category getCategoryByName(String name) {
        Category c = categoryPersistence.getCategoryByName(name);

        if (c != null) {
            List<Long> flashcardIds = fcPersistence.getFlashcardsInCategory(c.getId());
            for (Long fid : flashcardIds)
                c.add(fid);
        }

        return c;
    }

    /** Attempts to create a new Category, throwing an error if there is invalid data
     *
     * @param category - Category to create in the database
     * @return - Created Category
     * @throws EmptyNameException - Thrown when a Category has an empty name
     */
    public Category createNewCategory(Category category) throws EmptyNameException, DuplicateNameException {

        // Validate Category
        categoryValidator.validateCategory(category);

        // Check for duplicate names
        if (categoryPersistence.getCategoryByName(category.getName()) != null)
            throw new DuplicateNameException();

        category = categoryPersistence.createCategory(category);

        // Save associations with Flashcards
        for (Long fid : category.getFlashcardIds())
            fcPersistence.createFlashcardCategory(fid, category.getId());

        return category;
    }

    /** Updates the Category in the DB using the passed Category's ID
     *
     * @param category - Category to update
     * @return True/False if the update was successful
     * @throws EmptyNameException - Thrown when the Category has an empty name
     */
    public boolean updateCategory(Category category) throws EmptyNameException, CategoryNotFoundException, DuplicateNameException {
        boolean updated;

        // Validate Category
        categoryValidator.validateCategory(category);

        // Check if Category exists first
        Category original = categoryPersistence.getCategoryById(category.getId());
        if (original == null)
            throw new CategoryNotFoundException();

        // Check for a different Category with the same name
        Category duplicate = categoryPersistence.getCategoryByName(category.getName());
        if (duplicate != null && duplicate.getId() != category.getId())
            throw new DuplicateNameException();

        updated = categoryPersistence.updateCategory(category);

        // Update associations with Flashcards
        List<Long> existing = fcPersistence.getFlashcardsInCategory(category.getId());

        // Remove associations that were deleted
        for (Long fid : existing)
            if (!category.getFlashcardIds().contains(fid))
                fcPersistence.deleteFlashcardCategory(fid, category.getId());

        // Add new associations
        for (Long fid : category.getFlashcardIds())
            if (!existing.contains(fid))
                fcPersistence.createFlashcardCategory(fid, category.getId());

        return updated;
    }

    /** Attempts to delete the Category in the DB
     *
     * @param category - Category to delete
     * @return True/False if it succeeded in deleting the Category
     */
    public boolean deleteCategory(Category category) {
        boolean deleted;

        // Delete Category and all of its links to Flashcards
        deleted = categoryPersistence.deleteCategory(category);
        if (deleted)
            fcPersistence.removeCategory(category.getId());

        return deleted;
    }

    //endregion

}

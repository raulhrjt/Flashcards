package com.queens.flashcards.Persistence.stubs;

import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Persistence.Interfaces.CategoryPersistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub implementation of a DB handler for Category persistence.
 */
public class CategoryPersistenceStub implements CategoryPersistence {

    //region Members

    private ArrayList<Category> categories;
    private static long nextCategoryID;

    //endregion

    //region Constructor

    /**
     * Initializes a new instance of a CategoryPersistenceStub with temporary default data.
     */
    public CategoryPersistenceStub() {
        categories = new ArrayList<>();
        nextCategoryID = 1;

        categories.add(new Category("Category1", nextCategoryID));
        nextCategoryID++;
        categories.add(new Category("Category2", nextCategoryID));
        nextCategoryID++;
        categories.add(new Category("Category3", nextCategoryID));
        nextCategoryID++;
        categories.add(new Category("Category4", nextCategoryID));
        nextCategoryID++;
        categories.add(new Category("Category5", nextCategoryID));
        nextCategoryID++;
        categories.add(new Category("Category6", nextCategoryID));
        nextCategoryID++;
        categories.add(new Category("Category7", nextCategoryID));
        nextCategoryID++;
        categories.add(new Category("Category8", nextCategoryID));
        nextCategoryID++;
        //8 categories, last id is 8
    }

    //endregion

    //region CategoryPersistence Methods

    /** Gets all categories */
    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    /**
     * Searches for a category with the specified ID in the list.
     * @param id The ID of the Category to search for.
     * @return The category if it exists, otherwise null.
     */
    @Override
    public Category getCategoryById(long id) {
        for (Category c : categories)
            if (c.getId() == id)
                return c;

        return null;
    }

    /**
     * Searches the list of Categories for a Category with the specified name.
     * @param name The name of the Category to search for.
     * @return The Category with the specified name, or null if not found.
     */
    @Override
    public Category getCategoryByName(String name) {
        for (Category c : categories)
            if (c.getName().equals(name))
                return c;

        return null;
    }

    /** Creates a new Category, based on the passed in parameter
     *
     * @param category - Category to add
     * @return - category that was created with an ID
     */
    @Override
    public Category createCategory(Category category) {
        if(category != null) {
             category.setId(nextCategoryID++);
             categories.add(category);
        }

        return category;
    }

    /** Attempts to delete a Category from the stub
     *
     * @param categoryToRemove - Category to be removed
     * @return True/False if the category has been removed
     */
    @Override
    public boolean deleteCategory(Category categoryToRemove){
        return categories.remove(categoryToRemove);
    }

    /** Updates the name of the Category
     *
     * @param newCategory - Category to update data from
     * @return True/False if the update succeeded
     */
    @Override
    public boolean updateCategory(Category newCategory) {
        for(Category category: categories) {
            if(category.getId() == newCategory.getId()) {
                category.setName(newCategory.getName());
                return true;
            }
        }

        return false;
    }

    //endregion

}
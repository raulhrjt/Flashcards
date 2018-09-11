package com.queens.flashcards.UnitTests.Logic;

import com.queens.flashcards.Logic.CategoryManagementService;
import com.queens.flashcards.Logic.Exception.CategoryNotFoundException;
import com.queens.flashcards.Logic.Exception.DuplicateNameException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.TestServices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class CategoryManagementServiceTest {
    private CategoryManagementService categoryManagementService;

    @Before
    public void setUp() {
        categoryManagementService = new CategoryManagementService(TestServices.getCategoryPersistence(), TestServices.getFcPersistence(), TestServices.getCategoryValidator());
    }

    @After
    public void tearDown() {
        for (Category c : categoryManagementService.getAllCategories())
            categoryManagementService.deleteCategory(c);
    }

    @Test (expected = EmptyNameException.class)
    public void testCategoryWithEmptyName()throws EmptyNameException, DuplicateNameException {
        String name = "";

        Category category = new Category(name);

        System.out.println("\nStarting testCategoryWithNoName (No second print expected)");

        categoryManagementService.createNewCategory(category);

        System.out.println("\nFinished testCategoryWithNoName");
    }

    @Test (expected = DuplicateNameException.class)
    public void testCategoryWithDuplicateName() throws EmptyNameException, DuplicateNameException {
        Category c1 = new Category("Name1");
        Category c2 = new Category("Name1");

        System.out.println("\nStarting testCategoryWithDuplicateName (No second print expected)");

        categoryManagementService.createNewCategory(c1);
        categoryManagementService.createNewCategory(c2);
    }

    @Test
    public void testCategoryUpdate()throws EmptyNameException, CategoryNotFoundException, DuplicateNameException {
        boolean updated;
        String name1 = "oldCategory";
        String updatedName = "updatedCategory";
        Category category1 = new Category(name1);
        Category createdCategory;

        System.out.println("\nStarting testCategoryUpdate");

        createdCategory = categoryManagementService.createNewCategory(category1);

        assertNotNull(createdCategory);

        createdCategory.setName(updatedName);

        updated = categoryManagementService.updateCategory(createdCategory);

        assertTrue(updated);
        assertTrue(createdCategory.getName().equals(updatedName));

        System.out.println("\nFinished testCategoryUpdate");
    }

    @Test
    public void testCategoryGetAll()throws EmptyNameException, DuplicateNameException {
        String name1 = "test1";
        String name2 = "test2";
        List<Category> testList;
        Category category1 = new Category(name1);
        Category category2 = new Category(name2);

        System.out.println("\nStarting testCategoryGet");

        categoryManagementService.createNewCategory(category1);
        testList = categoryManagementService.getAllCategories();

        assertNotNull(testList);
        assertTrue(testList.size() > 0);
        assertTrue(testList.contains(category1));

        categoryManagementService.createNewCategory(category2);
        testList = categoryManagementService.getAllCategories();

        assertNotNull(testList);
        assertTrue(testList.size() > 1);
        assertTrue(testList.contains(category2));

        System.out.println("\nFinished testCategoryGet");
    }

    @Test
    public void testCategoryGetById() throws EmptyNameException, DuplicateNameException {
        Category category = new Category("Test");

        System.out.println("\nStarting testCategoryGetById()");

        category = categoryManagementService.createNewCategory(category);
        Category pulled = categoryManagementService.getCategoryById(category.getId());
        assertTrue(pulled != null);

        System.out.println("\nFinished testCategoryGetById()");
    }

    @Test
    public void testCategoryGetByName() throws EmptyNameException, DuplicateNameException {
        Category category = new Category("Test");

        System.out.println("\nStarting testCategoryGetByName()");

        category = categoryManagementService.createNewCategory(category);
        Category pulled = categoryManagementService.getCategoryByName(category.getName());
        assertTrue(pulled != null);

        System.out.println("\nFinished testCategoryGetByName()");
    }

    @Test (expected = EmptyNameException.class)
    public void testCategoryInvalidNameUpdate()throws EmptyNameException, CategoryNotFoundException, DuplicateNameException {
        boolean updated;
        String name1 = "oldCategory";
        String updatedName = "";
        Category category1 = new Category(name1);
        Category createdCategory;

        System.out.println("\nStarting testCategoryInvalidNameUpdate (No second print expected)");

        createdCategory = categoryManagementService.createNewCategory(category1);

        assertNotNull(createdCategory);

        createdCategory.setName(updatedName);

        updated = categoryManagementService.updateCategory(createdCategory);

        assertFalse(updated);

        System.out.println("\nFinished testCategoryInvalidNameUpdate");
    }

    @Test
    public void testCategoryValidDelete()throws EmptyNameException, DuplicateNameException {
        boolean deleted;
        String name1 = "oldCategory";
        Category category1 = new Category(name1);
        Category createdCategory;

        System.out.println("\nStarting testCategoryValidDelete");

        createdCategory = categoryManagementService.createNewCategory(category1);

        assertNotNull(createdCategory);

        deleted = categoryManagementService.deleteCategory(category1);

        assertTrue(deleted);

        System.out.println("\nFinished testCategoryValidDelete");
    }

    @Test
    public void testCategoryDeleteWithoutObject() {
        boolean deleted;
        String name1 = "oldCategory";
        long randomID = 15500L;
        Category category1 = new Category(name1, randomID);

        System.out.println("\nStarting testCategoryDeleteWithoutObject");

        deleted = categoryManagementService.deleteCategory(category1);

        assertFalse(deleted);

        System.out.println("\nFinished testCategoryDeleteWithoutObject");
    }

    @Test (expected = CategoryNotFoundException.class)
    public void testCategoryUpdateNotExist() throws EmptyNameException, CategoryNotFoundException, DuplicateNameException {
        Category wrong = new Category("Wrong", -1L);

        System.out.println("\nStarting testCategoryUpdateNotExist");
        categoryManagementService.updateCategory(wrong);
        System.out.println("Finished testCategoryUpdateNotExist");
    }
}

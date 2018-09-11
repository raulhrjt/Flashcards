package com.queens.flashcards.UnitTests.Logic;

import com.queens.flashcards.Logic.FCManagementService;
import com.queens.flashcards.Model.FlashcardCategory;
import com.queens.flashcards.TestServices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;

public class FCManagementServiceTest {
    private FCManagementService fcManagementService;

    @Before
    public void setUp() {
        fcManagementService = new FCManagementService(TestServices.getFcPersistence());
    }

    @After
    public void tearDown() {
        for (FlashcardCategory fc : fcManagementService.getAllFlashcardCategories())
            fcManagementService.removeFlashcardCategory(fc.getFlashcardId(), fc.getCategoryId());
    }

    @Test
    public void testGetFlashcardsInCategory() {

        System.out.println("\nStarting testGetFlashcardsInCategory");

        long categoryId = 9999;
        for (long i = 9999; i < 10009; i++)
            fcManagementService.addFlashcardCategory(i, categoryId);

        List<Long> flashcardIds = fcManagementService.getFlashcardsInCategory(categoryId);
        for (long i = 9999; i < 10009; i++)
            assertTrue(flashcardIds.contains(i));

        System.out.println("Finished testGetFlashcardsInCategory");
    }

    @Test
    public void testGetCategoriesWithFlashcard() {

        System.out.println("\nStarting testGetCategoriesWithFlashcard");

        long flashcardId = 999;
        for (long i = 999; i < 1009; i++)
            fcManagementService.addFlashcardCategory(flashcardId, i);

        List<Long> categoryIds = fcManagementService.getCategoriesWithFlashcard(flashcardId);
        for (long i = 999; i < 1009; i++)
            assertTrue(categoryIds.contains(i));

        System.out.println("Finished testGetCategoriesWithFlashcard");
    }

    @Test
    public void testAddFlashcardCategory() {

        System.out.println("\nStarting testAddFlashcardCategory");

        long flashcardId = 99999, categoryId = 99999;
        assertTrue(fcManagementService.addFlashcardCategory(flashcardId, categoryId));

        System.out.println("Finished testAddFlashcardCategory");
    }

    @Test
    public void testRemoveFlashcardCategory() {

        System.out.println("\nStarting testRemoveFlashcardCategory");

        long flashcardId = 111111, categoryId = 1111111;
        fcManagementService.addFlashcardCategory(flashcardId, categoryId);
        assertTrue(fcManagementService.removeFlashcardCategory(flashcardId, categoryId));

        System.out.println("Finished testRemoveFlashcardCategory");
    }

    @Test
    public void testRemoveFlashcard() {

        System.out.println("\nStarting testRemoveFlashcard");

        long flashcardId = 777;
        for (long i = 100; i < 110; i++)
            fcManagementService.addFlashcardCategory(flashcardId, i);

        assertTrue(fcManagementService.removeFlashcard(flashcardId) == 10);

        System.out.println("Finished testRemoveFlashcard");
    }

    @Test
    public void testRemoveCategory() {

        System.out.println("\nStarting testRemoveCategory");

        long categoryId = 777;
        for (long i = 100; i < 110; i++)
            fcManagementService.addFlashcardCategory(i, categoryId);

        assertTrue(fcManagementService.removeCategory(categoryId) == 10);

        System.out.println("Finished testRemoveCategory");
    }

}

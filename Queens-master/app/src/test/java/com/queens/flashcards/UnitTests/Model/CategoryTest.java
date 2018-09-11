package com.queens.flashcards.UnitTests.Model;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;

import org.junit.Test;
import static org.junit.Assert.*;

public class CategoryTest {
    @Test
    public void testCreateCategoryWithName() {
        System.out.println("\nStarting CreateCategoryWithName");
        String toTest = "Testing createCategory";
        Category test = new Category(toTest);

        assertTrue(toTest.equals(test.getName()));
        assertTrue(-1 == test.getId());

        System.out.println("Finished CreateCategoryWithName");
    }

    @Test
    public void testCreateCategoryWithNameAndId() {
        System.out.println("\nStarting testCreateCategoryWithNameAndId");
        String toTest = "Testing createCategory";
        long numTest = 01;

        Category test = new Category(toTest, numTest);

        assertTrue(toTest.equals(test.getName()));
        assertTrue(numTest == test.getId());

        System.out.println("Finished  testCreateCategoryWithNameAndId");
    }

    @Test
    public void testCategoryWithNoFlashCard() {
        System.out.println("\nStarting testCategoryWithNoFlashCard");

        String toTest = "Testing createCategory";
        long numTest = 01;

        Category test = new Category(toTest, numTest);

        assertTrue(test.getFlashcardIds().size() == 0);

        System.out.println("Finished testCategoryWithNoFlashCard");
    }

    @Test
    public void testCategoryWithOneFlashCard(){
        System.out.println("\nStarting testCategoryWithOneFlashCard");

        String toTest = "Testing createCategory";
        long numTest = 01;

        Flashcard testFlashCard = new Flashcard();
        Category test = new Category(toTest, numTest);

        test.add(testFlashCard.getId());

        assertTrue(test.getFlashcardIds().size() == 1);

        System.out.println("Finished testCategoryWithOneFlashCard");
    }

    @Test
    public void testCategoryWithFlashCards(){
        System.out.println("\nStarting testCategoryWithFlashCards");

        String toTest = "Testing createCategory";
        long numTest = 01;

        Category test = new Category(toTest, numTest);
        long id1 = 1;
        long id2 = 2;
        long id3 = 3;

        assertTrue(test.getFlashcardIds().size() == 0);

        test.add(id1);
        test.add(id2);
        test.add(id3);

        assertTrue(test.getFlashcardIds().size() == 3);

        System.out.println("Finished testCategoryWithFlashCards");
    }

    @Test
    public void testCategoryRemovingOneFlashCard(){
        System.out.println("\ntestCategoryRemovingOneFlashCard");

        String toTest = "Testing createCategory";
        long numTest = 01;

        Category test = new Category(toTest, numTest);
        long id1 = 1;
        long id2 = 2;
        long id3 = 3;

        test.add(id1);
        test.add(id2);
        test.add(id3);

        assertTrue(test.getFlashcardIds().size() == 3);

        test.remove(id1);

        assertTrue(test.getFlashcardIds().size() == 2);

        System.out.println("Finished testCategoryRemovingOneFlashCard");
    }

    @Test
    public void testCategoryRemovingAllFlashCards(){
        System.out.println("\nStarting testCategoryRemovingAllFlashCards");
        String toTest = "Testing createCategory";
        long numTest = 01;

        Category test = new Category(toTest, numTest);
        long id1 = 1;
        long id2 = 2;
        long id3 = 3;

        test.add(id1);
        test.add(id2);
        test.add(id3);

        assertTrue(test.getFlashcardIds().size() == 3);

        test.clear();

        assertTrue(test.getFlashcardIds().size() == 0);

        System.out.println("Finished testCategoryRemovingAllFlashCards");
    }


}

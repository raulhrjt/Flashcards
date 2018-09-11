package com.queens.flashcards.UnitTests.Model;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FlashcardTest {
    private Flashcard flashcard;
    FlashcardAnswer flashcardAnswer;

    @Test
    public void testFlashcardWithID() {
        flashcardAnswer = new FlashcardTextAnswer("");
        String name = "Test name";
        String question = "Stuff, More stuff, and more stuff?";
        Long id = 9001l;

        System.out.println("\nStarting testFlashcardWithID");
        flashcard = new Flashcard(name, question, flashcardAnswer, id);

        assertNotNull(flashcard);
        assertTrue(question.equals(flashcard.getQuestion()));
        assertTrue(id == flashcard.getId());
        assertTrue(flashcard.getAnswer() == flashcardAnswer);

        System.out.println("Finished testFlashcardWithID");
    }

    @Test
    public void testFlashcardNoID() {
        flashcardAnswer = new FlashcardTextAnswer();
        String name = "Test name";
        String question = "Stuff, More stuff, and more stuff?";

        System.out.println("\nStarting testFlashcardNoID");
        flashcard = new Flashcard(name, question, flashcardAnswer);

        assertNotNull(flashcard);
        assertTrue(question.equals(flashcard.getQuestion()));
        assertTrue(flashcard.getAnswer() == flashcardAnswer);
        assertTrue(flashcard.getId() == -1);

        System.out.println("Finished testFlashcardNoID");
    }

    @Test
    public void testEmptyFlashcard() {
        System.out.println("\nStarting testEmptyFlashcard");
        flashcard = new Flashcard();

        assertNotNull(flashcard);
        assertTrue(flashcard.getQuestion().equals(""));
        assertTrue(-1 == flashcard.getId());
        assertNotNull(flashcard.getAnswer());

        System.out.println("Finished testEmptyFlashcard");
    }

    /*
    @Test
    public void testFlashcardAddCategory() {
        Long categoryID = 0l;

        System.out.println("\nStarting testFlashcardAddCategory");
        flashcard = new Flashcard();

        assertFalse(flashcard.containsCategory(categoryID));
        flashcard.addToCategory(categoryID);
        assertTrue(flashcard.containsCategory(categoryID));

        System.out.println("Finished testFlashcardAddCategory");
    }

    @Test
    public void testFlashcardRemoveCategory() {
        Long categoryID = 0l;

        System.out.println("\nStarting testFlashcardRemoveCategory");
        flashcard = new Flashcard();
        flashcard.addToCategory(categoryID);

        assertTrue(flashcard.containsCategory(categoryID));
        flashcard.removeFromCategory(categoryID);
        assertFalse(flashcard.containsCategory(categoryID));

        System.out.println("Finished testFlashcardRemoveCategory");
    }
    */

    /*
    @Test
    public void testFlashcardAllCategories() {
        Long num1 = 0l;
        Long num2 = 1l;
        Long num3 = 2l;
        ArrayList<Long> categories = new ArrayList<>();
        categories.add(num1);
        categories.add(num2);
        categories.add(num3);
        List<Long> returnedCategories;

        System.out.println("\nStarting testFlashcardAllCategories");
        flashcard = new Flashcard("Test name", "", new FlashcardTextAnswer(""), 0, categories);

        assertTrue(flashcard.containsCategory(num1));
        assertTrue(flashcard.containsCategory(num2));
        assertTrue(flashcard.containsCategory(num3));

        returnedCategories = flashcard.getAllCategories();
        assertNotNull(returnedCategories);

        //Contains ONLY the categories that were passed in with the constructor
        assertTrue(returnedCategories.contains(num1));
        assertTrue(returnedCategories.contains(num2));
        assertTrue(returnedCategories.contains(num3));
        assertTrue(returnedCategories.size() == categories.size());

        System.out.println("Finished testFlashcardAllCategories");
    }

    @Test
    public void testFlashcardDuplicateCategories() {
        Long num1 = 0l;

        System.out.println("\nStarting testFlashcardDuplicateCategories");
        flashcard = new Flashcard();

        assertFalse(flashcard.containsCategory(num1));
        flashcard.addToCategory(num1);
        flashcard.addToCategory(num1);
        assertTrue(flashcard.containsCategory(num1));
        flashcard.removeFromCategory(num1);
        assertFalse(flashcard.containsCategory(num1));

        System.out.println("Finished testFlashcardDuplicateCategories");
    }
    */
}

package com.queens.flashcards.UnitTests.Model;

import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;

import org.junit.Test;
import static org.junit.Assert.*;

public class FlashcardAnswerTest {
    @Test
    public void testValidFlashcardAnswer() {
        FlashcardAnswer flashcardAnswer;
        String textAnswer = "Something something something";
        Long id = 15l;

        System.out.println("\nStarting testValidFlashcardAnswer");

        flashcardAnswer = new FlashcardTextAnswer(textAnswer, id);

        assertNotNull(flashcardAnswer);
        assertTrue(textAnswer.equals(flashcardAnswer.getAnswer()));
        assertTrue(id == flashcardAnswer.getId());

        System.out.println("Finished testValidFlashcardAnswer");
    }

    @Test
    public void testNoTextAnswerFlashcardAnswer() {
        FlashcardAnswer flashcardAnswer;
        String textAnswer = "";
        Long id = 0l;

        System.out.println("\nStarting testNoTextAnswerFlashcardAnswer");

        flashcardAnswer = new FlashcardTextAnswer(textAnswer, id);

        assertNotNull(flashcardAnswer);
        assertTrue(textAnswer.equals(flashcardAnswer.getAnswer()));
        assertTrue(id == flashcardAnswer.getId());

        System.out.println("Finished testNoTextAnswerFlashcardAnswer");
    }

    @Test
    public void testMissingIDFlashcardAnswer() {
        FlashcardAnswer flashcardAnswer;
        String textAnswer = "";

        System.out.println("\nStarting testMissingIDFlashcardAnswer");

        flashcardAnswer = new FlashcardTextAnswer(textAnswer);

        assertNotNull(flashcardAnswer);
        assertTrue(textAnswer.equals(flashcardAnswer.getAnswer()));
        assertTrue(0 == flashcardAnswer.getId());

        System.out.println("Finished testMissingIDFlashcardAnswer");
    }
}

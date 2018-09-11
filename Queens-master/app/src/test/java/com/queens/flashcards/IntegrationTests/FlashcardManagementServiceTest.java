package com.queens.flashcards.IntegrationTests;

import com.queens.flashcards.Logic.CategoryManagementService;
import com.queens.flashcards.Logic.Exception.DuplicateNameException;
import com.queens.flashcards.Logic.Exception.EmptyAnswerException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Logic.Exception.EmptyQuestionException;
import com.queens.flashcards.Logic.Exception.FlashcardNotFoundException;
import com.queens.flashcards.Logic.FlashcardManagementService;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Services;
import com.queens.flashcards.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FlashcardManagementServiceTest {
    private FlashcardManagementService flashcardManagementService;
    private CategoryManagementService categoryManagementService;
    private FlashcardAnswer flashcardAnswer;
    private Flashcard flashcard;
    private File tempdb;

    @Before
    public void setUp() throws IOException {
        tempdb = TestUtils.copyDB();
        flashcardManagementService = new FlashcardManagementService(Services.getFlashcardPersistence(),
                Services.getFcPersistence(),
                Services.getFaTextPersistence(),
                Services.getFaTrueFalsePersistence(),
                Services.getFaMultipleChoicePersistence(),
                Services.getFlashcardValidator(),
                Services.getAnswerValidator());
        categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(), Services.getFcPersistence(), Services.getCategoryValidator());
    }

    @After
    public void tearDown() {
        List<Flashcard> flashcards = flashcardManagementService.getAllFlashcards();
        for (Flashcard f : flashcards)
            flashcardManagementService.deleteFlashcard(f);

        List<Category> categories = categoryManagementService.getAllCategories();
        for (Category c : categories)
            categoryManagementService.deleteCategory(c);

        tempdb.delete();
    }

    @Test(expected = EmptyNameException.class)
    public void testFlashcardWithEmptyName() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        String name = "";
        flashcardAnswer = new FlashcardTextAnswer("Stuff");
        flashcard = new Flashcard(name, "Question", flashcardAnswer);

        System.out.println("\nStarting testFlashcardWithEmptyName (No second print expected)");

        flashcardManagementService.createNewFlashcard(flashcard);

        System.out.println("Finished testFlashcardWithEmptyName");
    }

    @Test (expected = DuplicateNameException.class)
    public void testFlashcardsWithDuplicateName() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        Flashcard f1 = new Flashcard("Name1", "Question 1", new FlashcardTextAnswer("Answer1"));
        Flashcard f2 = new Flashcard("Name1", "Question 2", new FlashcardTextAnswer("Answer2"));

        System.out.println("\nStarting testFlashcardsWithDuplicateName (No second print expected)");

        flashcardManagementService.createNewFlashcard(f1);
        flashcardManagementService.createNewFlashcard(f2);
    }

    @Test (expected = EmptyQuestionException.class)
    public void testFlashcardWithEmptyQuestion() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        String question = "";
        flashcardAnswer = new FlashcardTextAnswer("Stuff");
        flashcard = new Flashcard("Name", question, flashcardAnswer);

        System.out.println("\nStarting testFlashcardWithEmptyQuestion (No second print expected)");

        flashcardManagementService.createNewFlashcard(flashcard);

        System.out.println("Finished testFlashcardWithEmptyQuestion");
    }

    @Test (expected = EmptyAnswerException.class)
    public void testFlashcardWithNoAnswer() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        String question = "Some question goes here";
        flashcard = new Flashcard("Name", question, null);

        System.out.println("\nStarting testFlashcardWithNoAnswer (No second print expected)");

        flashcardManagementService.createNewFlashcard(flashcard);

        System.out.println("Finished testFlashcardWithNoAnswer");
    }

    @Test (expected = EmptyAnswerException.class)
    public void testFlashcardWithEmptyTextAnswer() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        flashcardAnswer = new FlashcardTextAnswer("");
        String question = "Some question goes here";
        flashcard = new Flashcard("Name", question, flashcardAnswer);

        System.out.println("\nStarting testFlashcardWithEmptyTextAnswer (No second print expected)");

        flashcardManagementService.createNewFlashcard(flashcard);

        System.out.println("Finished testFlashcardWithEmptyTextAnswer");
    }

    @Test
    public void testFlashcardWithValidQuestionAnswer() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        String answerText = "A great answer goes here";
        flashcardAnswer = new FlashcardTextAnswer(answerText);
        String question = "Some question goes here";
        flashcard = new Flashcard("Name", question, flashcardAnswer);

        System.out.println("\nStarting testFlashcardWithValidQuestionAnswer");

        flashcard = flashcardManagementService.createNewFlashcard(flashcard);

        assertNotNull(flashcard);
        assertTrue(question.equals(flashcard.getQuestion()));

        FlashcardAnswer returnedAnswer = flashcard.getAnswer();
        assertNotNull(returnedAnswer);
        assertTrue(returnedAnswer.getAnswer().equals(answerText));

        System.out.println("Finished testFlashcardWithValidQuestionAnswer");
    }

    @Test
    public void testFlashcardWithValidMCQuestionAnswer() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        String answerText = "A great answer goes here";
        flashcardAnswer = new FlashcardMCAnswer(answerText, new ArrayList<String>() {{add("One"); add("Two"); add("Three");}});
        String question = "Some question goes here";
        flashcard = new Flashcard("Name", question, flashcardAnswer);

        System.out.println("\nStarting testFlashcardWithValidMCQuestionAnswer");

        flashcard = flashcardManagementService.createNewFlashcard(flashcard);

        assertNotNull(flashcard);
        assertTrue(question.equals(flashcard.getQuestion()));

        FlashcardAnswer returnedAnswer = flashcard.getAnswer();
        assertNotNull(returnedAnswer);
        assertTrue(returnedAnswer.getAnswer().equals(answerText));

        System.out.println("Finished testFlashcardWithValidMCQuestionAnswer");
    }

    @Test (expected = EmptyAnswerException.class)
    public void testFlashcardWithInvalidMCWrong() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        Flashcard f = new Flashcard("Name", "Question", new FlashcardMCAnswer("Yay", new ArrayList<String>(){{add(null);}}));

        System.out.println("\nStarting testFlashcardWithInvalidMCWrong (No second print expected)");

        flashcardManagementService.createNewFlashcard(f);
    }

    @Test (expected = EmptyAnswerException.class)
    public void testFlashcardWithInvalidMCCorrect() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        Flashcard f = new Flashcard("name", "Question", new FlashcardMCAnswer(null, new ArrayList<String>()));

        System.out.println("\nStarting testFlashcardWithInvalidMCCorrect (No second print expected)");

        flashcardManagementService.createNewFlashcard(f);
    }

    @Test
    public void testFlashcardWithValidTFQuestionAnswer() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        final boolean answer = true;
        Flashcard f = new Flashcard("Name", "True/false?", new FlashcardTFAnswer(answer));

        System.out.println("\nStarting testFlashcardWithValidTFQuestionAnswer");

        f = flashcardManagementService.createNewFlashcard(f);

        Flashcard returned = flashcardManagementService.getFlashcardById(f.getId());

        assertNotNull(returned);
        assertTrue(returned.getAnswer() instanceof FlashcardTFAnswer);
        assertTrue(((FlashcardTFAnswer)returned.getAnswer()).getAnswerIsTrue() == answer);

        System.out.println("Finished testFlashcardWithValidTFQuestionAnswer");
    }

    @Test
    public void testFlashcardGetById() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {

        // Create card
        Flashcard f = new Flashcard("Name", "Question", new FlashcardTextAnswer("Answer"));

        System.out.println("\nStarting testFlashcardGetById");

        // Add card
        flashcardManagementService.createNewFlashcard(f);

        // Get card
        Flashcard get = flashcardManagementService.getFlashcardById(f.getId());

        assertTrue(get != null);

        System.out.println("Finished testFlashcardGetById");
    }

    @Test
    public void testFlashcardGetByName() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        Flashcard f = new Flashcard("Name", "Question", new FlashcardTextAnswer("Answer"));

        System.out.println("\nStarting testFlashcardGetByName");

        flashcardManagementService.createNewFlashcard(f);
        Flashcard get = flashcardManagementService.getFlashcardByName(f.getName());
        assertTrue(get != null);

        System.out.println("Finished testFlashcardGetByName");
    }

    @Test
    public void testFlashcardGetAll() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {
        Flashcard f = new Flashcard("NewCard", "NewCardWat", new FlashcardTextAnswer("NewCardWUTWUT"));

        System.out.println("\nStarting testFlashcardGetAll");

        flashcardManagementService.createNewFlashcard(f);
        List<Flashcard> flashcards = flashcardManagementService.getAllFlashcards();
        assertTrue(flashcards.size() > 0);
        assertTrue(flashcards.contains(f));

        System.out.println("Finished testFlashcardGetAll");
    }

    @Test
    public void testFlashcardGetInCategory() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, DuplicateNameException {

        System.out.println("\nStarting testFlashcardGetInCategory");

        Flashcard flashcard1 = new Flashcard("Other", "Question", new FlashcardTextAnswer("Answer"));
        Flashcard flashcard2 = new Flashcard("Other2", "Question2", new FlashcardTextAnswer("Answer2"));
        flashcard1 = flashcardManagementService.createNewFlashcard(flashcard1);
        flashcard2 = flashcardManagementService.createNewFlashcard(flashcard2);

        Category category = new Category("Category");
        category.add(flashcard1.getId());
        category.add(flashcard2.getId());

        categoryManagementService.createNewCategory(category);
        List<Flashcard> flashcards = flashcardManagementService.getFlashcardsInCategory(category);

        assertTrue(flashcards.size() == 2);

        System.out.println("Finished testFlashcardGetInCategory");

    }

    @Test
    public void testFlashcardDelete() throws EmptyNameException, EmptyAnswerException, EmptyQuestionException, DuplicateNameException {

        // Create card
        Flashcard f = new Flashcard("Name", "Question", new FlashcardTextAnswer("Answer"));

        System.out.println("\nStarting testFlashcardDelete");

        // Add card
        flashcardManagementService.createNewFlashcard(f);

        // Delete card
        flashcardManagementService.deleteFlashcard(f);

        // Make sure it's deleted
        Flashcard retrieve = flashcardManagementService.getFlashcardById(f.getId());
        assertTrue(retrieve == null);

        System.out.println("Finished testFlashcardDelete");
    }

    @Test
    public void testFlashcardUpdateTextAnswer() throws EmptyNameException, EmptyAnswerException, EmptyQuestionException, FlashcardNotFoundException, DuplicateNameException {

        // Create cards
        Flashcard original = new Flashcard("Name", "Question", new FlashcardTextAnswer("Answer"));
        Flashcard update = new Flashcard(original);

        System.out.println("\nStarting testFlashcardUpdate");

        // Add card
        update = flashcardManagementService.createNewFlashcard(update);

        // Modify & Update card
        update.setName("New Name");
        update.setQuestion("New Question");
        ((FlashcardTextAnswer)update.getAnswer()).setTextAnswer("New Answer");

        flashcardManagementService.updateFlashcard(update);

        // Ensure it was changed
        Flashcard updated = flashcardManagementService.getFlashcardById(update.getId());

        assertTrue(!updated.getName().equals(original.getName()));
        assertTrue(!updated.getQuestion().equals(original.getQuestion()));
        assertTrue(!updated.getAnswer().getAnswer().equals(original.getAnswer().getAnswer()));

        System.out.println("Finished testFlashcardUpdate");
    }

    @Test
    public void testFlashcardUpdateTFAnswer() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, FlashcardNotFoundException, DuplicateNameException {
        // Create cards
        Flashcard original = new Flashcard("Name", "Question", new FlashcardTFAnswer(true));
        Flashcard update = new Flashcard(original);

        System.out.println("\nStarting testFlashcardUpdate");

        // Add card
        update = flashcardManagementService.createNewFlashcard(update);

        // Modify & Update card
        update.setName("New Name");
        update.setQuestion("New Question");
        ((FlashcardTFAnswer)update.getAnswer()).setAnswerIsTrue(false);

        flashcardManagementService.updateFlashcard(update);

        // Ensure it was changed
        Flashcard updated = flashcardManagementService.getFlashcardById(update.getId());

        assertTrue(!updated.getName().equals(original.getName()));
        assertTrue(!updated.getQuestion().equals(original.getQuestion()));
        assertTrue(!updated.getAnswer().getAnswer().equals(original.getAnswer().getAnswer()));

        System.out.println("Finished testFlashcardUpdate");
    }

    @Test
    public void testFlashcardUpdateMCAnswer() throws EmptyNameException, EmptyQuestionException, EmptyAnswerException, FlashcardNotFoundException, DuplicateNameException {
        // Create cards
        Flashcard original = new Flashcard("Name", "Question", new FlashcardMCAnswer("Answer", new ArrayList<String>(){{add("Wrong1"); add("Wrong2");}}));
        Flashcard update = new Flashcard(original);

        System.out.println("\nStarting testFlashcardUpdate");

        // Add card
        update = flashcardManagementService.createNewFlashcard(update);

        // Modify & Update card
        update.setName("New Name");
        update.setQuestion("New Question");
        FlashcardMCAnswer updateAnswer = (FlashcardMCAnswer) update.getAnswer();
        updateAnswer.setAnswer("NewAnswer");
        updateAnswer.setWrongAnswers(new ArrayList<String>(){{add("NewWrong1"); add("NewWrong2");}});

        flashcardManagementService.updateFlashcard(update);

        // Ensure it was changed
        Flashcard updated = flashcardManagementService.getFlashcardById(update.getId());

        assertTrue(!updated.getName().equals(original.getName()));
        assertTrue(!updated.getQuestion().equals(original.getQuestion()));
        assertTrue(!updated.getAnswer().getAnswer().equals(original.getAnswer().getAnswer()));
        for (String wrongAnswer : ((FlashcardMCAnswer)updated.getAnswer()).getWrongAnswers())
            assertTrue(!((FlashcardMCAnswer)original.getAnswer()).getWrongAnswers().contains(wrongAnswer));

        System.out.println("Finished testFlashcardUpdate");
    }

    @Test (expected = FlashcardNotFoundException.class)
    public void testFlashcardUpdateNotExist() throws EmptyNameException, EmptyAnswerException, EmptyQuestionException, FlashcardNotFoundException, DuplicateNameException {
        Flashcard wrong = new Flashcard("Wrong","Wrong", new FlashcardTextAnswer("Wrong", -1),-1,"Wrong");

        System.out.println("\nStarting testFlashcardUpdateNotExist");
        flashcardManagementService.updateFlashcard(wrong);
        System.out.println("Finished testFlashcardUpdateNotExist");
    }
}

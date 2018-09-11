package com.queens.flashcards.AcceptanceTests;

import android.app.Application;
import android.net.sip.SipErrorCode;
import android.test.ApplicationTestCase;

import com.queens.flashcards.Logic.CategoryManagementService;
import com.queens.flashcards.Logic.FlashcardManagementService;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Services;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.List;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TextFlashcardsTest.class,
        CategoryTests.class,
        GuessingGameTests.class,
        CustomizableFlashcardsTests.class,
        GuessingGameOptionsTest.class,
        GuessingGameModeTest.class
})
public class AllAcceptanceTests extends ApplicationTestCase<Application> {
    public AllAcceptanceTests() {
        super(Application.class);
    }

    @BeforeClass
    public static void onlyOnce() {
        // Wipe DB to ensure a clean slate for acceptance testing
        FlashcardManagementService fms = new FlashcardManagementService(Services.getFlashcardPersistence(),
                Services.getFcPersistence(),
                Services.getFaTextPersistence(),
                Services.getFaTrueFalsePersistence(),
                Services.getFaMultipleChoicePersistence(),
                Services.getFlashcardValidator(),
                Services.getAnswerValidator());
        List<Flashcard> flashcards = fms.getAllFlashcards();
        for (Flashcard f : flashcards)
            fms.deleteFlashcard(f);

        CategoryManagementService cms = new CategoryManagementService(Services.getCategoryPersistence(),
                Services.getFcPersistence(),
                Services.getCategoryValidator());
        List<Category> categories = cms.getAllCategories();
        for (Category c : categories)
            cms.deleteCategory(c);
    }
}


package com.queens.flashcards;

import com.queens.flashcards.Logic.Validation.CategoryValidator;
import com.queens.flashcards.Logic.Validation.FlashcardAnswerValidator;
import com.queens.flashcards.Logic.Validation.FlashcardValidator;
import com.queens.flashcards.Persistence.Interfaces.CategoryPersistence;
import com.queens.flashcards.Persistence.Interfaces.FAMultipleChoicePersistence;
import com.queens.flashcards.Persistence.Interfaces.FATextPersistence;
import com.queens.flashcards.Persistence.Interfaces.FATrueFalsePersistence;
import com.queens.flashcards.Persistence.Interfaces.FCPersistence;
import com.queens.flashcards.Persistence.Interfaces.FlashcardPersistence;
import com.queens.flashcards.Persistence.Interfaces.ImagePersistence;
import com.queens.flashcards.Persistence.stubs.CategoryPersistenceStub;
import com.queens.flashcards.Persistence.stubs.FAMultipleChoicePersistenceStub;
import com.queens.flashcards.Persistence.stubs.FATextPersistenceStub;
import com.queens.flashcards.Persistence.stubs.FATrueFalsePersistenceStub;
import com.queens.flashcards.Persistence.stubs.FCPersistenceStub;
import com.queens.flashcards.Persistence.stubs.FlashcardPersistenceStub;
import com.queens.flashcards.Persistence.stubs.ImagePersistenceStub;

public class TestServices {
    private static FlashcardPersistence flashcardPersistence = null;
    private static CategoryPersistence categoryPersistence = null;
    private static FCPersistence fcPersistence = null;
    private static FATextPersistence faTextPersistence = null;
    private static FATrueFalsePersistence faTrueFalsePersistence = null;
    private static FAMultipleChoicePersistence faMultipleChoicePersistence = null;
    private static ImagePersistence imagePersistence = null;
    private static FlashcardValidator flashcardValidator = null;
    private static FlashcardAnswerValidator answerValidator = null;
    private static CategoryValidator categoryValidator = null;

    public static synchronized  FlashcardValidator getFlashcardValidator() {
        if (flashcardValidator == null) {
            flashcardValidator = new FlashcardValidator();
        }

        return flashcardValidator;
    }

    public static FlashcardAnswerValidator getAnswerValidator() {
        if(answerValidator == null) {
            answerValidator = new FlashcardAnswerValidator();
        }

        return answerValidator;
    }

    public static CategoryValidator getCategoryValidator() {
        if(categoryValidator == null) {
            categoryValidator = new CategoryValidator();
        }
        return categoryValidator;
    }

    public static synchronized FlashcardPersistence getFlashcardPersistence() {
        if(flashcardPersistence ==  null) {
            flashcardPersistence = new FlashcardPersistenceStub();
        }

        return flashcardPersistence;
    }

    public static synchronized CategoryPersistence getCategoryPersistence() {
        if(categoryPersistence ==  null) {
            categoryPersistence = new CategoryPersistenceStub();
        }

        return categoryPersistence;
    }

    public static synchronized FCPersistence getFcPersistence() {
        if (fcPersistence == null) {
            fcPersistence = new FCPersistenceStub();
        }

        return fcPersistence;
    }

    public static synchronized FATextPersistence getFaTextPersistence() {
        if (faTextPersistence == null) {
            faTextPersistence = new FATextPersistenceStub();
        }

        return faTextPersistence;
    }

    public static synchronized FATrueFalsePersistence getFaTrueFalsePersistence() {
        if (faTrueFalsePersistence == null) {
            faTrueFalsePersistence = new FATrueFalsePersistenceStub();
        }

        return faTrueFalsePersistence;
    }

    public static synchronized FAMultipleChoicePersistence getFaMultipleChoicePersistence() {
        if (faMultipleChoicePersistence == null) {
            faMultipleChoicePersistence = new FAMultipleChoicePersistenceStub();
        }

        return faMultipleChoicePersistence;
    }

    public static synchronized ImagePersistence getImagePersistence() {
        if (imagePersistence == null) {
            imagePersistence = new ImagePersistenceStub();
        }

        return imagePersistence;
    }
}

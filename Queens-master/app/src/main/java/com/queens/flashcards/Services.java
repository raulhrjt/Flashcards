package com.queens.flashcards;

import com.queens.flashcards.Logic.Validation.CategoryValidator;
import com.queens.flashcards.Logic.Validation.FlashcardAnswerValidator;
import com.queens.flashcards.Logic.Validation.FlashcardValidator;
import com.queens.flashcards.Persistence.Interfaces.FAMultipleChoicePersistence;
import com.queens.flashcards.Persistence.Interfaces.FATextPersistence;
import com.queens.flashcards.Persistence.Interfaces.FATrueFalsePersistence;
import com.queens.flashcards.Persistence.hsqldb.CategoryPersistenceHSQLDB;
import com.queens.flashcards.Persistence.hsqldb.FAMultipleChoicePersistenceHSQLDB;
import com.queens.flashcards.Persistence.hsqldb.FATextPersistenceHSQLDB;
import com.queens.flashcards.Persistence.hsqldb.FATrueFalsePersistenceHSQLDB;
import com.queens.flashcards.Persistence.hsqldb.FCPersistenceHSQLDB;
import com.queens.flashcards.Persistence.hsqldb.FlashcardPersistenceHSQLDB;
import com.queens.flashcards.Persistence.ImagePersistenceFile;
import com.queens.flashcards.Persistence.Interfaces.CategoryPersistence;
import com.queens.flashcards.Persistence.Interfaces.FCPersistence;
import com.queens.flashcards.Persistence.Interfaces.FlashcardPersistence;

public class Services {
    private static FlashcardPersistence flashcardPersistence = null;
    private static CategoryPersistence categoryPersistence = null;
    private static ImagePersistenceFile imagePersistence = null;
    private static FCPersistence fcPersistence = null;
    private static FATextPersistence faTextPersistence = null;
    private static FATrueFalsePersistence faTrueFalsePersistence = null;
    private static FAMultipleChoicePersistence faMultipleChoicePersistence = null;
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
            flashcardPersistence = new FlashcardPersistenceHSQLDB(Databases.getDBPathName());
        }

        return flashcardPersistence;
    }

    public static synchronized CategoryPersistence getCategoryPersistence() {
        if(categoryPersistence ==  null) {
            categoryPersistence = new CategoryPersistenceHSQLDB(Databases.getDBPathName());
        }

        return categoryPersistence;
    }

    public static synchronized ImagePersistenceFile getImagePersistence() {
        if(imagePersistence == null) {
            imagePersistence = new ImagePersistenceFile();
        }

        return imagePersistence;
    }

    public static synchronized FCPersistence getFcPersistence() {
        if (fcPersistence == null) {
            fcPersistence = new FCPersistenceHSQLDB(Databases.getDBPathName());
        }

        return fcPersistence;
    }

    public static synchronized FATextPersistence getFaTextPersistence() {
        if (faTextPersistence == null) {
            faTextPersistence = new FATextPersistenceHSQLDB(Databases.getDBPathName());
        }

        return faTextPersistence;
    }

    public static synchronized FATrueFalsePersistence getFaTrueFalsePersistence() {
        if (faTrueFalsePersistence == null) {
            faTrueFalsePersistence = new FATrueFalsePersistenceHSQLDB(Databases.getDBPathName());
        }

        return faTrueFalsePersistence;
    }

    public static synchronized FAMultipleChoicePersistence getFaMultipleChoicePersistence() {
        if (faMultipleChoicePersistence == null) {
            faMultipleChoicePersistence = new FAMultipleChoicePersistenceHSQLDB(Databases.getDBPathName());
        }

        return faMultipleChoicePersistence;
    }
}

package com.queens.flashcards.Logic;

import android.support.annotation.NonNull;

import com.queens.flashcards.Logic.Exception.DuplicateNameException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Logic.Exception.FlashcardNotFoundException;
import com.queens.flashcards.Logic.Validation.FlashcardAnswerValidator;
import com.queens.flashcards.Logic.Validation.FlashcardValidator;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Persistence.Factory.FAPersistenceFactory;
import com.queens.flashcards.Persistence.Factory.FAPersistenceFactoryMaker;
import com.queens.flashcards.Persistence.Interfaces.FAMultipleChoicePersistence;
import com.queens.flashcards.Persistence.Interfaces.FAPersistence;
import com.queens.flashcards.Persistence.Interfaces.FATextPersistence;
import com.queens.flashcards.Persistence.Interfaces.FATrueFalsePersistence;
import com.queens.flashcards.Persistence.Interfaces.FCPersistence;
import com.queens.flashcards.Persistence.Interfaces.FlashcardPersistence;
import com.queens.flashcards.Logic.Exception.EmptyAnswerException;
import com.queens.flashcards.Logic.Exception.EmptyQuestionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for application logic dealing with Flashcards.
 */
public class FlashcardManagementService {

    //region Members

    private FlashcardPersistence flashcardPersistence;
    private FCPersistence fcPersistence;
    private FlashcardAnswerManagementService flashcardAnswerManagementService;
    private FlashcardValidator validator;

    //endregion

    //region Constructor

    /**
     * Initializes a new instance of a FlashcardManagementService with the specified persistence implementations.
     * @param flashcardPersistence The Flashcard persistence.
     * @param fcPersistence The FlashcardCategory persistence.
     * @param faTextPersistence The FlashcardTextAnswer persistence.
     * @param faTrueFalsePersistence The FlashcardTFAnswer persistence.
     * @param faMultipleChoicePersistence The FlashcardMCAnswer persistence.
     * @param validator The Validator for the Flashcard
     * @param answerValidator The Validator for the FlashcardAnswer
     */
    public FlashcardManagementService(@NonNull FlashcardPersistence flashcardPersistence,
                                      @NonNull FCPersistence fcPersistence,
                                      @NonNull FATextPersistence faTextPersistence,
                                      @NonNull FATrueFalsePersistence faTrueFalsePersistence,
                                      @NonNull FAMultipleChoicePersistence faMultipleChoicePersistence,
                                      @NonNull FlashcardValidator validator,
                                      @NonNull FlashcardAnswerValidator answerValidator)
    {
        this.flashcardPersistence = flashcardPersistence;
        this.fcPersistence = fcPersistence;
        this.validator = validator;
        this.flashcardAnswerManagementService = new FlashcardAnswerManagementService(faTextPersistence, faTrueFalsePersistence, faMultipleChoicePersistence, answerValidator);
    }

    //endregion

    //region Public Methods

    /**
     * Gets a Flashcard with the specified ID.
     * @param id The ID of the Flashcard to get.
     * @return The Flashcard with the specified ID, or null if it doesn't exist.
     */
    public Flashcard getFlashcardById(long id) {

        Flashcard flashcard = flashcardPersistence.getFlashcardById(id);

        if (flashcard != null) {
            FlashcardAnswer answer = flashcardAnswerManagementService.getFlashcardAnswerFor(flashcard);
            flashcard.setAnswer(answer);
        }

        return flashcard;
    }

    /**
     * Gets a Flashcard with the specified name.
     * @param name THe name of the Flashcard to get.
     * @return The Flaschard with the specified name, or null if it doesn't exist.
     */
    public Flashcard getFlashcardByName(String name) {
        Flashcard flashcard = flashcardPersistence.getFlashcardByName(name);

        if (flashcard != null) {
            FlashcardAnswer answer = flashcardAnswerManagementService.getFlashcardAnswerFor(flashcard);
            flashcard.setAnswer(answer);
        }

        return flashcard;
    }


    /** Gets all Flashcards that are part of a given Category
     *
     * @param category - Category that the Flashcard must belong to
     * @return - List of Flashcards belonging to the Category
     */
    public List<Flashcard> getFlashcardsInCategory(Category category) {
        List<Flashcard> flashcards = new ArrayList<>();

        List<Long> flashcardIds = fcPersistence.getFlashcardsInCategory(category.getId());
        for (Long id : flashcardIds) {
            final Flashcard f = getFlashcardById(id);
            flashcards.add(f);
        }

        return flashcards;
    }

    /**
     * Gets a list of all Flashcards.
     * @return The list of all Flashcards.
     */
    public List<Flashcard> getAllFlashcards() {
        List<Flashcard> flashcards = flashcardPersistence.getAllFlashcards();

        for (Flashcard f : flashcards) {
            final FlashcardAnswer answer = flashcardAnswerManagementService.getFlashcardAnswerFor(f);
            f.setAnswer(answer);
        }

        return flashcards;
    }

    /** Attempts to add the new Flashcard to the database
     * @param flashcard - Flashcard to add to the DB
     * @throws EmptyQuestionException - Thrown when the Flashcard has an empty question field
     * @throws EmptyAnswerException - Thrown when the Flashcard has an empty answer field
     */
    public Flashcard createNewFlashcard(Flashcard flashcard) throws EmptyNameException,
                                                                    EmptyQuestionException,
                                                                    EmptyAnswerException,
                                                                    DuplicateNameException
    {

        // Validate Flashcard
        validator.validateFlashcard(flashcard);

        // Check for duplicate names
        if (flashcardPersistence.getFlashcardByName(flashcard.getName()) != null)
            throw new DuplicateNameException();

        // Create Flashcard and its answer
        Flashcard created = flashcardPersistence.createFlashcard(flashcard);
        flashcardAnswerManagementService.createFlashcardAnswerFor(flashcard.getAnswer(), flashcard);

        return created;
    }

    /**
     * Updates the existing Flashcard in the database.
     * @param flashcard The Flashcard to update.
     * @throws EmptyQuestionException Thrown when the question is empty.
     * @throws EmptyAnswerException Thrown when the answer is null or empty.
     * @throws EmptyNameException Thrown when the name is empty.
     * @throws FlashcardNotFoundException Thrown when the Flashcard does not exist in the database.
     */
    public boolean updateFlashcard(Flashcard flashcard) throws EmptyNameException,
                                                            EmptyQuestionException,
                                                            EmptyAnswerException,
                                                            FlashcardNotFoundException,
                                                            DuplicateNameException
    {
        boolean updated;

        // Validate Flashcard
        validator.validateFlashcard(flashcard);

        // Retrieve existing Flashcard from data
        Flashcard original = flashcardPersistence.getFlashcardById(flashcard.getId());
        if (original == null)
            throw new FlashcardNotFoundException();
        else
            original.setAnswer(flashcardAnswerManagementService.getFlashcardAnswerFor(original));

        // Check for a different card with same name
        Flashcard duplicate = flashcardPersistence.getFlashcardByName(flashcard.getName());
        if (duplicate != null && duplicate.getId() != flashcard.getId())
            throw new DuplicateNameException();

        // Check if the FlashcardAnswer type has changed & remove old record if so
        boolean answerDeleted = false;
        if (!flashcard.getAnswer().getClass().equals(original.getAnswer().getClass())) {
            flashcardAnswerManagementService.deleteFlashcardAnswerFor(original);
            answerDeleted = true;
        }

        // Update Flashcard and FlashcardAnswer
        updated = flashcardPersistence.updateFlashcard(flashcard);
        if (answerDeleted)
            flashcardAnswerManagementService.createFlashcardAnswerFor(flashcard.getAnswer(), flashcard);
        else
            updated |= flashcardAnswerManagementService.updateFlashcardAnswerFor(flashcard.getAnswer(), flashcard);

        return updated;
    }

    /**
     * Removes a Flashcard from the database.
     * <p>
     *     This function also cascades the delete to the categories the Flashcard
     *     is associated with.
     * </p>
     * @param flashcard The Flashcard to remove.
     * @return True if the removal was a success, false otherwise
     */
    public boolean deleteFlashcard(Flashcard flashcard) {
        boolean deleted;

        // Remove Flashcard, all its links to Categories, and its answer
        deleted = flashcardPersistence.deleteFlashcard(flashcard.getId());
        if (deleted) {
            fcPersistence.removeFlashcard(flashcard.getId());
            flashcardAnswerManagementService.deleteFlashcardAnswerFor(flashcard);
        }

        return deleted;
    }

    //endregion

}

class FlashcardAnswerManagementService {

    //region Members

    private FAPersistenceFactoryMaker faPersistenceFactoryMaker;
    private FlashcardAnswerValidator validator;

    //endregion

    //region Constructors

    /**
     * Initializes a new instance of a FlashcardAnswerManagementService with the specified persistence implementations.
     * @param faTextPersistence The FlashcardTextAnswer persistence.
     * @param faTrueFalsePersistence The FlashcardTFAnswer persistence.
     * @param faMultipleChoicePersistence The FlashcardMCAnswer persistence.
     */
    FlashcardAnswerManagementService(@NonNull FATextPersistence faTextPersistence,
                                     @NonNull FATrueFalsePersistence faTrueFalsePersistence,
                                     @NonNull FAMultipleChoicePersistence faMultipleChoicePersistence,
                                     @NonNull FlashcardAnswerValidator validator)
    {
        this.faPersistenceFactoryMaker = new FAPersistenceFactoryMaker(faTextPersistence, faTrueFalsePersistence, faMultipleChoicePersistence);
        this.validator = validator;
    }

    //endregion

    //region Public Methods

    FlashcardAnswer getFlashcardAnswerFor(Flashcard flashcard) {

        // Get the correct persistence


        FAPersistenceFactory persistenceFactory = faPersistenceFactoryMaker.makeFactory(flashcard.getAnswer());
        FAPersistence faPersistence = persistenceFactory.createFAPersistence();

        return faPersistence.getAnswerFor(flashcard);
    }

    FlashcardAnswer createFlashcardAnswerFor(FlashcardAnswer answer, Flashcard flashcard) throws EmptyAnswerException {

        // Validate FlashcardAnswer
        validator.validateFlashcardAnswer(answer);

        // Get the correct persistence
        FAPersistenceFactory persistenceFactory = faPersistenceFactoryMaker.makeFactory(answer);
        FAPersistence faPersistence = persistenceFactory.createFAPersistence();

        // Create FlashcardAnswer
        return faPersistence.createAnswerFor(answer, flashcard);
    }

    boolean deleteFlashcardAnswerFor(Flashcard flashcard) {

        // Get the correct persistence
        FAPersistenceFactory persistenceFactory = faPersistenceFactoryMaker.makeFactory(flashcard.getAnswer());
        FAPersistence faPersistence = persistenceFactory.createFAPersistence();

        // Delete answer
        return faPersistence.deleteAnswerFor(flashcard);
    }

    boolean updateFlashcardAnswerFor(FlashcardAnswer answer, Flashcard flashcard) throws EmptyAnswerException {

        // Validate FlashcardAnswer
        validator.validateFlashcardAnswer(answer);

        // Get the correct persistence
        FAPersistenceFactory persistenceFactory = faPersistenceFactoryMaker.makeFactory(answer);
        FAPersistence faPersistence = persistenceFactory.createFAPersistence();

        // Update answer
        return faPersistence.updateAnswerFor(answer, flashcard);
    }

    //endregion

}

package com.queens.flashcards.Presentation.Interface;

import android.graphics.Bitmap;

import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;

import java.util.List;

/**
 * Interface for a Flashcard updater.
 */
public interface UpdateFlashcardDataListener {

    /**
     * Updates the question of the Flashcard.
     * @param question The question.
     * @return The updated Flashcard.
     */
    Flashcard updateFlashcardQuestion(String question);

    /**
     * Adds a category association to the Flashcard.
     * @param category The category to associate the Flashcard with.
     * @return The updated Flashcard.
     */
    Flashcard addFlashcardToCategory(Category category);

    /**
     * Removes a category association from the Flashcard.
     * @param category The the category to dissociate from the Flashcard.
     * @return The updated Flashcard.
     */
    Flashcard removeFlashcardFromCategory(Category category);

    /**
     * Updates the name of the Flashcard.
     * @param flashcardName The name.
     * @return The updated Flashcard.
     */
    Flashcard updateFlashcardName(String flashcardName);

    /**
     * Updates the Answer of the Flashcard
     * @param newAnswer - new Answer to be used
     * @return Updated Flashcard
     */
    Flashcard changeFlashcardAnswerTo(FlashcardAnswer newAnswer);

    /**
     * Updates the drawable Bitmap to use for the Flashcard
     * @param image - Image that will be saved to the card
     */
    void updateFlashcardDrawableImage(Bitmap image);
}

package com.queens.flashcards.Presentation.Interface;

/**
 * Interface for classes that want to be notified of Activity events.
 */
public interface ActivityEventListener {

    /**
     * Callback for when the back button is pressed.
     * @return True if the back button press was handled, false otherwise.
     */
    boolean onBackPressed();

}

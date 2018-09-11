package com.queens.flashcards.Presentation.Interface;

import android.support.v4.app.Fragment;

/**
 * Interface for an activity hosting a single Fragment as its contents.
 */
public interface UpdateActivityInterface {

    /**
     * Replaces the fragment currently being displayed with the specified Fragment.
     * @param fragment The Fragment to display.
     */
    void replaceCurrentFragmentWith(Fragment fragment);

    /**
     * Updates the Toolbar title.
     * @param newTitle The new Toolbar title.
     */
    void updateToolbarTitleWith(String newTitle);
}

package com.queens.flashcards;

import android.app.Activity;
import android.content.Context;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Class that stores general utility methods that are useful throughout the application.
 */
public final class Utility {

    /**
     * Hides the soft keyboard input.
     * @param activity The activity currently being displayed.
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View curr = activity.getCurrentFocus();

        if (imm != null)
            imm.hideSoftInputFromWindow(curr != null ? curr.getWindowToken() : null, 0);
    }

}

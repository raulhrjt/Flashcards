package com.queens.flashcards.Presentation;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class Messages {

    //region Constants

    public final static String LOG_TAG = "QUEENS";

    //endregion

    //region Public Methods

    public static void displayMessage(final Activity owner, String message) {
        Toast.makeText(owner, message, Toast.LENGTH_LONG).show();
    }

    public static void printDebug(String message) {
        Log.d(LOG_TAG, message);
    }

    public static void logMessage(String message) {
        Log.d(LOG_TAG, message);
    }

    //endregion

}

package com.queens.flashcards.Presentation;

import android.content.Context;
import android.content.res.AssetManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;


import com.queens.flashcards.Databases;
import com.queens.flashcards.Presentation.Interface.ActivityEventListener;
import com.queens.flashcards.Presentation.Interface.UpdateActivityInterface;
import com.queens.flashcards.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

/**
 * Class for the main Activity.
 */
public class MainActivity extends AppCompatActivity implements UpdateActivityInterface {

    //region Members

    private Toolbar toolbar;

    //endregion

    //region Event Handlers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_activity_frag_holder, new MainMenuFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        // Notify fragments of back press
        boolean handled = false;
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment f : fragments)
            if (f != null && f instanceof ActivityEventListener)
                handled |= ((ActivityEventListener) f).onBackPressed();

        // Handle back press if fragments didn't care about it
        if (!handled) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    //endregion

    //region UpdateActivityInterface Methods

    /** Replaces the current displayed Fragment with passed in fragment, and adds it to the backstack
     *
     * @param fragment - Fragment to display
     */
    @Override
    public void replaceCurrentFragmentWith(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fl_activity_frag_holder, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());

        fragmentTransaction.commit();
    }

    /** Updates the Toolbar title with the new title
     *
     * @param newTitle - Title to display
     */
    @Override
    public void updateToolbarTitleWith(String newTitle) {
        toolbar.setTitle(newTitle);
    }

    //endregion

}

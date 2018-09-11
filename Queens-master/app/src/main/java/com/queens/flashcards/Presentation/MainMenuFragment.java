package com.queens.flashcards.Presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.queens.flashcards.Presentation.Category.CategoryFragment;
import com.queens.flashcards.Presentation.GuessingGame.CategorySelectionFragment;
import com.queens.flashcards.Presentation.Interface.UpdateActivityInterface;
import com.queens.flashcards.R;

/**
 * Class for the Main Menu fragment.
 */
public class MainMenuFragment extends Fragment {

    //region Members

    private Button btnCards;
    private Button btnCategories;
    private Button btnSelect;
    private UpdateActivityInterface activityCallback;

    //endregion

    //region Static Factory Methods

    /**
     * Creates a new instance of a MainMenuFragment fragment.
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();

        return fragment;
    }

    //endregion

    //region Event Handlers

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        activityCallback.updateToolbarTitleWith(getResources().getString(R.string.app_name));

        // Get button references
        btnCards = (Button) view.findViewById(R.id.btn_cards);
        btnCategories = (Button) view.findViewById(R.id.btn_categories);
        btnSelect = (Button) view.findViewById(R.id.btn_select_category);

        // Set click listeners for navigation
        btnCards.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Go to card managing UI
                activityCallback.replaceCurrentFragmentWith(new ManageCardsFragment());
            }
        });

        btnCategories.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Go to category managing UI
                activityCallback.replaceCurrentFragmentWith(new CategoryFragment());
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Go to guessing game category selection UI
                activityCallback.replaceCurrentFragmentWith(new CategorySelectionFragment());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof UpdateActivityInterface) {
            activityCallback = (UpdateActivityInterface) context;
        } else {
            throw new RuntimeException(context.toString() +
                " must implement UpdateActivityInterface.");
        }
    }

    //endregion

}

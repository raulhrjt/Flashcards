package com.queens.flashcards.Presentation.GuessingGame;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.queens.flashcards.Logic.FlashcardManagementService;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Presentation.Interface.GuessingGameSetup;
import com.queens.flashcards.Presentation.Parcelable.CategoryParcelable;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import java.util.List;

public class GuessingGameOptionsFragment extends Fragment {

    //region Adapter

    class FlashcardListAdapter extends BaseAdapter {
        private List<Flashcard> flashcards;
        private Context context;

        public FlashcardListAdapter(@NonNull Context context, List<Flashcard> flashcards) {
            this.flashcards = flashcards;
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if(view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.row_select_flashcard_gg, parent, false);
            }

            final Flashcard currFlashcard = getItem(position);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_flashcard_name);

            //Sets the name of the Flashcard being displayed
            checkBox.setText(currFlashcard.getName());
            checkBox.setChecked(true);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //If the checkbox is now checked, add the Flashcard to the Guessing Game
                    //Else remove the Flashcard from the Guessing Game
                    if(checkBox.isChecked()) {
                       guessingGameCallback.addCardToGame(currFlashcard);
                    } else {
                        guessingGameCallback.removeCardFromGame(currFlashcard);
                    }
                }
            });

            return view;
        }

        @Override
        public int getCount() {
            return this.flashcards.size();
        }

        @Override
        public Flashcard getItem(int position) {
            return this.flashcards.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    //endregion

    //region Members
    private EditText timeLimitInput;
    private Button startGame;
    private ListView cardsListView;
    private List<Flashcard> listOfFlashcards;
    private GuessingGameSetup guessingGameCallback;
    private ToggleButton toggleRepeatGameBtn;
    private EditText maxCardsInput;

    private Switch reverseSwitch;
    private boolean switchValue = false;
    //endregion

    //region Static Factory Methods

    /** Creates a new instance of the Guessing Game Options
     *
     * @param category - Categry to grab Flashcards from
     * @return Instance of the GuessingGameOptionsFragment with the Category attached
     */
    public static GuessingGameOptionsFragment newInstance(Category category) {
        GuessingGameOptionsFragment guessingGameOptionsFragment = new GuessingGameOptionsFragment();

        CategoryParcelable categoryParcelable = new CategoryParcelable(category);
        Bundle bundle = new Bundle();
        bundle.putParcelable(QueensConstants.CATEGORY_KEY, categoryParcelable);
        guessingGameOptionsFragment.setArguments(bundle);

        return guessingGameOptionsFragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guessing_game_options, container, false);
        Bundle bundle = getArguments();
        Category category = null;

        if(bundle != null) {
            CategoryParcelable categoryParcelable = bundle.getParcelable(QueensConstants.CATEGORY_KEY);

            if(categoryParcelable != null) {
                category = categoryParcelable.getCategory();
            }
        }

        timeLimitInput = (EditText) view.findViewById(R.id.et_time_limit_input);
        startGame = (Button) view.findViewById(R.id.btn_start_game);
        cardsListView = (ListView) view.findViewById(R.id.lv_select_gg_cards);
        reverseSwitch = (Switch) view.findViewById(R.id.sc_reverse_qa);
        maxCardsInput = (EditText) view.findViewById(R.id.et_max_cards_input);

        //switchValue = false;
        reverseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    switchValue = true;
                }
                else {
                    switchValue = false;
                }
            }
        });
        toggleRepeatGameBtn = (ToggleButton) view.findViewById(R.id.toggle_repeatGame_Btn);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rawTimeInput = timeLimitInput.getText().toString().trim();
                int timeLimit = 0;

                if(!rawTimeInput.equals("")) {
                    timeLimit = Integer.parseInt(rawTimeInput);
                }

                String rawMaxCardsInput = maxCardsInput.getText().toString().trim();
                int maxCards = listOfFlashcards.size();

                if(!rawMaxCardsInput.equals("")) {
                    maxCards = Integer.parseInt(rawMaxCardsInput);
                }

                guessingGameCallback.setGameTimeLimit(timeLimit);
                guessingGameCallback.reverseQuestion(switchValue);
                guessingGameCallback.setCardLimit(maxCards);
                guessingGameCallback.startGame();
            }
        });

        toggleRepeatGameBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    buttonView.setBackgroundColor(Color.GRAY);
                    guessingGameCallback.setRepeatUntilAllCorrect(true);
                }
                else
                {
                    buttonView.setBackgroundColor(Color.LTGRAY);
                    guessingGameCallback.setRepeatUntilAllCorrect(false);
                }
            }
        });

        FlashcardManagementService flashcardManagementService = new FlashcardManagementService(Services.getFlashcardPersistence(),
                Services.getFcPersistence(),
                Services.getFaTextPersistence(),
                Services.getFaTrueFalsePersistence(),
                Services.getFaMultipleChoicePersistence(),
                Services.getFlashcardValidator(),
                Services.getAnswerValidator());
        listOfFlashcards = flashcardManagementService.getFlashcardsInCategory(category);

        //Add all of the current cards to the game initially
        for(Flashcard flashcard: listOfFlashcards) {
            guessingGameCallback.addCardToGame(flashcard);
        }

        FlashcardListAdapter adapter = new FlashcardListAdapter(getContext(), listOfFlashcards);
        cardsListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof GuessingGameSetup) {
            guessingGameCallback = (GuessingGameSetup) getParentFragment();
        } else {
            throw new RuntimeException(getParentFragment().toString()
                    + " must implement GuessingGameSetup.");
        }
    }

    //endregion

}

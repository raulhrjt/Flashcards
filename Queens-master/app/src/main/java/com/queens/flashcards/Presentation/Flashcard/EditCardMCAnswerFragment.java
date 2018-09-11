package com.queens.flashcards.Presentation.Flashcard;

import java.util.List;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardMCAnswerListener;
import com.queens.flashcards.Presentation.Parcelable.FlashcardParcelable;
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardDataListener;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;

public class EditCardMCAnswerFragment extends Fragment {

    //region Members
    private Flashcard flashcard;
    private UpdateFlashcardMCAnswerListener updateFlashcardMCAnswerListener;
    private EditText correctAnswer;
    private EditText wrongAnswer_1;
    private EditText wrongAnswer_2;
    private EditText wrongAnswer_3;

    //endregion

    //region Static Factory Methods

    /**
     * Initializes a new instance of an EditCardMCAnswerFragment with the specified
     * Flashcard as an argument.
     * @param flashcard The Flashcard to add as an argument.
     * @return A new instance of an EditCardMCAnswerFragment.
     */
    public static EditCardMCAnswerFragment newInstance(Flashcard flashcard) {
        EditCardMCAnswerFragment fragment = new EditCardMCAnswerFragment();

        Bundle args = new Bundle();
        FlashcardParcelable flashcardParcelable = new FlashcardParcelable(flashcard);
        args.putParcelable(QueensConstants.CARD_KEY, flashcardParcelable);
        fragment.setArguments(args);

        return fragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_card_mc_answer, container, false);
        Bundle args = getArguments();

        correctAnswer = (EditText) view.findViewById(R.id.et_mc_answer);
        wrongAnswer_1 = (EditText) view.findViewById(R.id.et_incorrect_answer_1);
        wrongAnswer_2 = (EditText) view.findViewById(R.id.et_incorrect_answer_2);
        wrongAnswer_3 = (EditText) view.findViewById(R.id.et_incorrect_answer_3);

        if(args != null) {
            FlashcardParcelable flashcardParcelable = args.getParcelable(QueensConstants.CARD_KEY);
            flashcard = flashcardParcelable.getFlashcard();

            // Initialize the answer fields
            FlashcardMCAnswer multipleChoiceAnswer = (FlashcardMCAnswer) flashcard.getAnswer();
            List<String> wrongAnswers = multipleChoiceAnswer.getWrongAnswers();
            correctAnswer.setText(multipleChoiceAnswer.getAnswer());
            if(wrongAnswers.size() >= 3) {
                wrongAnswer_1.setText(wrongAnswers.get(0));
                wrongAnswer_2.setText(wrongAnswers.get(1));
                wrongAnswer_3.setText(wrongAnswers.get(2));
            }
        }

        correctAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String correctText = editable.toString();
                updateFlashcardMCAnswerListener.updateRightMCAnswer(correctText);
            }
        });

        wrongAnswer_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                saveWrongAnswers();
            }
        });

        wrongAnswer_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                saveWrongAnswers();
            }
        });

        wrongAnswer_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                saveWrongAnswers();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            updateFlashcardMCAnswerListener = (UpdateFlashcardMCAnswerListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString() + " must implement" + UpdateFlashcardMCAnswerListener.class.getName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        correctAnswer.clearFocus();
        wrongAnswer_1.clearFocus();
        wrongAnswer_2.clearFocus();
        wrongAnswer_3.clearFocus();
    }

    //endregion

    //region helper functions

    /** Grabs the 3 wrong answers, and updates them in the Flashcard temporarily*/
    private void saveWrongAnswers() {
        List<String> wrongAnswers = new ArrayList<>();
        wrongAnswers.add(wrongAnswer_1.getText().toString());
        wrongAnswers.add(wrongAnswer_2.getText().toString());
        wrongAnswers.add(wrongAnswer_3.getText().toString());

        updateFlashcardMCAnswerListener.updateWrongMCAnswers(wrongAnswers);
    }

    //endregion
}

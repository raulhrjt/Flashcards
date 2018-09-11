package com.queens.flashcards.Presentation.Flashcard;

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
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardTextAnswerListener;
import com.queens.flashcards.Presentation.Parcelable.FlashcardParcelable;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;

/**
 * Class for the Answer tab of the Flashcard editing interface.
 */
public class EditCardTextAnswerFragment extends Fragment {

    //region Members

    private EditText answer;
    private Flashcard flashcard;
    private UpdateFlashcardTextAnswerListener updateFlashcardTextAnswerListener;

    //endregion

    //region Static Factory Methods

    /**
     * Initializes a new instance of an EditCardTextAnswerFragment with the specified
     * Flashcard as an argument.
     * @param flashcard The Flashcard to add as an argument.
     * @return A new instance of an EditCardTextAnswerFragment.
     */
    public static EditCardTextAnswerFragment newInstance(Flashcard flashcard) {
        EditCardTextAnswerFragment fragment = new EditCardTextAnswerFragment();

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_card_text_answer, container, false);

        // Get view reference
        answer = (EditText) view.findViewById(R.id.et_edit_card_text_answer);

        // Retrieve arguments
        Bundle args = getArguments();
        if(args != null) {
            FlashcardParcelable flashcardParcelable = args.getParcelable(QueensConstants.CARD_KEY);
            flashcard = flashcardParcelable.getFlashcard();
            FlashcardAnswer answer = flashcard.getAnswer();

            this.answer.setText(answer.getAnswer());
        }

        // Set listener to update Flashcard
        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String answerText = editable.toString();
                flashcard = updateFlashcardTextAnswerListener.updateFlashcardAnswerText(answerText);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            updateFlashcardTextAnswerListener = (UpdateFlashcardTextAnswerListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString() + " must implement " + UpdateFlashcardTextAnswerListener.class.getName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        answer.clearFocus();
    }

    //endregion

}

package com.queens.flashcards.Presentation.Flashcard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardTFAnswerListener;
import com.queens.flashcards.Presentation.Parcelable.FlashcardParcelable;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardDataListener;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;

public class EditCardTFAnswerFragment extends Fragment {

    //region Members

    private Flashcard flashcard;
    private UpdateFlashcardTFAnswerListener updateFlashcardTFAnswerListener;
    private RadioGroup answerCorrectGroup;
    private RadioButton trueButton;
    private RadioButton falseButton;

    //endregion

    //region Static Factory Methods

    /**
     * Initializes a new instance of an EditCardTFAnswerFragment with the specified
     * Flashcard as an argument.
     * @param flashcard The Flashcard to add as an argument.
     * @return A new instance of an EditCardTFAnswerFragment.
     */
    public static EditCardTFAnswerFragment newInstance(Flashcard flashcard) {
        EditCardTFAnswerFragment fragment = new EditCardTFAnswerFragment();

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
        View view = inflater.inflate(R.layout.fragment_edit_card_tf_answer, container, false);

        answerCorrectGroup = (RadioGroup) view.findViewById(R.id.rg_answer_correct);
        falseButton = (RadioButton) view.findViewById(R.id.rb_answer_false);
        trueButton = (RadioButton) view.findViewById(R.id.rb_answer_true);

        // Retrieve arguments
        Bundle args = getArguments();
        if(args != null) {
            FlashcardParcelable flashcardParcelable = args.getParcelable(QueensConstants.CARD_KEY);
            flashcard = flashcardParcelable.getFlashcard();

            // Initialize the true/false answer
            FlashcardTFAnswer tfAnswer = (FlashcardTFAnswer) flashcard.getAnswer();
            if(tfAnswer.getAnswerIsTrue()) {
                trueButton.setChecked(true);
            } else {
                falseButton.setChecked(true);
            }
        }

        answerCorrectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkID) {
                switch(checkID) {
                    case R.id.rb_answer_true:
                        updateFlashcardTFAnswerListener.updateFlashcardTrueFalseAnswer(true);
                        break;
                    case R.id.rb_answer_false:
                        updateFlashcardTFAnswerListener.updateFlashcardTrueFalseAnswer(false);
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            updateFlashcardTFAnswerListener = (UpdateFlashcardTFAnswerListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString() + " must implement " + UpdateFlashcardTFAnswerListener.class.getName());
        }
    }

    //endregion
}

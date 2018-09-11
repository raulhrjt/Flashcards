package com.queens.flashcards.Presentation.GuessingGame;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.Interface.UpdateGuessingGame;
import com.queens.flashcards.Presentation.Parcelable.FlashcardParcelable;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;

public class NiceAnswerFragment extends Fragment {

    //region Members

    private TextView answerResultDisplay;
    private TextView textAnswerSavedAs;
    private Button textAnswerWasCorrect;
    private Button textAnswerWasIncorrect;

    private UpdateGuessingGame guessingGameCallback;
    private Flashcard currCard;
    private boolean reverseQuestionAnswer = false;

    //endregion

    //region Static Factory Methods

    /** Creates a new instance of the NiceAnswer Fragment that lets user decide if they answers right or wrong.
     *
     * @param flashcard - Flashcard containing the Answer
     * @param reverseQuestionAnswer - boolean if to reverse question and answer
     * @return NiceAnswerFragment with the above parameters attached
     */
    public static NiceAnswerFragment newInstance(Flashcard flashcard, boolean reverseQuestionAnswer) {
        NiceAnswerFragment answerFragment = new NiceAnswerFragment();
        FlashcardParcelable flashcardParcelable = new FlashcardParcelable(flashcard);

        Bundle bundle = new Bundle();
        bundle.putParcelable(QueensConstants.CARD_KEY, flashcardParcelable);
        bundle.putBoolean(QueensConstants.REVERSE_QUESTION_KEY, reverseQuestionAnswer);
        answerFragment.setArguments(bundle);

        return answerFragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nice_answer, container, false);

        // Get args
        Bundle bundle = getArguments();
        FlashcardParcelable flashcardParcelable = bundle.getParcelable(QueensConstants.CARD_KEY);
        currCard = flashcardParcelable.getFlashcard();
        reverseQuestionAnswer = bundle.getBoolean(QueensConstants.REVERSE_QUESTION_KEY);

        // Get view references
        answerResultDisplay = (TextView) view.findViewById(R.id.tv_correct_answer);
        textAnswerSavedAs = (TextView) view.findViewById(R.id.tv_your_answer_saved);
        textAnswerWasCorrect = (Button) view.findViewById(R.id.btn_was_correct);
        textAnswerWasIncorrect = (Button) view.findViewById(R.id.btn_was_incorrect);

        // Initialize answer selection
        FlashcardTextAnswer answer = (FlashcardTextAnswer) currCard.getAnswer();
        String displayText = reverseQuestionAnswer ? currCard.getQuestion() : answer.getAnswer();
        answerResultDisplay.setText(displayText);

        // Set handlers for choosing right or wrong
        textAnswerWasCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessingGameCallback.userSelectedAnswerCorrectly(true);
                guessingGameCallback.submitAnswer(currCard.getAnswer().getAnswer(), currCard);
                setAnswerButtonsEnabled(false);
                textAnswerSavedAs.setText(R.string.marked_correct);
            }
        });
        textAnswerWasIncorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessingGameCallback.userSelectedAnswerCorrectly(false);
                guessingGameCallback.submitAnswer(currCard.getAnswer().getAnswer() + "_WRONG", currCard);
                setAnswerButtonsEnabled(false);
                textAnswerSavedAs.setText(R.string.marked_incorrect);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof UpdateGuessingGame) {
            guessingGameCallback = (UpdateGuessingGame) getParentFragment();
        } else {
            throw new RuntimeException(getParentFragment().toString()
                    + " must implement UpdateGuessingGame.");
        }
    }

    //endregion

    //region Helper Methods

    private void setAnswerButtonsEnabled(boolean enabled) {
        textAnswerWasCorrect.setEnabled(enabled);
        textAnswerWasIncorrect.setEnabled(enabled);
    }

    //endregion

}

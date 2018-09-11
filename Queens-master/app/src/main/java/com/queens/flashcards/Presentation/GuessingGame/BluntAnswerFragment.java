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

public class BluntAnswerFragment extends Fragment {

    //region Members

    private TextView answerDisplay;
    private TextView guessedAnswerDisplay;
    private TextView answerResultDisplay;

    private UpdateGuessingGame guessingGameCallback;
    private Flashcard currCard;
    private boolean userWasCorrect = false;
    private String guessedAnswer;

    //endregion

    //region Static Factory Methods

    /** Creates a new instance of the BluntAnswer Fragment that tells the user if they got the answer right or wrong.
     *
     * @param flashcard - Flashcard containing the Answer
     * @param correct - If the answer the user gave was correct/wrong
     * @param guessedAnswer - Answer that the user guessed
     * @return AnswerFragment with the above parameters attached
     */
    public static BluntAnswerFragment newInstance(Flashcard flashcard, boolean correct, String guessedAnswer) {
        BluntAnswerFragment answerFragment = new BluntAnswerFragment();
        FlashcardParcelable flashcardParcelable = new FlashcardParcelable(flashcard);

        Bundle bundle = new Bundle();
        bundle.putParcelable(QueensConstants.CARD_KEY, flashcardParcelable);
        bundle.putBoolean(QueensConstants.USER_CORRECT_KEY, correct);
        bundle.putString(QueensConstants.USER_GUESS_KEY, guessedAnswer);
        answerFragment.setArguments(bundle);

        return answerFragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blunt_answer, container, false);

        // Get args
        Bundle bundle = getArguments();
        FlashcardParcelable flashcardParcelable = bundle.getParcelable(QueensConstants.CARD_KEY);
        currCard = flashcardParcelable.getFlashcard();
        userWasCorrect = bundle.getBoolean(QueensConstants.USER_CORRECT_KEY);
        guessedAnswer = bundle.getString(QueensConstants.USER_GUESS_KEY);

        // Get view references
        answerDisplay = (TextView) view.findViewById(R.id.tv_correct_answer);
        guessedAnswerDisplay = (TextView) view.findViewById(R.id.tv_guessed_answer);
        answerResultDisplay = (TextView) view.findViewById(R.id.tv_your_guess_was);

        // Initialize answer display
        FlashcardAnswer answer = currCard.getAnswer();
        guessedAnswerDisplay.setText(guessedAnswer);
        answerDisplay.setText(answer.getAnswer());
        String displayText = userWasCorrect ? getResources().getString(R.string.you_were_correct)
                                            : getResources().getString(R.string.you_were_incorrect);
        answerResultDisplay.setText(displayText);

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

}

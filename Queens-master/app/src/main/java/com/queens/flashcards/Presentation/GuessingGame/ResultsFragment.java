package com.queens.flashcards.Presentation.GuessingGame;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.queens.flashcards.Presentation.Interface.UpdateGuessingGame;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;

public class ResultsFragment extends Fragment {
    //region Members
    private TextView numQuestionsDisplay;
    private TextView numCorrectDisplay;
    private TextView numIncorrectDisplay;
    private UpdateGuessingGame guessingGameCallback;

    //endregion

    //region Static Factory Methods

    /** Creates an instance of the ResultsFragment
     *
     * @param numQuestions - Number of questions total
     * @param numCorrect - Number of questions correct
     * @param numIncorrect - Number of questions incorrect
     * @return ResultsFragment with the above parameters attached
     */
    public static ResultsFragment newInstance(int numQuestions, int numCorrect, int numIncorrect) {
        ResultsFragment resultsFragment = new ResultsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(QueensConstants.NUM_CARDS_KEY, numQuestions);
        bundle.putInt(QueensConstants.NUM_CORRECT_KEY, numCorrect);
        bundle.putInt(QueensConstants.NUM_INCORRECT_KEY, numIncorrect);
        resultsFragment.setArguments(bundle);

        return resultsFragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        Bundle args = getArguments();
        int numQuestions = 0;
        int numCorrect = 0;
        int numIncorrect = 0;

        if(args != null) {
            numQuestions = args.getInt(QueensConstants.NUM_CARDS_KEY);
            numCorrect = args.getInt(QueensConstants.NUM_CORRECT_KEY);
            numIncorrect = args.getInt(QueensConstants.NUM_INCORRECT_KEY);
        }

        numQuestionsDisplay = (TextView) view.findViewById(R.id.tv_num_questions);
        numCorrectDisplay = (TextView) view.findViewById(R.id.tv_num_correct);
        numIncorrectDisplay = (TextView) view.findViewById(R.id.tv_num_incorrect);

        int numQuestionAnswered = numCorrect+numIncorrect;

        numQuestionsDisplay.setText(numQuestionAnswered + "");
        numCorrectDisplay.setText(numCorrect + "");
        numIncorrectDisplay.setText(numIncorrect + "");

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

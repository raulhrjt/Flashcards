package com.queens.flashcards.Presentation.GuessingGame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.queens.flashcards.Logic.Validation.FlashcardAnswerValidator;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Presentation.GuessingGame.Factory.AnswerFragmentFactory;
import com.queens.flashcards.Presentation.GuessingGame.Factory.QuestionFragmentFactory;
import com.queens.flashcards.Presentation.Parcelable.CategoryParcelable;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.Interface.GuessingGameSetup;
import com.queens.flashcards.Presentation.Interface.UpdateGuessingGame;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import java.util.ArrayList;
import java.util.List;

public class GuessingGameFragment extends Fragment implements UpdateGuessingGame, GuessingGameSetup {

    //region Members

    private Button exitGame;
    private Button nextCard;
    private ImageButton submitAnswer;
    private TextView cardPositionDisplay;
    private TextView remainingTimeTitle;
    private TextView remainingTimeDisplay;

    private int numCorrect;
    private int numIncorrect;
    private int maxCards;
    private List<Flashcard> cards;
    private int currCardNumber;
    private int numCardsInGame;
    private Category category;
    private String userSelectedAnswer;
    private boolean userSelectedCorrect;
    private FlashcardAnswerValidator validator = Services.getAnswerValidator();
    private CountDownTimer countDownTimer;
    private long countdownTime;
    private boolean repeatUntilAllCorrect;
    private boolean reverseQuestionAnswer = false;

    //endregion

    public static GuessingGameFragment newInstance(Category category) {
        GuessingGameFragment guessingGameFragment = new GuessingGameFragment();

        CategoryParcelable categoryParcelable = new CategoryParcelable(category);
        Bundle bundle = new Bundle();
        bundle.putParcelable(QueensConstants.CATEGORY_KEY, categoryParcelable);
        guessingGameFragment.setArguments(bundle);

        return guessingGameFragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guessing_game, container, false);

        Bundle bundle = getArguments();

        if(bundle != null) {
            CategoryParcelable categoryParcelable = bundle.getParcelable(QueensConstants.CATEGORY_KEY);

            if(categoryParcelable != null) {
                category = categoryParcelable.getCategory();
            }
        }

        numCorrect = 0;
        numIncorrect = 0;
        currCardNumber = 0;
        cards = new ArrayList<>();

        exitGame = (Button) view.findViewById(R.id.btn_exit_game);
        nextCard = (Button) view.findViewById(R.id.btn_next_card);
        submitAnswer = (ImageButton) view.findViewById(R.id.btn_okay);
        cardPositionDisplay = (TextView) view.findViewById(R.id.tv_card_number);
        remainingTimeTitle = (TextView) view.findViewById(R.id.tv_remaining_time_title);
        remainingTimeDisplay = (TextView) view.findViewById(R.id.tv_remaining_time);

        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitGame();
            }
        });

        nextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToNextCard();
            }
        });

        submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToAnswer();
            }
        });

        GuessingGameOptionsFragment guessingGameOptionsFragment = GuessingGameOptionsFragment.newInstance(category);
        FragmentManager fragmentManager = getChildFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_gg_frag_holder, guessingGameOptionsFragment);
        fragmentTransaction.commit();

        return view;
    }

    //endregion

    //region Helper Methods

    /** Switches the current Child Fragment without adding it to the backstack
     *
     * @param fragment - Child Fragment to be displayed
     */
    private void switchChildFragmentTo(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_gg_frag_holder, fragment);
        fragmentTransaction.commit();
    }

    /** Switches to the next Flashcard in the List
     *  Increases score if the Flashcard had a Text answer
     *  If there are no Flashcards remaining, it'll switch to the results fragment
     */
    private void switchToNextCard() {
        FlashcardAnswer currAnswer = cards.get(currCardNumber).getAnswer();

        currCardNumber++;

        if(currCardNumber < maxCards && currCardNumber < cards.size()) {
            updateCardNumDisplay();
            switchToQuestion();
        } else {
            if(countDownTimer != null) {
                countDownTimer.cancel();
                displayResults();
            }if(repeatUntilAllCorrect && numIncorrect > 0){

                //notify the user that the game is restarting
                Snackbar snackbar = Snackbar.make(submitAnswer, "User did not answer all questions correctly. Repeating the game.", Snackbar.LENGTH_LONG);
                snackbar.show();

                //resetting our stats back to 0
                numCorrect = 0;
                numIncorrect = 0;
                currCardNumber = 0;

                //then restarting the game
                this.startGame();
            }else{
                displayResults();
            }
        }
    }

    /** Displays the current card number out of the total number of cards */
    private void updateCardNumDisplay() {
        String cardPositionText = (currCardNumber + 1) + "/" + numCardsInGame;
        cardPositionDisplay.setText(cardPositionText);
    }

    /** Switches the current Flashcard to one that has a selectable answers based Answer */
    private void switchToQuestion() {
        showButtonsForQuestion();

        userSelectedAnswer = "";
        userSelectedCorrect = false;

        Fragment questionFragment = QuestionFragmentFactory.createQuestionFragmentFor(cards.get(currCardNumber), reverseQuestionAnswer);
        switchChildFragmentTo(questionFragment);
    }

    /** Switches the current Child Fragment to display the Flashcard Answer */
    private void switchToAnswer() {
        showButtonsForAnswer();

        // Go to the answer fragment
        Fragment answerFragment = AnswerFragmentFactory.createAnswerFragmentFor(cards.get(currCardNumber), reverseQuestionAnswer, userSelectedCorrect, userSelectedAnswer);
        switchChildFragmentTo(answerFragment);
    }

    /** Displays buttons to indicate that the next card will be displayed */
    private void showButtonsForAnswer() {
        nextCard.setVisibility(View.VISIBLE);
        submitAnswer.setVisibility(View.GONE);
    }

    /** Displays buttons to indicate that the answer for the current card will be displayed */
    private void showButtonsForQuestion() {
        nextCard.setVisibility(View.GONE);
        submitAnswer.setVisibility(View.VISIBLE);
    }

    /** Hides all of the menu buttons */
    private void showButtonsForResult() {
        nextCard.setVisibility(View.GONE);
        submitAnswer.setVisibility(View.GONE);
    }

    /** Exits the current guessing game, back to the options
     *  Allows a way to skip the remaining questions
     */
    private void exitGame() {
        //since we're not adding fragments to the backstack, by a user exiting we will just go back to
        //the fragment before this current one (or should..)
        getActivity().onBackPressed();
    }

    /** Switches to the Result Fragment to display how the user did */
    private void displayResults() {
        cardPositionDisplay.setText("");

        ResultsFragment resultsFragment = ResultsFragment.newInstance(cards.size(), numCorrect, numIncorrect);
        switchChildFragmentTo(resultsFragment);

        showButtonsForResult();
    }

    //endregion

    //region interface functions

    /** Sets the answer the user has entered
     *
     * @param answer - Answer to be set
     */
    @Override
    public void userSelectedAnswer(String answer) {
        this.userSelectedAnswer = answer;
    }

    /** Updates if the user was correct
     *
     * @param correct - True/False if they are correct
     */
    @Override
    public void userSelectedAnswerCorrectly(boolean correct) {
        this.userSelectedCorrect = correct;
    }

    @Override
    public void submitAnswer(String answer, Flashcard flashcard) {
        boolean correct = validator.validateGuessedAnswer(flashcard.getAnswer(), answer);
        if (correct) {
            this.numCorrect++;
            this.userSelectedCorrect = true;
        } else {
            this.numIncorrect++;
            this.userSelectedCorrect = false;
        }
    }

    @Override
    public String getUserSelectedAnswer() {
        return userSelectedAnswer;
    }

    /** Starts the Guessing Game, with the specified options and starts the CountDownTimer if its time is > 0 */
    @Override
    public void startGame() {
        Fragment fragment;

        if(cards.size() > 0 ) {
            numCardsInGame = Math.min(cards.size(), maxCards);
            updateCardNumDisplay();
            showButtonsForQuestion();
            fragment = QuestionFragmentFactory.createQuestionFragmentFor(cards.get(currCardNumber), reverseQuestionAnswer);

            if(countDownTimer != null && countdownTime > 0) {
                countDownTimer.start();
                remainingTimeTitle.setVisibility(View.VISIBLE);
                remainingTimeDisplay.setText(String.valueOf(countdownTime));
            }
        } else {
            submitAnswer.setVisibility(View.GONE);
            fragment = new EmptyCategoryFragment();
        }

        switchChildFragmentTo(fragment);
    }

    /** Sets the time limit on the game
     *
     * @param timeLimit - Time limit in seconds
     */
    @Override
    public void setGameTimeLimit(long timeLimit) {
        this.countdownTime = timeLimit;

        countDownTimer = new CountDownTimer(timeLimit * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long timeInSeconds = millisUntilFinished / 1000;
                remainingTimeDisplay.setText(String.valueOf(timeInSeconds));
            }

            @Override
            public void onFinish() {
                remainingTimeDisplay.setText("0");
                numIncorrect += numCardsInGame - currCardNumber;
                displayResults();
            }
        };
    }
    /** Sets the time limit on the game
     *
     * @param repeatGame - indicates if the user wants to keep repeating the game untill
     *                   all answers are correct
     */
    @Override
    public void setRepeatUntilAllCorrect(boolean repeatGame) {
        this.repeatUntilAllCorrect = repeatGame;
    }

    /** Adds the passed Flashcard to the Guessing Game
     *
     * @param flashcard - Flashcard to add to the game
     */
    @Override
    public void addCardToGame(Flashcard flashcard) {
        if(flashcard != null && !cards.contains(flashcard)) {
            cards.add(flashcard);
        }
    }

    /** Removes the passed Flashcard from the Guessing Game
     *
     * @param flashcard - Flashcard to remove from the game
     */
    @Override
    public void removeCardFromGame(Flashcard flashcard) {
        cards.remove(flashcard);
    }

    /** Reverses question and answer for Text FlashCard
     *
     * @param reverse - boolean if to reverse the question and answer
     */
    @Override
    public void reverseQuestion(boolean reverse) {
        reverseQuestionAnswer = reverse;
    }

    /** Sets a limit on the number of cards used in the game
     *
     * @param cardLimit - Max number of cards
     */
    @Override
    public void setCardLimit(int cardLimit) {
        this.maxCards = cardLimit;
    }

    //endregion
}

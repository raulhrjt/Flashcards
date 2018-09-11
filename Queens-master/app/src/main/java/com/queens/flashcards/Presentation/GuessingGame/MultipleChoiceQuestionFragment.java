package com.queens.flashcards.Presentation.GuessingGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.queens.flashcards.Logic.ImageService;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.Interface.UpdateGuessingGame;
import com.queens.flashcards.Presentation.Parcelable.FlashcardParcelable;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import java.util.List;


public class MultipleChoiceQuestionFragment extends Fragment {

    //region Members

    private TextView questionTextDisplay;
    private RadioGroup multipleChoiceGroup;
    private TextView multipleChoiceAnswer1;
    private TextView multipleChoiceAnswer2;
    private TextView multipleChoiceAnswer3;
    private TextView multipleChoiceAnswer4;
    private ImageView questionImageDisplay;

    private UpdateGuessingGame guessingGameCallback;
    private Flashcard currCard;

    //endregion

    //region Static Factory Methods

    /** Creates an instance of the MultipleChoiceQuestionFragment
     *
     * @param flashcard - Flashcard to use the question from
     * @return MultipleChoiceQuestionFragment with the Flashcard attached to it
     */
    public static MultipleChoiceQuestionFragment newInstance(Flashcard flashcard) {
        MultipleChoiceQuestionFragment questionFragment = new MultipleChoiceQuestionFragment();
        FlashcardParcelable flashcardParcelable = new FlashcardParcelable(flashcard);

        Bundle bundle = new Bundle();
        bundle.putParcelable(QueensConstants.CARD_KEY, flashcardParcelable);
        questionFragment.setArguments(bundle);

        return questionFragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_choice_question, container, false);
        Bundle bundle = getArguments();

        if(bundle != null) {
            FlashcardParcelable flashcardParcelable = bundle.getParcelable(QueensConstants.CARD_KEY);

            if(flashcardParcelable != null) {
                currCard = flashcardParcelable.getFlashcard();
            }
        }

        questionTextDisplay = (TextView) view.findViewById(R.id.tv_question_text);
        questionImageDisplay = (ImageView) view.findViewById(R.id.iv_question_image_display);
        multipleChoiceGroup = (RadioGroup) view.findViewById(R.id.rg_multiple_choice);
        multipleChoiceAnswer1 = (TextView) view.findViewById(R.id.rb_mc_option1);
        multipleChoiceAnswer2 = (TextView) view.findViewById(R.id.rb_mc_option2);
        multipleChoiceAnswer3 = (TextView) view.findViewById(R.id.rb_mc_option3);
        multipleChoiceAnswer4 = (TextView) view.findViewById(R.id.rb_mc_option4);

        // Initialize question and image
        questionTextDisplay.setText(currCard.getQuestion());
        if(!currCard.getImageLocation().equals("")){
            ImageService imageService = new ImageService(Services.getImagePersistence());
            byte[] imageAsBytes = imageService.getImage(currCard.getImageLocation());
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

            if(bitmap != null) {
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);

                questionImageDisplay.setBackground(drawable);
                questionImageDisplay.setVisibility(View.VISIBLE);
            }
        }

        // Initialize multiple choice answers
        FlashcardMCAnswer currentMCAnswer = (FlashcardMCAnswer) currCard.getAnswer();
        List<String> wrongAnswers = currentMCAnswer.getWrongAnswers();

        multipleChoiceAnswer1.setText(wrongAnswers.get(0));
        multipleChoiceAnswer2.setText(wrongAnswers.get(1));
        multipleChoiceAnswer3.setText(wrongAnswers.get(2));
        multipleChoiceAnswer4.setText(currentMCAnswer.getAnswer());
        multipleChoiceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                if(checkedID == R.id.rb_mc_option1){
                    guessingGameCallback.userSelectedAnswer((multipleChoiceAnswer1.getText()).toString());
                    guessingGameCallback.submitAnswer((multipleChoiceAnswer1.getText()).toString(), currCard);
                } else if(checkedID == R.id.rb_mc_option2){
                    guessingGameCallback.userSelectedAnswer((multipleChoiceAnswer2.getText()).toString());
                    guessingGameCallback.submitAnswer((multipleChoiceAnswer2.getText()).toString(), currCard);
                } else if(checkedID == R.id.rb_mc_option3){
                    guessingGameCallback.userSelectedAnswer((multipleChoiceAnswer3.getText()).toString());
                    guessingGameCallback.submitAnswer((multipleChoiceAnswer3.getText()).toString(), currCard);
                } else if(checkedID == R.id.rb_mc_option4) {
                    guessingGameCallback.userSelectedAnswer((multipleChoiceAnswer4.getText()).toString());
                    guessingGameCallback.submitAnswer((multipleChoiceAnswer4.getText()).toString(), currCard);
                }
                setOptionsEnabled(false);
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

    private void setOptionsEnabled(boolean enabled) {
        multipleChoiceGroup.setEnabled(enabled);
    }

}

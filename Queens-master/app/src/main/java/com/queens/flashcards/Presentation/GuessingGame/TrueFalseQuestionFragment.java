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

public class TrueFalseQuestionFragment extends Fragment {

    //region Members

    private TextView questionTextDisplay;
    private RadioGroup trueFalseGroup;
    private ImageView questionImageDisplay;

    private UpdateGuessingGame guessingGameCallback;
    private Flashcard currCard;

    //endregion

    //region Static Factory Methods

    /** Creates an instance of the TrueFalseQuestionFragment
     *
     * @param flashcard - Flashcard to use the question from
     * @return TrueFalseQuestionFragment with the Flashcard attached to it
     */
    public static TrueFalseQuestionFragment newInstance(Flashcard flashcard) {
        TrueFalseQuestionFragment questionFragment = new TrueFalseQuestionFragment();
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
        View view = inflater.inflate(R.layout.fragment_true_false_question, container, false);

        // Get args
        Bundle bundle = getArguments();
        FlashcardParcelable flashcardParcelable = bundle.getParcelable(QueensConstants.CARD_KEY);
        currCard = flashcardParcelable.getFlashcard();

        // Get view references
        questionTextDisplay = (TextView) view.findViewById(R.id.tv_question_text);
        trueFalseGroup = (RadioGroup) view.findViewById(R.id.rg_true_false);
        questionImageDisplay = (ImageView) view.findViewById(R.id.iv_question_image_display);

        // Initialize question and image display
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

        // Initialize true/false answer listener
        trueFalseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                switch(checkedID) {
                    case R.id.rb_gg_answer_true:
                        guessingGameCallback.userSelectedAnswer("true");
                        guessingGameCallback.submitAnswer(String.valueOf(true), currCard);
                        setOptionsEnabled(false);
                        break;
                    case R.id.rb_gg_answer_false:
                        guessingGameCallback.userSelectedAnswer("false");
                        guessingGameCallback.submitAnswer(String.valueOf(false), currCard);
                        setOptionsEnabled(false);
                        break;
                }
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
        trueFalseGroup.setEnabled(enabled);
    }

}

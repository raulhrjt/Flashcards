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

public class TextQuestionFragment extends Fragment {

    //region Members

    private TextView questionTextDisplay;
    private ImageView questionImageDisplay;

    private UpdateGuessingGame guessingGameCallback;
    private Flashcard currCard;
    private boolean reverseQuestionAnswer = false;

    //endregion

    //region Static Factory Methods

    /** Creates an instance of the TextQuestionFragment
     *
     * @param flashcard - Flashcard to use the question from
     * @param reverseQuestionAnswer - boolean if to reverseQuestionAnswer question and answer
     * @return TextQuestionFragment with the Flashcard attached to it
     */
    public static TextQuestionFragment newInstance(Flashcard flashcard, boolean reverseQuestionAnswer) {
        TextQuestionFragment questionFragment = new TextQuestionFragment();
        FlashcardParcelable flashcardParcelable = new FlashcardParcelable(flashcard);

        Bundle bundle = new Bundle();
        bundle.putParcelable(QueensConstants.CARD_KEY, flashcardParcelable);
        bundle.putBoolean(QueensConstants.REVERSE_QUESTION_KEY, reverseQuestionAnswer);
        questionFragment.setArguments(bundle);

        return questionFragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_question, container, false);

        // Get args
        Bundle bundle = getArguments();
        FlashcardParcelable flashcardParcelable = bundle.getParcelable(QueensConstants.CARD_KEY);
        reverseQuestionAnswer = bundle.getBoolean(QueensConstants.REVERSE_QUESTION_KEY);
        currCard = flashcardParcelable.getFlashcard();

        // Get view references
        questionTextDisplay = (TextView) view.findViewById(R.id.tv_question_text);
        questionImageDisplay = (ImageView) view.findViewById(R.id.iv_question_image_display);

        // Initialize image and question (or answer for reversed cards)
        FlashcardTextAnswer textAnswer = (FlashcardTextAnswer) currCard.getAnswer();
        String displayText = reverseQuestionAnswer ? textAnswer.getAnswer() : currCard.getQuestion();
        questionTextDisplay.setText(displayText);
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

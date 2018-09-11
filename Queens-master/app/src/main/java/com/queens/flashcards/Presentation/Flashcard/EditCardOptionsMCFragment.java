package com.queens.flashcards.Presentation.Flashcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Presentation.Parcelable.FlashcardParcelable;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;

public class EditCardOptionsMCFragment extends EditCardOptionsFragment {
    //region Static Factory Methods

    /**
     * Initializes a new instance of an EditCardOptionsMCFragment with the
     * specified Flashcard as an argument.
     * @param flashcard The Flashcard to add as an argument.
     * @return A new instance of an EditCardOptionsMCFragment.
     */
    public static EditCardOptionsMCFragment newInstance(Flashcard flashcard) {
        EditCardOptionsMCFragment fragment = new EditCardOptionsMCFragment();

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
        View view = super.onCreateView(inflater, container, savedInstanceState);

        RadioButton multipleChoiceOption = (RadioButton) view.findViewById(R.id.rb_answer_type_mc);
        multipleChoiceOption.setChecked(true);

        return view;
    }

    //endregion
}

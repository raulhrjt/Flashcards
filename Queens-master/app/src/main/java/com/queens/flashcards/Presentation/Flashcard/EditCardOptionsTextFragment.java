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

public class EditCardOptionsTextFragment extends EditCardOptionsFragment {
    //region Static Factory Methods

    /**
     * Initializes a new instance of an EditCardOptionsTextFragment with the
     * specified Flashcard as an argument.
     * @param flashcard The Flashcard to add as an argument.
     * @return A new instance of an EditCardOptionsTextFragment.
     */
    public static EditCardOptionsTextFragment newInstance(Flashcard flashcard) {
        EditCardOptionsTextFragment fragment = new EditCardOptionsTextFragment();

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

        RadioButton textOption = (RadioButton) view.findViewById(R.id.rb_answer_type_text);
        textOption.setChecked(true);

        return view;
    }

    //endregion
}

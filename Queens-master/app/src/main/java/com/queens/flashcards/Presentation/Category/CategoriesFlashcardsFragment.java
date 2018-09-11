package com.queens.flashcards.Presentation.Category;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.queens.flashcards.Logic.CategoryManagementService;
import com.queens.flashcards.Logic.Exception.CategoryNotFoundException;
import com.queens.flashcards.Logic.Exception.DuplicateNameException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Logic.FlashcardManagementService;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Presentation.Parcelable.CategoryParcelable;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Presentation.Flashcard.EditCardFragment;
import com.queens.flashcards.Presentation.Interface.UpdateActivityInterface;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import java.util.List;

/**
 * Class for the Fragment where the collection of Flashcards associated with a Category can be edited.
 */
public class CategoriesFlashcardsFragment extends Fragment {

    //region Adapter

    class FlashcardListAdapter extends BaseAdapter {
        private Context context;
        private List<Flashcard> listOfFlashcards;

        public FlashcardListAdapter(Context context, List<Flashcard> listOfFlashcards) {
            this.context = context;
            this.listOfFlashcards = listOfFlashcards;
        }

        @Override
        public int getCount() {
            return listOfFlashcards.size();
        }

        @Override
        public Flashcard getItem(int pos) {
            return listOfFlashcards.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            View view = convertView;

            if(view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.row_flashcard_display, parent, false);
            }

            TextView hiddenID = (TextView) view.findViewById(R.id.tv_hidden_flashcard_id);
            TextView flashcardNameTV = (TextView) view.findViewById(R.id.tv_flashcard_name);
            ImageButton removeFlashcard = (ImageButton) view.findViewById(R.id.ib_remove_flashcard);
            ImageButton editFlashcard = (ImageButton) view.findViewById(R.id.ib_edit_flashcard);

            final Flashcard currFlashcard = getItem(pos);

            hiddenID.setText(currFlashcard.getId() + "");
            flashcardNameTV.setText(currFlashcard.getName());

            removeFlashcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currCategory.remove(currFlashcard.getId());
                    //currFlashcard.removeFromCategory(currCategory.getId());

                    try {
                        //flashcardManagementService.updateFlashcard(currFlashcard);
                        categoryManagementService.updateCategory(currCategory);
                        notifyDataSetChanged();
                    } catch (EmptyNameException | CategoryNotFoundException | DuplicateNameException e) {
                        displaySnackbar(getResources().getString(R.string.failed_to_remove_flashcard));
                    }
                }
            });

            editFlashcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditCardFragment fragment = EditCardFragment.newInstance(currFlashcard);
                    activityCallback.replaceCurrentFragmentWith(fragment);
                }
            });

            return view;
        }
    }

    //endregion

    //region Members

    private ListView flashcardsLV;
    private FloatingActionButton createFlashcardFAB;
    private ConstraintLayout constraintLayout;
    private List<Flashcard> flashcards;
    private UpdateActivityInterface activityCallback;
    private Category currCategory;
    private FlashcardManagementService flashcardManagementService;
    private CategoryManagementService categoryManagementService;

    //endregion

    //region Static Factory Methods

    /**
     * Initializes a new instance of a CategoriesFlashcardsFragment with the specified Category.
     * @param currCategory The Category to add as an argument.
     * @return A new instance of a CategoriesFlashcardsFragment.
     */
    public static CategoriesFlashcardsFragment newInstance(CategoryParcelable currCategory) {
        CategoriesFlashcardsFragment fragment = new CategoriesFlashcardsFragment();

        Bundle args = new Bundle();
        args.putParcelable(QueensConstants.CATEGORY_KEY, currCategory);
        fragment.setArguments(args);

        return fragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_flashcards, container, false);
        Bundle args = getArguments();

        flashcardManagementService = new FlashcardManagementService(Services.getFlashcardPersistence(),
                                                                    Services.getFcPersistence(),
                                                                    Services.getFaTextPersistence(),
                                                                    Services.getFaTrueFalsePersistence(),
                                                                    Services.getFaMultipleChoicePersistence(),
                                                                    Services.getFlashcardValidator(),
                                                                    Services.getAnswerValidator());

        categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(),
                                                                  Services.getFcPersistence(),
                                                                  Services.getCategoryValidator());

        if(args != null) {
            CategoryParcelable categoryParcelable = args.getParcelable(QueensConstants.CATEGORY_KEY);
            currCategory = categoryParcelable.getCategory();
            flashcards = flashcardManagementService.getFlashcardsInCategory(currCategory);
        }

        flashcardsLV = (ListView) view.findViewById(R.id.lv_flashcards_list);
        createFlashcardFAB = (FloatingActionButton) view.findViewById(R.id.fab_create_flashcard);

        createFlashcardFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCardFragment editCardFragment = EditCardFragment.newInstance(currCategory.getId());
                activityCallback.replaceCurrentFragmentWith(editCardFragment);
            }
        });

        constraintLayout = (ConstraintLayout) view.findViewById(R.id.cl_categories_flashcards);

        FlashcardListAdapter adapter = new FlashcardListAdapter(getContext(), flashcards);
        flashcardsLV.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UpdateActivityInterface) {
            activityCallback = (UpdateActivityInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement UpdateActivityInterface.");
        }
    }

    //endregion

    //region Helper Methods

    /** Default Snackbar to be displayed with a passed in message
     *
     * @param msg - Message to be shown
     */
    private void displaySnackbar(String msg) {
        Snackbar snackbar = Snackbar.make(constraintLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    //endregion

}

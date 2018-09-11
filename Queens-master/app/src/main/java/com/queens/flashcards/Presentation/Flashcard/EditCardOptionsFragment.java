package com.queens.flashcards.Presentation.Flashcard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.queens.flashcards.Logic.CategoryManagementService;
import com.queens.flashcards.Logic.FCManagementService;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Presentation.Parcelable.FlashcardParcelable;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardDataListener;
import com.queens.flashcards.QueensConstants;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Options tab in the Flashcard editing interface.
 */
public abstract class EditCardOptionsFragment extends Fragment {

    //region Adapter

    class CategoryListAdapter extends BaseAdapter {
        private List<Category> categories;
        private List<Long> checkedCategories;
        private Context context;

        public CategoryListAdapter(@NonNull Context context, List<Category> categories,
                                   List<Long> checkedCategories) {
            this.categories = categories;
            this.context = context;
            this.checkedCategories = checkedCategories;
        }

        public CategoryListAdapter(@NonNull Context context, List<Category> categories) {
            this.categories = categories;
            this.context = context;
            this.checkedCategories = new ArrayList<>();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if(view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.row_select_category_checkbox, parent, false);
            }

            final Category currentCategory = getItem(position);
            final Long currCategoryID = currentCategory.getId();

            final TextView hiddenID = (TextView) view.findViewById(R.id.tv_hidden_category_id);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_category);

            //Sets the name of the category, and the ID of the category
            hiddenID.setText(currCategoryID.toString());
            checkBox.setText(currentCategory.getName());

            if(checkedCategories.contains(currCategoryID)) {
                checkBox.setChecked(true);
            }

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //If the checkbox is now checked, add the category to the Flashcard
                    //Else remove the category from the Flashcard
                    if(checkBox.isChecked()) {
                        flashcard = updateFlashcardDataListener.addFlashcardToCategory(currentCategory);
                    } else {
                        flashcard = updateFlashcardDataListener.removeFlashcardFromCategory(currentCategory);
                    }
                }
            });

            return view;
        }

        @Override
        public int getCount() {
            return this.categories.size();
        }

        @Override
        public Category getItem(int position) {
            return this.categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    //endregion

    //region Members

    private UpdateFlashcardDataListener updateFlashcardDataListener;
    private Flashcard flashcard = null;
    private ListView categoryListView;
    private EditText nameEditText;
    private RadioGroup answerTypesGroup;

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_card_options, container, false);

        // Get view references
        nameEditText = (EditText) view.findViewById(R.id.et_card_name);
        categoryListView = (ListView) view.findViewById(R.id.lv_category_selection);
        answerTypesGroup = (RadioGroup) view.findViewById(R.id.rg_answer_types);

        // Initialize adapter and set its data
        CategoryListAdapter adapter;
        Bundle args = getArguments();
        CategoryManagementService categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(),
                                                                                            Services.getFcPersistence(),
                                                                                            Services.getCategoryValidator());
        List<Category> categories = categoryManagementService.getAllCategories();
        if(args != null) {
            FlashcardParcelable flashcardParcelable = args.getParcelable(QueensConstants.CARD_KEY);
            flashcard = flashcardParcelable.getFlashcard();

            //Case for when there are categories that need to also be selected
            FCManagementService fcManagementService = new FCManagementService(Services.getFcPersistence());
            List<Long> containingCategories = fcManagementService.getCategoriesWithFlashcard(flashcard.getId());
            adapter = new CategoryListAdapter(getContext(), categories, containingCategories);
        } else {
            adapter = new CategoryListAdapter(getContext(), categories);
            flashcard = new Flashcard();
        }

        // Set ListView adapter and add item click listeners
        categoryListView.setAdapter(adapter);

        // Update name input and set listener
        nameEditText.setText(flashcard.getName());
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String nameText = editable.toString();
                flashcard = updateFlashcardDataListener.updateFlashcardName(nameText);
            }
        });

        answerTypesGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                switch(checkedID) {
                    case R.id.rb_answer_type_text:
                        updateFlashcardDataListener.changeFlashcardAnswerTo(new FlashcardTextAnswer());
                        break;
                    case R.id.rb_answer_type_tf:
                        updateFlashcardDataListener.changeFlashcardAnswerTo(new FlashcardTFAnswer());
                        break;
                    case R.id.rb_answer_type_mc:
                        updateFlashcardDataListener.changeFlashcardAnswerTo(new FlashcardMCAnswer());
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            updateFlashcardDataListener = (UpdateFlashcardDataListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString() + " must implement UpdateFlashcardDataListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        nameEditText.clearFocus();
    }

    //endregion

}

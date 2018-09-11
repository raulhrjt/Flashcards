package com.queens.flashcards.Presentation.GuessingGame;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.queens.flashcards.Logic.CategoryManagementService;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Presentation.Interface.UpdateActivityInterface;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import java.util.List;

public class CategorySelectionFragment extends Fragment {


    //region Adapter

    class CategoryListAdapter extends BaseAdapter {
        private Context context;
        private List<Category> listOfCategories;

        public CategoryListAdapter(Context context, List<Category> listOfCategories) {
            this.context = context;
            this.listOfCategories = listOfCategories;
        }

        @Override
        public int getCount() {
            return listOfCategories.size();
        }

        @Override
        public Category getItem(int pos) {
            return listOfCategories.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            View view = convertView;

            if(view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.row_category_selection, parent, false);
            }

            TextView categoryName = (TextView) view.findViewById(R.id.tv_category);
            Button startGame = (Button) view.findViewById(R.id.btn_play);

            final Category currCategory = getItem(pos);

            categoryName.setText(currCategory.getName());

            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activityCallback.replaceCurrentFragmentWith(GuessingGameFragment.newInstance(currCategory));
                }
            });

            return view;
        }
    }

    //endregion

    //region Members

    private ListView categoryLV;
    private ConstraintLayout constraintLayout;
    private CategorySelectionFragment.CategoryListAdapter adapter;
    private List<Category> categories;
    private CategoryManagementService categoryManagementService;
    private UpdateActivityInterface activityCallback;

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_category, container, false);

        // Get category data
        activityCallback.updateToolbarTitleWith("Select a Category");

        categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(),
                                                                  Services.getFcPersistence(),
                                                                  Services.getCategoryValidator());
        categories = categoryManagementService.getAllCategories();

        // Get view references
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.CL_select_category);
        categoryLV = (ListView) view.findViewById(R.id.lv_category_to_select);

        adapter = new CategoryListAdapter(getContext(), categories);
        categoryLV.setAdapter(adapter);

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
}

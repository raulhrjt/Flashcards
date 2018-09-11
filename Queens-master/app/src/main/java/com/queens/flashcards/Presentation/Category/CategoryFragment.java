package com.queens.flashcards.Presentation.Category;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.queens.flashcards.Logic.CategoryManagementService;
import com.queens.flashcards.Logic.Exception.CategoryNotFoundException;
import com.queens.flashcards.Logic.Exception.DuplicateNameException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Presentation.Parcelable.CategoryParcelable;
import com.queens.flashcards.Presentation.Interface.UpdateActivityInterface;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import java.util.List;

/**
 * Class for the Category editing fragment.
 */
public class CategoryFragment extends Fragment {

    //region Adapter

    class CategoryListAdapter extends BaseAdapter {
        private List<Category> categoryList;
        private Context context;

        public CategoryListAdapter(Context context, List<Category> categoryList) {
            this.categoryList = categoryList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return categoryList.size();
        }

        @Override
        public Category getItem(int pos) {
            return categoryList.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            View view = convertView;

            if(view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.row_category_display, parent, false);
            }

            final Category currCategory = getItem(pos);
            TextView categoryName = (TextView) view.findViewById(R.id.tv_category_name);
            TextView categoryID = (TextView) view.findViewById(R.id.tv_hidden_category_id);
            ImageButton editCategory = (ImageButton) view.findViewById(R.id.ib_edit_category);
            final ImageButton deleteCategory = (ImageButton) view.findViewById(R.id.ib_delete_category);

            categoryName.setText(currCategory.getName());
            categoryID.setText(currCategory.getId() + "");

            editCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View promptsView = getLayoutInflater().inflate(R.layout.dialog_update_category, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setView(promptsView);

                    final EditText categoryNameInput = (EditText) promptsView.findViewById(R.id.et_category_name);
                    categoryNameInput.setText(currCategory.getName());

                    builder.setCancelable(true)
                            .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String newName = categoryNameInput.getText().toString();
                                    currCategory.setName(newName);

                                    updateCategory(currCategory);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            deleteCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCategory(currCategory);
                }
            });

            categoryName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CategoryParcelable categoryParcelable = new CategoryParcelable(currCategory);
                    CategoriesFlashcardsFragment fragment = CategoriesFlashcardsFragment.newInstance(categoryParcelable);
                    activityCallback.replaceCurrentFragmentWith(fragment);
                }
            });

            return view;
        }
    }

    //endregion

    //region Members

    private ListView categoryLV;
    private FloatingActionButton fabCreateCategory;
    private ConstraintLayout constraintLayout;
    private CategoryListAdapter adapter;
    private List<Category> categories;
    private CategoryManagementService categoryManagementService;
    private UpdateActivityInterface activityCallback;

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        // Get category data
        activityCallback.updateToolbarTitleWith("Manage Categories");

        categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(),
                                                                  Services.getFcPersistence(),
                                                                  Services.getCategoryValidator());
        categories = categoryManagementService.getAllCategories();

        // Get view references
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.cl_category_layout);
        categoryLV = (ListView) view.findViewById(R.id.lv_categories_list);
        fabCreateCategory = (FloatingActionButton) view.findViewById(R.id.fab_new_category);

        // Click listener to create a new Category or cancel its creation.
        fabCreateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View promptsView = getLayoutInflater().inflate(R.layout.dialog_update_category, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(promptsView);

                final EditText categoryNameInput = (EditText) promptsView.findViewById(R.id.et_category_name);

                builder.setCancelable(true)
                        .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                createCategory(categoryNameInput.getText().toString());
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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

    //region Helper Methods

    /** Attempts to create a new Category, if its successful it updates the ListView
     *
     * @param categoryName - Name of the Category to create
     */
    private void createCategory(String categoryName) {
        Category newCategory = new Category(categoryName);

        try {
            newCategory = categoryManagementService.createNewCategory(newCategory);
            categories.add(newCategory);
            adapter.notifyDataSetChanged();
        } catch (EmptyNameException e) {
            displaySnackbar(getResources().getString(R.string.category_name_missing));
        } catch (DuplicateNameException e) {
            displaySnackbar(getResources().getString(R.string.category_dup_name));
        }
    }

    /** Attempts to update the Category, if its successful it updates the ListView
     *
     * @param category - Category to be updated
     */
    private void updateCategory(Category category) {
        try {
            categoryManagementService.updateCategory(category);
            adapter.notifyDataSetChanged();
        } catch (EmptyNameException exception) {
            displaySnackbar(getResources().getString(R.string.category_name_missing));
        } catch (CategoryNotFoundException exception) {
            displaySnackbar(getResources().getString(R.string.failed_to_update_category));
        } catch (DuplicateNameException e) {
            displaySnackbar(getResources().getString(R.string.category_dup_name));
        }
    }

    /** Attempts to delete the Category, if its successful it updates the ListView
     *
     * @param category - Category to delete
     */
    private void deleteCategory(Category category) {
        boolean deleted;

        if(deleted = categoryManagementService.deleteCategory(category)) {
            categories.remove(category);
            adapter.notifyDataSetChanged();
        }

        if (!deleted)
            displaySnackbar(getResources().getString(R.string.failed_to_delete));
    }

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

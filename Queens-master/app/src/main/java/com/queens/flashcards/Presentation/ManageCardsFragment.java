package com.queens.flashcards.Presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.queens.flashcards.Logic.FlashcardManagementService;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Presentation.Flashcard.EditCardFragment;
import com.queens.flashcards.Presentation.Interface.ActivityEventListener;
import com.queens.flashcards.Presentation.Interface.UpdateActivityInterface;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Fragment for the card management interface, where cards can be added, deleted,
 * or be selected for editing.
 */
public class ManageCardsFragment extends Fragment implements ActivityEventListener {

    //region Members

    private UpdateActivityInterface activityCallback;
    private ListView lvFlashcards;
    private Menu mainMenu;
    private boolean deleteMode = false;
    private FlashcardRowAdapter adapter;

    //endregion

    //region Constructor

    public ManageCardsFragment() {
        // Required empty public constructor
    }

    //endregion

    //region Static Factory Methods

    public static ManageCardsFragment newInstance() {
        return new ManageCardsFragment();
    }

    //endregion

    //region Event Handlers

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_cards, container, false);

        activityCallback.updateToolbarTitleWith(getString(R.string.manage_cards));

        // Initialize ListView
        FlashcardManagementService flashcardManagementService = new FlashcardManagementService(Services.getFlashcardPersistence(),
                                                                                               Services.getFcPersistence(),
                                                                                               Services.getFaTextPersistence(),
                                                                                               Services.getFaTrueFalsePersistence(),
                                                                                               Services.getFaMultipleChoicePersistence(),
                                                                                               Services.getFlashcardValidator(),
                                                                                               Services.getAnswerValidator() );
        List<Flashcard> flashcards = flashcardManagementService.getAllFlashcards();
        adapter = new FlashcardRowAdapter(view.getContext(), flashcards);
        lvFlashcards = (ListView) view.findViewById(R.id.lv_flashcards);
        lvFlashcards.setAdapter(adapter);

        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        mainMenu = menu;

        if(menu != null && menu.findItem(R.id.delete_card) != null) {
            menu.findItem(R.id.delete_card).setVisible(deleteMode);
        }
        if (menu != null && menu.findItem(R.id.add) != null) {
            menu.findItem(R.id.add).setVisible(!deleteMode);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_card:
                // ODOT: Use alert dialog to ask "Are you sure you want to delete?"
                if (adapter != null) {
                    int deleted = adapter.deleteSelected();
                    updateButtons();
                    Toast.makeText(getContext(), deleted + " item(s) deleted.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.add:
                Fragment f = EditCardFragment.newInstance(new Flashcard());
                activityCallback.replaceCurrentFragmentWith(f);
                break;

            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    //endregion

    //region Public Methods

    /**
     * Updates the Toolbar buttons based on the current mode.
     */
    public void updateButtons() {
        if (mainMenu != null) {
            mainMenu.findItem(R.id.delete_card).setVisible(deleteMode);
            mainMenu.findItem(R.id.add).setVisible(!deleteMode);
        }
    }

    //endregion

    //region ActivityEventListener Methods

    /**
     * Reverts from deletion mode, otherwise does nothing.
     * @return True if deletion mode was reverted, false otherwise.
     */
    public boolean onBackPressed() {

        if (deleteMode) {
            deleteMode = false;
            adapter.notifyDataSetChanged();
            return true;
        }

        return false;
    }

    //endregion

    /**
     * Row adapter to display Flashcard data on ListView rows.
     */
    class FlashcardRowAdapter extends ArrayAdapter<Flashcard>
    {

        //region Members

        /**
         * Map of flashcards to a flag indicated whether it is selected to be deleted.
         */
        private Map<Flashcard, Boolean> flashcards = new HashMap<>();

        //endregion

        //region Constructor

        public FlashcardRowAdapter(@NonNull Context context, List<Flashcard> flashcards) {
            super(context, R.layout.flashcard_row, flashcards);

            for (Flashcard f : flashcards)
                this.flashcards.put(f, false);
        }

        //endregion

        //region Event Handlers

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            final View customView = layoutInflater.inflate(R.layout.flashcard_row, parent, false);

            // Get reference to flashcard
            final Flashcard flashcard = getItem(position);
            if (flashcard == null)
                return customView;

            // Get references to views in layout
            final TextView tvName = (TextView) customView.findViewById(R.id.tv_name);
            final TextView tvQuestion = (TextView) customView.findViewById(R.id.tv_question);
            final ImageButton btnEditFlashcard = (ImageButton) customView.findViewById(R.id.btn_edit_flashcard);
            final CheckBox cbDelete = (CheckBox) customView.findViewById(R.id.cb_delete);

            // Set fields
            tvName.setText(flashcard.getName());
            tvQuestion.setText(flashcard.getQuestion());
            cbDelete.setChecked(flashcards.get(flashcard));

            // Set visibility for deletion mode
            cbDelete.setVisibility(deleteMode ? View.VISIBLE : View.INVISIBLE);

            // Button click listener to navigate to edit card with current Flashcard
            btnEditFlashcard.setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            EditCardFragment fragment = EditCardFragment.newInstance(flashcard);
                            activityCallback.replaceCurrentFragmentWith(fragment);
                        }
                    }
            );

            // LongClick listener to initialize delete selection
            final FlashcardRowAdapter fthis = this;
            customView.setOnLongClickListener(
                    new View.OnLongClickListener()
                    {
                        @Override
                        public boolean onLongClick(View view) {

                            Toast.makeText(customView.getContext(), "Delete mode", Toast.LENGTH_SHORT).show();
                            if (!deleteMode) {
                                deleteMode = true;
                                fthis.notifyDataSetChanged();
                                updateButtons();
                                return true;
                            }
                            return false;
                        }
                    }
            );

            // Checkbox click listener to set the flag for a Flashcard's deletion
            cbDelete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (flashcards.containsKey(flashcard))
                                flashcards.put(flashcard, cbDelete.isChecked());
                        }
                    }
            );

            return customView;
        }

        //endregion

        //region Public Methods

        /**
         * Deletes all selected Flashcards.
         * @return The number of Flashcards deleted.
         */
        public int deleteSelected() {
            FlashcardManagementService service = new FlashcardManagementService(Services.getFlashcardPersistence(),
                                                                                Services.getFcPersistence(),
                                                                                Services.getFaTextPersistence(),
                                                                                Services.getFaTrueFalsePersistence(),
                                                                                Services.getFaMultipleChoicePersistence(),
                                                                                Services.getFlashcardValidator(),
                                                                                Services.getAnswerValidator());

            int count = 0;
            Set<Flashcard> keys = new HashSet<>(flashcards.keySet());
            for (Flashcard key : keys) {
                if (flashcards.get(key)) {

                    // Remove from DB, deletion map, and adapter
                    service.deleteFlashcard(key);
                    flashcards.remove(key);
                    this.remove(key);

                    count++;
                }
            }

            // Refresh ListView
            deleteMode = false;
            this.notifyDataSetChanged();

            return count;
        }

        //endregion

    }

}

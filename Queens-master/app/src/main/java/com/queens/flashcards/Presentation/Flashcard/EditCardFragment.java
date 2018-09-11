package com.queens.flashcards.Presentation.Flashcard;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.queens.flashcards.Logic.Exception.DuplicateNameException;
import com.queens.flashcards.Logic.Exception.EmptyAnswerException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Logic.Exception.EmptyQuestionException;
import com.queens.flashcards.Logic.Exception.FlashcardNotFoundException;
import com.queens.flashcards.Logic.FCManagementService;
import com.queens.flashcards.Logic.FlashcardManagementService;
import com.queens.flashcards.Logic.ImageService;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Presentation.Flashcard.Factory.EditCardAnswerFragmentFactory;
import com.queens.flashcards.Presentation.Flashcard.Factory.EditCardOptionsFragmentFactory;
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardMCAnswerListener;
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardTFAnswerListener;
import com.queens.flashcards.Presentation.Interface.UpdateFlashcardTextAnswerListener;
import com.queens.flashcards.Presentation.Parcelable.CategoryParcelable;
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
import com.queens.flashcards.Utility;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Flashcard editing interface (outer fragment).
 * <p>
 *     This Fragment hosts the tabs and inner Fragments with Flashcard data.
 * </p>
 */
public class EditCardFragment extends Fragment implements UpdateFlashcardDataListener, UpdateFlashcardMCAnswerListener, UpdateFlashcardTextAnswerListener, UpdateFlashcardTFAnswerListener {

    //region Adapter

    class CardSetupFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private String[] tabTitles;

        public CardSetupFragmentPagerAdapter(FragmentManager fm, String[] titles) {
            super(fm);
            this.tabTitles = titles;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragItem = null;

            //Options - Question - Answer is the order of the tabs
            if(position == 0) {
                fragItem = EditCardOptionsFragmentFactory.createEditCardOptionsFragmentFor(currentCard);
            } else if(position == 1){
                fragItem = EditCardQuestionFragment.newInstance(currentCard);
            } else if(position == 2) {
                fragItem = EditCardAnswerFragmentFactory.createEditCardAnswerFragmentFor(currentCard);
            }

            return fragItem;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    //endregion

    //region Members

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ConstraintLayout constraintLayout;
    private Flashcard currentCard;
    private Bitmap currDrawableCardImage;
    private boolean isNewCard;

    /**
     * A map of Category IDs and a flag representing if the currentCard
     * is a part of the Category.
     */
    List<Long> categoriesAddedTo = new ArrayList<>();

    //endregion

    //region Static Factory Methods

    /**
     * Initializes a new instance of an EditCardFragment with the specified Flashcard.
     * @param flashcard The Flashcard to add as an argument.
     * @return A new instance of an EditCardFragment.
     */
    public static EditCardFragment newInstance(Flashcard flashcard)
    {
        EditCardFragment fragment = new EditCardFragment();

        Bundle args = new Bundle();
        FlashcardParcelable flashcardParcelable = new FlashcardParcelable(flashcard);
        args.putParcelable(QueensConstants.CARD_KEY, flashcardParcelable);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Initializes a new instance of an EditCardFragment with the specified Category.
     * @param categoryID The Category to add as an argument.
     * @return A new instance of an EditCardFragment.
     */
    public static EditCardFragment newInstance(Long categoryID) {
        EditCardFragment fragment = new EditCardFragment();

        Bundle args = new Bundle();
        args.putLong(QueensConstants.CATEGORY_ID_KEY, categoryID);
        fragment.setArguments(args);

        return fragment;
    }

    //endregion

    //region Event Handlers

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_card, container, false);

        // Get args
        Bundle args = getArguments();
        if(args != null) {
            // Get card
            FlashcardParcelable flashcardParcelable = args.getParcelable(QueensConstants.CARD_KEY);

            if (flashcardParcelable == null) {
                currentCard = new Flashcard("", "", new FlashcardTextAnswer());
                isNewCard = true;
            }
            else {
                currentCard = flashcardParcelable.getFlashcard();
                isNewCard = currentCard.getId() < 0;
            }

            // Get category to add card to
            CategoryParcelable categoryParcelable = args.getParcelable(QueensConstants.CATEGORY_KEY);
            if (categoryParcelable != null) {
                Category category = categoryParcelable.getCategory();
                category.add(currentCard.getId());
            }
        }

        // Populate list of Categories the Flashcard is in
        FCManagementService fcManagementService = new FCManagementService(Services.getFcPersistence());
        List<Long> categoryIds = fcManagementService.getCategoriesWithFlashcard(currentCard.getId());
        categoriesAddedTo.addAll(categoryIds);

        String[] tabTitles = getResources().getStringArray(R.array.tab_options);
        viewPager = (ViewPager) view.findViewById(R.id.vp_edit_card_frag_holder);
        viewPager.setAdapter(new CardSetupFragmentPagerAdapter(getChildFragmentManager(), tabTitles));

        tabLayout = (TabLayout) view.findViewById(R.id.tl_edit_card_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Hide soft keyboard input when switching between tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Utility.hideSoftKeyboard(getActivity());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing
            }
        });

        constraintLayout = (ConstraintLayout) view.findViewById(R.id.cl_edit_card);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        if(menu != null && menu.findItem(R.id.save_card) != null) {
            menu.findItem(R.id.save_card).setVisible(true);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean selected = super.onOptionsItemSelected(item);

        //Handle the clicks for creating the menu item
        if(item != null) {
            switch (item.getItemId()) {
                case R.id.save_card:
                    saveCard();

                    if (this.getActivity() != null)
                        Utility.hideSoftKeyboard(this.getActivity());

                    break;
            }
        }

        return selected;
    }

    //endregion

    //region Helper Methods

    /** Creates a new Flashcard using the data in the currentCard variable, data comes from the 3 tabs
     * Catches EmptyQuestion and EmptyAnswer Exceptions for a invalid Flashcard
     * Displays a Snackbar on success/failure
     */
    private void saveCard() {
        FlashcardManagementService flashcardManagementService = new FlashcardManagementService(Services.getFlashcardPersistence(),
                                                                                               Services.getFcPersistence(),
                                                                                               Services.getFaTextPersistence(),
                                                                                               Services.getFaTrueFalsePersistence(),
                                                                                               Services.getFaMultipleChoicePersistence(),
                                                                                               Services.getFlashcardValidator(),
                                                                                               Services.getAnswerValidator() );
        ImageService imageService = new ImageService(Services.getImagePersistence());

        try {
            //Check if the image needs to be saved, and update the Flashcard accordingly
            if(currDrawableCardImage != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                currDrawableCardImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] currImageAsBytes = stream.toByteArray();

                //Empty image location means that there is no image attached to a Flashcard yet
                if(currentCard.getImageLocation().equals("")) {
                    String imageLocation = imageService.saveNewImage(currImageAsBytes);
                    currentCard.setImageLocation(imageLocation);
                } else {
                    imageService.saveImage(currImageAsBytes, currentCard.getImageLocation());
                }
            } else {
                //Null card image means that the image was removed from the card
                if(!currentCard.getImageLocation().equals("")) {
                    currentCard.setImageLocation("");
                }
            }

            // Save Flashcard
            if (isNewCard)
                currentCard = flashcardManagementService.createNewFlashcard(currentCard);
            else
                flashcardManagementService.updateFlashcard(currentCard);

            // Save associations with Categories
            FCManagementService fcManagementService = new FCManagementService(Services.getFcPersistence());
            fcManagementService.removeFlashcard(currentCard.getId());
            for (Long categoryID : categoriesAddedTo)
                fcManagementService.addFlashcardCategory(currentCard.getId(), categoryID);

            displaySnackbar(getResources().getString(R.string.success_card_saved));

        } catch (EmptyNameException ex) {
            displaySnackbar(getResources().getString(R.string.name_missing));
        } catch (EmptyQuestionException emptyQuestion) {
            displaySnackbar(getResources().getString(R.string.question_missing));
        } catch (EmptyAnswerException emptyAnswer) {
            displaySnackbar(getResources().getString(R.string.answer_missing));
        } catch (FlashcardNotFoundException e) {
            displaySnackbar(getResources().getString(R.string.failed_to_save));
        } catch (DuplicateNameException e) {
            displaySnackbar(getResources().getString(R.string.flashcard_dup_name));
        }
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

    //region UpdateFlashcardDataListener Methods

    /** Updates the currentCard with the passed in question
     *
     * @param question - New question text of the Flashcard
     * @return updated Flashcard
     */
    @Override
    public Flashcard updateFlashcardQuestion(String question) {
        currentCard.setQuestion(question);

        return currentCard;
    }

    @Override
    public Flashcard addFlashcardToCategory(Category category) {
        category.add(currentCard.getId());
        categoriesAddedTo.add(category.getId());
        return currentCard;
    }

    @Override
    public Flashcard removeFlashcardFromCategory(Category category) {
        category.remove(currentCard.getId());
        categoriesAddedTo.remove(category.getId());
        return currentCard;
    }

    /** Updates the name of the flashcard
     *
     * @param flashcardName - New name of the Flashcard
     * @return updated Flashcard
     */
    @Override
    public Flashcard updateFlashcardName(String flashcardName) {
        currentCard.setName(flashcardName);

        return currentCard;
    }

    /**
     * Updates the Answer of the Flashcard
     * @param newAnswer - new Answer to be used
     * @return Updated Flashcard
     */
    @Override
    public Flashcard changeFlashcardAnswerTo(FlashcardAnswer newAnswer) {

        // Only assign a new answer if the answer type actually changed (bug fix)
        if(newAnswer != null && !newAnswer.getClass().equals(currentCard.getAnswer().getClass())) {
            currentCard.setAnswer(newAnswer);
        }

        return currentCard;
    }

    /**
     * Updates the drawable Bitmap to use for the Flashcard
     * @param image - Image that will be saved to the card
     */
    @Override
    public void updateFlashcardDrawableImage(Bitmap image) {
        currDrawableCardImage = image;
    }

    //endregion

    //region UpdateFlashcardTextAnswerListener Methods

    /** Updates the currentCard with the passed in answerText
     *
     * @param answerText - New answer text of the Flashcard
     * @return updated Flashcard
     */
    @Override
    public Flashcard updateFlashcardAnswerText(String answerText) {
        if(answerText != null) {
            FlashcardTextAnswer textAnswer = (FlashcardTextAnswer)currentCard.getAnswer();
            textAnswer.setTextAnswer(answerText);
        }

        return currentCard;
    }

    //endregion

    //region UpdateFlashcardMCAnswerListener Methods


    @Override
    public Flashcard updateRightMCAnswer(String answer) {
        if (answer != null) {
            FlashcardMCAnswer mcAnswer = (FlashcardMCAnswer) currentCard.getAnswer();
            mcAnswer.setAnswer(answer);
        }

        return currentCard;
    }

    /**
     * Updates the invalid answers of the Multiple Choice Flashcard.
     * @param wrongAnswers - List of Invalid Answers.
     * @return The updated Flashcard.
     */
    @Override
    public Flashcard updateWrongMCAnswers(List<String> wrongAnswers) {
        if(wrongAnswers != null) {
            FlashcardMCAnswer mcAnswer = (FlashcardMCAnswer)currentCard.getAnswer();
            mcAnswer.setWrongAnswers(wrongAnswers);
        }

        return currentCard;
    }

    //endregion

    //region UpdateFlashcardTFAnswerListener Methods

    /**
     * Updates the boolean answer of the Flashcard's answer
     * @param answer - if the answer is True/False
     * @return The updated Flashcard
     */
    @Override
    public Flashcard updateFlashcardTrueFalseAnswer(boolean answer) {
        FlashcardTFAnswer tfAnswer = (FlashcardTFAnswer) currentCard.getAnswer();
        tfAnswer.setAnswerIsTrue(answer);

        return currentCard;
    }

    //endregion

}

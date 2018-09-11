package com.queens.flashcards.AcceptanceTests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.queens.flashcards.Logic.CategoryManagementService;
import com.queens.flashcards.Logic.Exception.DuplicateNameException;
import com.queens.flashcards.Logic.Exception.EmptyAnswerException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Logic.Exception.EmptyQuestionException;
import com.queens.flashcards.Logic.FlashcardManagementService;
import com.queens.flashcards.Model.Category.Category;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.MainActivity;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.UUID;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TextFlashcardsTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws EmptyNameException, EmptyAnswerException, EmptyQuestionException, DuplicateNameException {
        FlashcardManagementService flashcardManagementService = new FlashcardManagementService(Services.getFlashcardPersistence(),
                Services.getFcPersistence(),
                Services.getFaTextPersistence(),
                Services.getFaTrueFalsePersistence(),
                Services.getFaMultipleChoicePersistence(),
                Services.getFlashcardValidator(),
                Services.getAnswerValidator());
        Flashcard f = new Flashcard("Flashcard", "Flashcard?", new FlashcardTextAnswer("Flashcard"));
        flashcardManagementService.createNewFlashcard(f);
    }

    @After
    public void tearDown() {
        FlashcardManagementService flashcardManagementService = new FlashcardManagementService(Services.getFlashcardPersistence(),
                                                                                                Services.getFcPersistence(),
                                                                                                Services.getFaTextPersistence(),
                                                                                                Services.getFaTrueFalsePersistence(),
                                                                                                Services.getFaMultipleChoicePersistence(),
                                                                                                Services.getFlashcardValidator(),
                                                                                                Services.getAnswerValidator());
        List<Flashcard> flashcards = flashcardManagementService.getAllFlashcards();
        for (Flashcard f : flashcards)
            flashcardManagementService.deleteFlashcard(f);
    }

    @Test
    public void createFlashcard() {
        String questionText = "This is some question text and stuff 150";
        String answerText = "This is some answer text and stuff 150";
        String flashcardName = UUID.randomUUID().toString();

        onView(withId(R.id.btn_cards)).perform(click());
        onView(withId(R.id.add)).perform(click());

        onView(withId(R.id.et_card_name)).perform(typeText(flashcardName));
        closeSoftKeyboard();

        //Switch to the question
        onView(withText("Question")).perform(click());
        onView(withId(R.id.et_edit_card_text_question)).perform(typeText(questionText));
        closeSoftKeyboard();

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.et_edit_card_text_answer)).perform(typeText(answerText));
        closeSoftKeyboard();

        //Saves the Flashcard
        onView(withId(R.id.save_card)).perform(click());

        //Go back, then verify the contents are what is expected
        pressBack();

        //back to main fragment, and go back to the adapter
        pressBack();
        onView(withId(R.id.btn_cards)).perform(click());

        final int[] listLength = new int[1];

        onView(withId(R.id.lv_flashcards)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;

                listLength[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onData(anything()).inAdapterView(withId(R.id.lv_flashcards))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_edit_flashcard))
                .perform(click());

        onView(withId(R.id.et_card_name)).check(matches(withText(flashcardName)));

        onView(withText("Question")).perform(click());
        onView(withId(R.id.et_edit_card_text_question)).check(matches(withText(questionText)));

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.et_edit_card_text_answer)).check(matches(withText(answerText)));
    }

    @Test
    public void editFlashcard() {
        String questionText = "This is some question text idk";
        String answerText = "This is some answer text idk";
        String flashcardName = UUID.randomUUID().toString();

        onView(withId(R.id.btn_cards)).perform(click());

        final int[] listLength = new int[1];

        onView(withId(R.id.lv_flashcards)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;

                listLength[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onData(anything()).inAdapterView(withId(R.id.lv_flashcards))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_edit_flashcard))
                .perform(click());

        onView(withId(R.id.et_card_name)).perform(replaceText(flashcardName));

        onView(withText("Question")).perform(click());
        onView(withId(R.id.et_edit_card_text_question)).perform(replaceText(questionText));

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.et_edit_card_text_answer)).perform(replaceText(answerText));

        //Saves the Flashcard
        onView(withId(R.id.save_card)).perform(click());

        //Go back, then verify the contents are what is expected
        pressBack();

        onView(withId(R.id.lv_flashcards)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;

                listLength[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onData(anything()).inAdapterView(withId(R.id.lv_flashcards))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_edit_flashcard))
                .perform(click());

        onView(withId(R.id.et_card_name)).check(matches(withText(flashcardName)));

        onView(withText("Question")).perform(click());
        onView(withId(R.id.et_edit_card_text_question)).check(matches(withText(questionText)));

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.et_edit_card_text_answer)).check(matches(withText(answerText)));
    }

    @Test
    public void attachCategoryToCard() {
        //Creates the category to attach to
        Category category = new Category(UUID.randomUUID().toString());
        CategoryManagementService categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(),
                Services.getFcPersistence(),
                Services.getCategoryValidator());

        try {
            categoryManagementService.createNewCategory(category);
        } catch (EmptyNameException ex) {
            ex.printStackTrace();
        } catch (DuplicateNameException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.btn_cards)).perform(click());

        final int[] listLength = new int[1];

        onView(withId(R.id.lv_flashcards)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;

                listLength[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onData(anything()).inAdapterView(withId(R.id.lv_flashcards))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_edit_flashcard))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.lv_category_selection))
                .atPosition(0)
                .onChildView(withId(R.id.cb_category))
                .perform(click());

        //Saves the Flashcard
        onView(withId(R.id.save_card)).perform(click());

        pressBack();

        onView(withId(R.id.lv_flashcards)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;

                listLength[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onData(anything()).inAdapterView(withId(R.id.lv_flashcards))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_edit_flashcard))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.lv_category_selection))
                .atPosition(0)
                .onChildView(withId(R.id.cb_category))
                .check(matches(isChecked()));
    }
}

package com.queens.flashcards.AcceptanceTests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GuessingGameOptionsTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupFakeData() {
        Category category = new Category(UUID.randomUUID().toString());
        FlashcardAnswer answer;
        Flashcard flashcard;
        String uuid;

        CategoryManagementService categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(),
                Services.getFcPersistence(),
                Services.getCategoryValidator());
        FlashcardManagementService flashcardManagementService = new FlashcardManagementService(Services.getFlashcardPersistence(),
                Services.getFcPersistence(),
                Services.getFaTextPersistence(),
                Services.getFaTrueFalsePersistence(),
                Services.getFaMultipleChoicePersistence(),
                Services.getFlashcardValidator(),
                Services.getAnswerValidator() );

        for(int i = 0; i < 4; i++) {
            try {
                uuid = UUID.randomUUID().toString();
                answer = new FlashcardTextAnswer("answer");
                flashcard = new Flashcard(uuid, "question", answer);
                flashcard = flashcardManagementService.createNewFlashcard(flashcard);
                category.add(flashcard.getId());
            } catch (EmptyNameException | EmptyQuestionException | EmptyAnswerException | DuplicateNameException ex) {
                ex.printStackTrace();
            }
        }

        try {
            categoryManagementService.createNewCategory(category);
        } catch (EmptyNameException | DuplicateNameException ex) {
            ex.printStackTrace();
        }
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

        CategoryManagementService categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(), Services.getFcPersistence(), Services.getCategoryValidator());
        List<Category> categories = categoryManagementService.getAllCategories();
        for (Category c : categories)
            categoryManagementService.deleteCategory(c);
    }

    @Test
    public void setTimeLimitForGuessingGameTest() {
        onView(withId(R.id.btn_select_category)).perform(click());

        final int[] listLength = new int[1];

        onView(withId(R.id.lv_category_to_select)).check(matches(new TypeSafeMatcher<View>() {
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

        onData(anything()).inAdapterView(withId(R.id.lv_category_to_select))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_play))
                .perform(click());

        onView(withId(R.id.et_time_limit_input)).perform(typeText("150"));
        closeSoftKeyboard();

        onView(withId(R.id.btn_start_game)).perform(click());

        //Check that the game started, and that there is time actually being displayed
        onView(withId(R.id.cl_gg_navigation)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_remaining_time_title)).check(matches(isDisplayed()));
    }

    @Test
    public void changeCardsInGuessingGameTest() {
        onView(withId(R.id.btn_select_category)).perform(click());

        final int[] listLength = new int[1];

        onView(withId(R.id.lv_category_to_select)).check(matches(new TypeSafeMatcher<View>() {
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

        onData(anything()).inAdapterView(withId(R.id.lv_category_to_select))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_play))
                .perform(click());

        onView(withId(R.id.lv_select_gg_cards)).check(matches(new TypeSafeMatcher<View>() {
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

        //Uncheck the last 2 flashcards
        onData(anything()).inAdapterView(withId(R.id.lv_select_gg_cards))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.cb_flashcard_name))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.lv_select_gg_cards))
                .atPosition(listLength[0] - 2)
                .onChildView(withId(R.id.cb_flashcard_name))
                .perform(click());

        onView(withId(R.id.btn_start_game)).perform(click());

        //go through the 2 expected questions
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());

        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());

        //Make sure its only 2 questions, as there are 4 initially with 2 being removed
        onView(withId(R.id.tv_results_title)).check(matches(isDisplayed()));
    }

    @Test
    public void reverseOptionTest() {
        onView(withId(R.id.btn_select_category)).perform(click());
        final int[] listLength = new int[1];

        onView(withId(R.id.lv_category_to_select)).check(matches(new TypeSafeMatcher<View>() {
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

        onData(anything()).inAdapterView(withId(R.id.lv_category_to_select))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_play))
                .perform(click());

        onView(withId(R.id.sc_reverse_qa)).perform(click());
        onView(withId(R.id.btn_start_game)).perform(click());

        // Go through all 4 pre-defined cards while reverse option is on
        // In this case, it can only test Text-Type cards
        for(int i = 0; i < 4; i++) {
            onView(withId(R.id.tv_question_text)).check(matches(withText("answer")));
            onView(withId(R.id.btn_okay)).perform(click());
            onView(withId(R.id.tv_correct_answer)).check(matches(withText("question")));
            onView(withId(R.id.btn_next_card)).perform(click());
        }

    }

    @Test
    public void setMaxCardsGuessingGameTest1() {
        // test1: input a valid number(2), should play 2 cards
        onView(withId(R.id.btn_select_category)).perform(click());

        final int[] listLength = new int[1];
        onView(withId(R.id.lv_category_to_select)).check(matches(new TypeSafeMatcher<View>() {
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
        onData(anything()).inAdapterView(withId(R.id.lv_category_to_select))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_play))
                .perform(click());

        onView(withId(R.id.et_max_cards_input)).perform(typeText("2"));
        closeSoftKeyboard();

        onView(withId(R.id.btn_start_game)).perform(click());

        //game playing
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());

        onView(withId(R.id.btn_exit_game)).perform(click());
    }


    @Test
    public void setMaxCardsGuessingGameTest2() {
        // test2: input an invalid number(100)
        // which is larger than the number of cards that the category contains(4),
        // should play all cards(4) by default

        onView(withId(R.id.btn_select_category)).perform(click());

        final int[] listLength = new int[1];
        onView(withId(R.id.lv_category_to_select)).check(matches(new TypeSafeMatcher<View>() {
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
        onData(anything()).inAdapterView(withId(R.id.lv_category_to_select))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_play))
                .perform(click());

        onView(withId(R.id.et_max_cards_input)).perform(typeText("100"));
        closeSoftKeyboard();

        onView(withId(R.id.btn_start_game)).perform(click());

        //game playing
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());

        onView(withId(R.id.btn_exit_game)).perform(click());
    }

    @Test
    public void setMaxCardsGuessingGameTest3() {
        // test3: no input,
        // should play all cards(4) by default

        onView(withId(R.id.btn_select_category)).perform(click());

        final int[] listLength = new int[1];
        onView(withId(R.id.lv_category_to_select)).check(matches(new TypeSafeMatcher<View>() {
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
        onData(anything()).inAdapterView(withId(R.id.lv_category_to_select))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.btn_play))
                .perform(click());


        onView(withId(R.id.btn_start_game)).perform(click());

        //game playing
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());
        onView(withId(R.id.btn_okay)).perform(click());
        onView(withId(R.id.btn_next_card)).perform(click());

        onView(withId(R.id.btn_exit_game)).perform(click());
    }
}

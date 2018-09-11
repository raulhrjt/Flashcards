package com.queens.flashcards.AcceptanceTests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.ListView;

import com.queens.flashcards.Logic.FlashcardManagementService;
import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Presentation.MainActivity;
import com.queens.flashcards.R;
import com.queens.flashcards.Services;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CustomizableFlashcardsTests {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

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
    public void saveImageToFlashcardTest() {
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

        //Add an image
        onView(withId(R.id.rb_include_image)).perform(click());
        onView(withId(R.id.btn_save_image)).perform(click());

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.et_edit_card_text_answer)).perform(typeText(answerText));
        closeSoftKeyboard();

        //Saves the Flashcard
        onView(withId(R.id.save_card)).perform(click());

        //Go back, then verify the contents are what is expected
        pressBack();

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

        //validate that the image is there
        onView(withId(R.id.rb_include_image)).check(matches(isChecked()));
        onView(withId(R.id.cl_drawing_image_layout)).check(matches(isDisplayed()));

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.et_edit_card_text_answer)).check(matches(withText(answerText)));
    }

    @Test
    public void multipleChoiceFlashcardTest() {
        String questionText = "This is some question text and stuff 150";
        String answerText = "this is some answer text";
        String wrongAnswer1 = "wrong answer 1";
        String wrongAnswer2 = "wrong answer 2";
        String wrongAnswer3 = "wrong answer 3";
        String flashcardName = UUID.randomUUID().toString();

        onView(withId(R.id.btn_cards)).perform(click());
        onView(withId(R.id.add)).perform(click());

        onView(withId(R.id.et_card_name)).perform(typeText(flashcardName));
        closeSoftKeyboard();

        onView(withId(R.id.rb_answer_type_mc)).perform(click());

        //Switch to the question
        onView(withText("Question")).perform(click());
        onView(withId(R.id.et_edit_card_text_question)).perform(typeText(questionText));
        closeSoftKeyboard();

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.et_mc_answer)).perform(typeText(answerText));
        closeSoftKeyboard();
        onView(withId(R.id.et_incorrect_answer_1)).perform(typeText(wrongAnswer1));
        closeSoftKeyboard();
        onView(withId(R.id.et_incorrect_answer_2)).perform(typeText(wrongAnswer2));
        closeSoftKeyboard();
        onView(withId(R.id.et_incorrect_answer_3)).perform(typeText(wrongAnswer3));
        closeSoftKeyboard();

        //Saves the Flashcard
        onView(withId(R.id.save_card)).perform(click());

        //Go back, then verify the contents are what is expected
        pressBack();

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
        onView(withId(R.id.rb_answer_type_mc)).check(matches(isChecked()));

        onView(withText("Question")).perform(click());
        onView(withId(R.id.et_edit_card_text_question)).check(matches(withText(questionText)));

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.et_mc_answer)).check(matches(withText(answerText)));
        onView(withId(R.id.et_incorrect_answer_1)).check(matches(withText(wrongAnswer1)));
        onView(withId(R.id.et_incorrect_answer_2)).check(matches(withText(wrongAnswer2)));
        onView(withId(R.id.et_incorrect_answer_3)).check(matches(withText(wrongAnswer3)));
    }

    @Test
    public void trueFalseFlashcardTest() {
        String questionText = "This is some question text and stuff 150";
        String flashcardName = UUID.randomUUID().toString();

        onView(withId(R.id.btn_cards)).perform(click());
        onView(withId(R.id.add)).perform(click());

        onView(withId(R.id.et_card_name)).perform(typeText(flashcardName));
        closeSoftKeyboard();

        onView(withId(R.id.rb_answer_type_tf)).perform(click());

        //Switch to the question
        onView(withText("Question")).perform(click());
        onView(withId(R.id.et_edit_card_text_question)).perform(typeText(questionText));
        closeSoftKeyboard();

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.rb_answer_false)).perform(click());

        //Saves the Flashcard
        onView(withId(R.id.save_card)).perform(click());

        //Go back, then verify the contents are what is expected
        pressBack();

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
        onView(withId(R.id.rb_answer_type_tf)).check(matches(isChecked()));

        onView(withText("Question")).perform(click());
        onView(withId(R.id.et_edit_card_text_question)).check(matches(withText(questionText)));

        //Switch to the answer
        onView(withText("Answer")).perform(click());
        onView(withId(R.id.rb_answer_false)).check(matches(isChecked()));
    }
}

package com.queens.flashcards.AcceptanceTests;

import android.net.sip.SipErrorCode;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.ListView;

import com.queens.flashcards.Logic.CategoryManagementService;
import com.queens.flashcards.Logic.Exception.DuplicateNameException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Model.Category.Category;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CategoryTests {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws EmptyNameException, DuplicateNameException {
        CategoryManagementService categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(), Services.getFcPersistence(), Services.getCategoryValidator());
        Category c = new Category("Category");
        categoryManagementService.createNewCategory(c);
    }

    @After
    public void tearDown() {
        CategoryManagementService categoryManagementService = new CategoryManagementService(Services.getCategoryPersistence(), Services.getFcPersistence(), Services.getCategoryValidator());
        List<Category> categories = categoryManagementService.getAllCategories();
        for (Category c : categories)
            categoryManagementService.deleteCategory(c);
    }

    @Test
    public void createCategoryTest() {
        String categoryName = UUID.randomUUID().toString();

        onView(withId(R.id.btn_categories)).perform(click());
        onView(withId(R.id.fab_new_category)).perform(click());

        onView(withId(R.id.et_category_name)).perform(typeText(categoryName));
        onView(withText(R.string.create)).perform(click());

        final int[] listLength = new int[1];

        onView(withId(R.id.lv_categories_list)).check(matches(new TypeSafeMatcher<View>() {
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

        onData(anything()).inAdapterView(withId(R.id.lv_categories_list))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.ib_edit_category))
                .perform(click());

        onView(withId(R.id.et_category_name)).check(matches(withText(categoryName)));
    }

    @Test
    public void editCategoryTest() {
        String categoryName = UUID.randomUUID().toString();

        onView(withId(R.id.btn_categories)).perform(click());

        final int[] listLength = new int[1];

        onView(withId(R.id.lv_categories_list)).check(matches(new TypeSafeMatcher<View>() {
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

        onData(anything()).inAdapterView(withId(R.id.lv_categories_list))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.ib_edit_category))
                .perform(click());

        onView(withId(R.id.et_category_name)).perform(replaceText(categoryName));
        onView(withText(R.string.save)).perform(click());

        onView(withId(R.id.lv_categories_list)).check(matches(new TypeSafeMatcher<View>() {
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

        onData(anything()).inAdapterView(withId(R.id.lv_categories_list))
                .atPosition(listLength[0] - 1)
                .onChildView(withId(R.id.ib_edit_category))
                .perform(click());

        onView(withId(R.id.et_category_name)).check(matches(withText(categoryName)));
    }
}

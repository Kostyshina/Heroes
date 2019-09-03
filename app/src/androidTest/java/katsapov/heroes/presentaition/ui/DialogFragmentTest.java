package katsapov.heroes.presentaition.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import katsapov.heroes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DialogFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void dialogFragmentTest() {
        ViewInteraction cardView = onView(
                allOf(withId(R.id.card_view),
                        childAtPosition(
                                allOf(withId(R.id.recyclerView),
                                        childAtPosition(
                                                withId(R.id.swipeRefresh),
                                                0)),
                                1),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("Culture:"),
                        childAtPosition(
                                allOf(withId(R.id.row1),
                                        childAtPosition(
                                                withId(R.id.key),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withText("Gender:"),
                        childAtPosition(
                                allOf(withId(R.id.row2),
                                        childAtPosition(
                                                withId(R.id.key),
                                                1)),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withText("Die:"),
                        childAtPosition(
                                allOf(withId(R.id.row7),
                                        childAtPosition(
                                                withId(R.id.key),
                                                2)),
                                0),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withText("Born:"),
                        childAtPosition(
                                allOf(withId(R.id.row3),
                                        childAtPosition(
                                                withId(R.id.key),
                                                3)),
                                0),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withText("Url:"),
                        childAtPosition(
                                allOf(withId(R.id.row4),
                                        childAtPosition(
                                                withId(R.id.key),
                                                4)),
                                0),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withText("Url:"),
                        childAtPosition(
                                allOf(withId(R.id.row4),
                                        childAtPosition(
                                                withId(R.id.key),
                                                4)),
                                0),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withText("Father:"),
                        childAtPosition(
                                allOf(withId(R.id.row5),
                                        childAtPosition(
                                                withId(R.id.key),
                                                5)),
                                0),
                        isDisplayed()));
        textView7.check(matches(isDisplayed()));

        ViewInteraction textView8 = onView(
                allOf(withText("Mother:"),
                        childAtPosition(
                                allOf(withId(R.id.row6),
                                        childAtPosition(
                                                withId(R.id.key),
                                                6)),
                                0),
                        isDisplayed()));
        textView8.check(matches(isDisplayed()));

        ViewInteraction textView9 = onView(
                allOf(withText("Mother:"),
                        childAtPosition(
                                allOf(withId(R.id.row6),
                                        childAtPosition(
                                                withId(R.id.key),
                                                6)),
                                0),
                        isDisplayed()));
        textView9.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.dialogButtonOK),
                        childAtPosition(
                                allOf(withId(R.id.key),
                                        childAtPosition(
                                                withId(R.id.rows),
                                                0)),
                                7),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.rows),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.rows),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout2.check(matches(isDisplayed()));

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.rows),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout3.check(matches(isDisplayed()));

        ViewInteraction linearLayout4 = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.content),
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout4.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

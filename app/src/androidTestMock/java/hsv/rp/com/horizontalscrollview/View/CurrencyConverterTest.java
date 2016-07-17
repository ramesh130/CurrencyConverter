package hsv.rp.com.horizontalscrollview.View;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import hsv.rp.com.CurrencyConverter.View.CurrencyConverterActivity;
import hsv.rp.com.horizontalscrollview.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author ramesh130@gmail.com
 */
public class CurrencyConverterTest {

    @Rule
    public ActivityTestRule<CurrencyConverterActivity> mActivityRule = new ActivityTestRule<>(
            CurrencyConverterActivity.class);

    @Test
    public void testOnEditTextChange() throws Exception {
        String newCurrencyText = "2391";
        onView(withId(R.id.currency_converter_edittext_input)).perform(typeText(newCurrencyText), closeSoftKeyboard());

        //Check if the converted currency view is populated
        onView(withId(R.id.currency_converter_textview_computed)).check(matches(hasSomeLength()));
    }

    public static Matcher<View> hasSomeLength() {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                return ((TextView)view).getText().length() > 1 ? true : false;
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }

}
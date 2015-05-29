package com.halzhang.android.example.testexample;

import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Espresso example
 * Created by zhanghanguo@yy.com on 2015/5/29.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private static final String USERNAME = "Espresso";//中文输入法会有问题
    private static final String MATCHES_TEXT = "hello," + USERNAME;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        assertNotNull(getActivity());
    }

    public void testEnterHint() {
        onView(withId(R.id.username)).check(matches(withHint(R.string.hint_username)));
    }

    public void testEnterUsername() {
        onView(withId(R.id.username)).perform(typeText(USERNAME), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(click());
        onView(withId(R.id.message)).check(matches(withText(MATCHES_TEXT)));
    }
}

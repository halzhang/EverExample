package com.halzhang.android.example.testexample;

import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


/**
 * Espresso data example
 * Created by zhanghanguo@yy.com on 2015/5/29.
 */
public class DataListActivityTest extends ActivityInstrumentationTestCase2<DataListActivity> {

    private static final String DATA = Datas.DATA + "0";

    private static final String MATCHES_TEXT = "hello," + DATA;

    public DataListActivityTest() {
        super(DataListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        assertNotNull(getActivity());
    }

    public void testListViewItemClick() {
        onData(allOf(is(instanceOf(String.class)), is(String.valueOf(DATA)))).perform(click());
        onView(withId(R.id.message)).check(matches(withText(MATCHES_TEXT)));
    }
}

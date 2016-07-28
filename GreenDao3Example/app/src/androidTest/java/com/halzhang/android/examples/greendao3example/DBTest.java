package com.halzhang.android.examples.greendao3example;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.halzhang.android.examples.greendao3example.dao.DaoMaster;
import com.halzhang.android.examples.greendao3example.dao.DaoSession;
import com.halzhang.android.examples.greendao3example.dao.UserDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Hal on 2016/7/28.
 */
@RunWith(AndroidJUnit4.class)
public class DBTest extends ActivityInstrumentationTestCase2 {

    private DaoSession mDaoSession;
    private UserDao mUserDao;

    public DBTest() {
        super(TestActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getInstrumentation().getContext(), "test.db", null);
        assertNotNull(helper);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        assertNotNull(daoMaster);
        mDaoSession = daoMaster.newSession();
        assertNotNull(mDaoSession);
        mUserDao = mDaoSession.getUserDao();
        assertNotNull(mUserDao);
    }

    @Test
    public void testUserDao() {
        mUserDao.deleteAll();
        long count = mUserDao.count();
        assertEquals(0, count);

        long uid = 0;

        User user = new User();
        user.setUid(11111);
        user.setName("hello");
        user.setAge(1);
        mUserDao.insert(user);

        count = mUserDao.count();
        assertEquals(1, count);


    }

}

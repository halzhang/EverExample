package com.halzhang.android.examples.greendao3example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.halzhang.android.examples.greendao3example.dao.DaoMaster;
import com.halzhang.android.examples.greendao3example.dao.DaoSession;
import com.halzhang.android.examples.greendao3example.dao.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GreenDao3";
    private DaoSession mDaoSession;
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "test.db", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
        mUserDao = mDaoSession.getUserDao();

        User user = new User();
        user.setUid(112111);
        user.setName("hello");
        user.setAge(1);
        mUserDao.insert(user);

        long count = mUserDao.count();
        Log.d(TAG, "onCreate: count - " + count);

        List<User> users = mUserDao.queryBuilder().build().list();
        Log.d(TAG, "onCreate: size - " + users.size());


    }
}

package com.halzhang.android.examples.greendao3example;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Hal on 2016/7/28.
 */
@Entity
public class User {

    /**
     * 主键自增，类型必须用Long而不能用long，否则主键值默认为0
     */
    @Id(autoincrement = true)
    public Long id;

    public long uid;
    public String name;
    public int age;

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    @Generated(hash = 1347702529)
    public User(Long id, long uid, String name, int age) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 586692638)
    public User() {
    }

}

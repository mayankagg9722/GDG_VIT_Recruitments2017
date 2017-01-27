package com.example.mayankaggarwal.gdg_recruitments2017;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.mayankaggarwal.gdg_recruitments2017.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mayankaggarwal on 27/01/17.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from User.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(User.class);
        realm.commitTransaction();
    }

    //find all objects in the User.class
    public RealmResults<User> getUsers() {

        return realm.where(User.class).findAll();
    }

    //query a single item with the given id
    public User getUser(String id) {

        return realm.where(User.class).equalTo("id", id).findFirst();
    }

    //check if User.class is empty
    public boolean hasUsers() {

        return !realm.allObjects(User.class).isEmpty();
    }

    //query example
    public RealmResults<User> queryedUsers() {

        return realm.where(User.class)
                .contains("website", "Website 0")
                .or()
                .contains("username", "Realm")
                .findAll();

    }
}

package com.example.mayankaggarwal.gdg_recruitments2017;

import android.content.Context;

import com.example.mayankaggarwal.gdg_recruitments2017.model.User;

import io.realm.RealmResults;

/**
 * Created by mayankaggarwal on 27/01/17.
 */

public class RealmUsersAdapter extends RealmModelAdapter<User> {

public RealmUsersAdapter(Context context, RealmResults<User> realmResults, boolean automaticUpdate) {

    super(context, realmResults, automaticUpdate);
        }
}
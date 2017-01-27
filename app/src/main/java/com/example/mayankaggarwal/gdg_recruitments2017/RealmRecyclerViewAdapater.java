package com.example.mayankaggarwal.gdg_recruitments2017;

import android.support.v7.widget.RecyclerView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;

/**
 * Created by mayankaggarwal on 27/01/17.
 */

public abstract class RealmRecyclerViewAdapater <T extends RealmObject> extends RecyclerView.Adapter {

    private RealmBaseAdapter<T> realmBaseAdapter;

    public T getItem(int position) {

        return realmBaseAdapter.getItem(position);
    }

    public RealmBaseAdapter<T> getRealmAdapter() {

        return realmBaseAdapter;
    }

    public void setRealmAdapter(RealmUsersAdapter realmAdapter) {

        realmBaseAdapter = (RealmBaseAdapter<T>) realmAdapter;
    }
}

package com.example.mayankaggarwal.gdg_recruitments2017;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mayankaggarwal.gdg_recruitments2017.model.User;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    private UsersAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private FloatingActionButton fab;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);


        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecycler();

        if (!Prefs.getPreLoad(this)) {
            setRealmData();
        }

        // refresh the realm instance

        RealmController.with(this).refresh();


        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getUsers());

        Toast.makeText(this, "Press card item for edit, long press to remove item", Toast.LENGTH_LONG).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inflater = MainActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.edit_item, null);
                final EditText editWebsite = (EditText) content.findViewById(R.id.website);
                final EditText editUsername = (EditText) content.findViewById(R.id.username);
                final EditText editPassword = (EditText) content.findViewById(R.id.password);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(content)
                        .setTitle("Add Website Details")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                User user = new User();
                                user.setId(RealmController.getInstance().getUsers().size()+1);
                                user.setWebsite(editWebsite.getText().toString());
                                user.setUsername(editUsername.getText().toString());
                                user.setPassword(editPassword.getText().toString());

                                if (editWebsite.getText() == null || editWebsite.getText().toString().equals("") || editWebsite.getText().toString().equals(" ")) {
                                    Toast.makeText(MainActivity.this, "Entry not saved, missing title", Toast.LENGTH_LONG).show();
                                } else {
                                    // Persist your data easily
                                    realm.beginTransaction();
                                    realm.copyToRealm(user);
                                    realm.commitTransaction();

                                    adapter.notifyDataSetChanged();

                                    // scroll the recycler view to bottom
                                    recycler.scrollToPosition(RealmController.getInstance().getUsers().size() - 1);
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void setRealmAdapter(RealmResults<User> users) {

        RealmUsersAdapter realmAdapter = new RealmUsersAdapter(this.getApplicationContext(), users, true);
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        recycler.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        adapter = new UsersAdapter(this);
        recycler.setAdapter(adapter);
    }

    private void setRealmData() {

        ArrayList<User> users = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setWebsite("Github");
        user.setUsername("mayankagg9722");
        user.setPassword("Mayank_Aggarwal");
        users.add(user);

        user = new User();
        user.setId(3);
        user.setWebsite("Facebook");
        user.setUsername("gdgvit@faceuser.com");
        user.setPassword("15BCE0751");
        users.add(user);

        user = new User();
        user.setId(2);
        user.setWebsite("Gmail");
        user.setUsername("mayankagg9722@gmail.com");
        user.setPassword("pqrs");
        users.add(user);

        user = new User();
        user.setId(4);
        user.setWebsite("Twitter");
        user.setUsername("mayankaggarwal9722");
        user.setPassword("bbcg");
        users.add(user);

        user = new User();
        user.setId(5);
        user.setWebsite("LinkedIn");
        user.setUsername("gdgVitVellore");
        user.setPassword("xyz");
        users.add(user);


        for (User u : users) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(u);
            realm.commitTransaction();
        }

        Prefs.setPreLoad(true, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}

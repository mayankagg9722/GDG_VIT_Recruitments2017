package com.example.mayankaggarwal.gdg_recruitments2017;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.ButtonBarLayout;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.text.InputType;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.example.mayankaggarwal.gdg_recruitments2017.model.User;

        import io.realm.Realm;
        import io.realm.RealmResults;

/**
 * Created by mayankaggarwal on 27/01/17.
 */


public class  UsersAdapter extends RealmRecyclerViewAdapater<User> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public UsersAdapter(Context context) {

        this.context = context;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();

        // get the article
        final User user = getItem(position);

        final CardViewHolder holder = (CardViewHolder) viewHolder;

        // set the title and the snippet
        holder.textWebsite.setText(user.getWebsite());
        holder.textUsername.setText(user.getUsername());
        holder.textPassword.setText(user.getPassword());
        holder.textButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        holder.textPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        holder.textPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });


        //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                RealmResults<User> results = realm.where(User.class).findAll();

                // Get the user website to show it in toast message
                User u = results.get(position);
                String title = u.getWebsite();

                // All changes to data must happen in a transaction
                realm.beginTransaction();

                // remove single match
                results.remove(position);
                realm.commitTransaction();

                if (results.size() == 0) {
                    Prefs.setPreLoad(false,context);
                }

                notifyDataSetChanged();

                Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //update single match from realm
        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_item, null);
                final EditText editWebsite = (EditText) content.findViewById(R.id.website);
                final EditText editUsername = (EditText) content.findViewById(R.id.username);
                final EditText editPassword = (EditText) content.findViewById(R.id.password);

                editWebsite.setText(user.getWebsite());
                editUsername.setText(user.getUsername());
                editPassword.setText(user.getPassword());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Edit website information")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmResults<User> results = realm.where(User.class).findAll();

                                realm.beginTransaction();
                                results.get(position).setWebsite(editWebsite.getText().toString());
                                results.get(position).setUsername(editUsername.getText().toString());
                                results.get(position).setPassword(editPassword.getText().toString());

                                realm.commitTransaction();

                                notifyDataSetChanged();
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

    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder {


        public CardView card;
        public TextView textWebsite;
        public TextView textUsername;
        public TextView textPassword;
        public Button textButton;


        public CardViewHolder(View itemView) {

            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_users);
            textWebsite = (TextView) itemView.findViewById(R.id.text_website);
            textUsername = (TextView) itemView.findViewById(R.id.text_username);
            textPassword = (TextView) itemView.findViewById(R.id.text_password);
            textButton=(Button) itemView.findViewById(R.id.hideshow);
        }
    }
}
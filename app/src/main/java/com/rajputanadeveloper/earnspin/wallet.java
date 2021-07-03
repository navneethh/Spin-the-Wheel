package com.rajputanadeveloper.earnspin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class wallet extends AppCompatActivity {   private FirebaseDatabase mFirebaseDatabase;

    private ChildEventListener childEventListener;

    shareadapter adapter;
    ListView listView;
    ArrayList<statusword> words;
    private InterstitialAd interstitialAd;
    private int i = 0;String g="",gi="", k="",namee="";

    private DatabaseReference mDatabaseReference,mDatabasePresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        listView = (ListView) findViewById(R.id.lissst);
        Intent i = getIntent();
        String uid=i.getStringExtra("key");
        final TextView sco= (TextView) findViewById(R.id.present_score);
        Button reed= (Button) findViewById(R.id.Redeembutton);
        mDatabaseReference = mFirebaseDatabase.getReference().child("score/deatail").child(uid);
        mDatabasePresent = mFirebaseDatabase.getReference().child("score/present").child(uid);
        words = new ArrayList<statusword>();
        attach();
        mDatabasePresent.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long temp = (Long) dataSnapshot.child("score").getValue();

                    sco.setText(temp.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getBaseContext(),Reedem.class);
                startActivity(i);
            }
        });

            adapter =
                new shareadapter(this, words, R.color.colorAccent);
        listView.setAdapter(adapter);


    }  void attach() {
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String statuskey=(String) messageSnapshot.child("des").getValue();
                    Long t=(Long) messageSnapshot.child("t").getValue();
                    Long c=(Long) messageSnapshot.child("sc").getValue();

                    String name = t.toString();
                    String message = c.toString();
                    words.add(0,new statusword(name, message,statuskey));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}

package com.rajputanadeveloper.earnspin;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reedem extends AppCompatActivity {
    private DatabaseReference mDatabaseReference,mDatabasePresent;
    private FirebaseAuth mAuth;
    TextView coins;Button ree;String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reedem);
        mAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("score/present").child(mAuth.getCurrentUser().getUid());
        coins=(TextView) findViewById(R.id.coins);
        ree=(Button) findViewById(R.id.cashout);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                temp= dataSnapshot.child("score").getValue().toString();
                coins.setText(temp);
                ree.setVisibility(View.VISIBLE);
            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
ree.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if((Integer.parseInt(temp))>60000){

        }else{
            Snackbar.make(v,"You must have 60,000 coins to get Rs 100", BaseTransientBottomBar.LENGTH_LONG).show();
               }
    }
});
}}

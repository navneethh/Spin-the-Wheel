package com.rajputanadeveloper.earnspin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class notifi extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi);
        final WebView post_desc =(WebView) findViewById(R.id.notificationeweb);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("not");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
String desc= dataSnapshot.child("noti").getValue().toString();
                post_desc.loadData(desc,"text/html","utf-8");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});

}
    }

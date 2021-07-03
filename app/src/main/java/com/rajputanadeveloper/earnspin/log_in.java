package com.rajputanadeveloper.earnspin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Random;

public class log_in extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    String Username="anonymus",useruid=null,refername=null;
    EditText name,email,phone,refferal;boolean bb=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        name=(EditText) findViewById(R.id.nameT);
        email=(EditText) findViewById(R.id.emailT);
        phone=(EditText) findViewById(R.id.mobileT);
        refferal=(EditText) findViewById(R.id.refT);
        Button sign=(Button) findViewById(R.id.buttonsign);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();



        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){// User is signed in

                    onSignedInInitialize(user.getDisplayName());
                    useruid=user.getUid();


                    DatabaseReference use = FirebaseDatabase.getInstance().getReference().child("user").child(mAuth.getCurrentUser().getUid());
                    use.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String re=(String)dataSnapshot.child("ref").getValue();
                                name.setText((String)dataSnapshot.child("na").getValue());
                                email.setText((String)dataSnapshot.child("em").getValue());
                                phone.setText((String)dataSnapshot.child("mob").getValue());
                           if (re.length()>4){

                               if (re.substring(0, 3).equals("new")) {
                                    refferal.setText( re);
                                }}else{
                                    refferal.setText("new"+re);
                                }
                                refername=(String) dataSnapshot.child("inv").getValue();
                                bb=(boolean)dataSnapshot.child("rtd").getValue();
                                refferal.setVisibility(View.GONE);
                            }else{
                                DatabaseReference tyt = FirebaseDatabase.getInstance().getReference().child("score/present").child(useruid);
                                Long lt = System.currentTimeMillis();
                                tyt.child("score").setValue(0L);
                                tyt.child("l").setValue(lt);
                                tyt.child("s").setValue(40);
                                String s=(user.getDisplayName()).substring(0,3).toLowerCase();
                                refername=s;
                                Random r=new Random();
                                refername=refername+r.nextInt(999);
                                name.setText(user.getDisplayName());
                                email.setText(user.getEmail());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                                Log.d("Main activity", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    onSignedOutCleanup();
//                    Toast.makeText(getBaseContext(),"Sign-In to Win CASH",Toast.LENGTH_LONG).show();

                    // User is signed out
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .setTheme(R.style.LoginTheme)
                                    .setLogo(R.mipmap.rajputstatus_icon)
                                    .build(), RC_SIGN_IN);

                    Log.d("Main activity", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((phone.getText()).length() == 10) {
                    String g=refferal.getText().toString();
                    if(g.equals("")){
                        g="newinvited";
                    }
//                    DatabaseReference mData1 = FirebaseDatabase.getInstance().getReference().child("nn").push();
                    String emil= email.getText().toString();
                    DatabaseReference mData1 = mDatabaseReference.child("user").child(useruid);
                    DatabaseReference mData2 = mDatabaseReference.child("refer").child(refername);
                    DatabaseReference mData3 = mDatabaseReference.child("refer").child(g);

                    mData3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String uu = dataSnapshot.getValue().toString();
                                DatabaseReference mDat4 = mDatabaseReference.child("score/invite").child(uu).push();
                                Long l=System.currentTimeMillis();
                                DatabaseReference mDat5 = mDatabaseReference.child("score/deatail").child(uu).push();
                                mDat4.child("sc").setValue(2000);
                                mDat5.child("sc").setValue(2000);
                                mDat5.child("t").setValue(l);
                                mDat5.child("des").setValue("App refer");

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mData1.child("em").setValue(emil);
                    mData1.child("na").setValue(name.getText().toString());
                    mData1.child("mob").setValue(phone.getText().toString());
                    mData1.child("ref").setValue(g);
                    mData1.child("inv").setValue(refername);
                   mData1.child("rtd").setValue(bb);
                    mData2.setValue(useruid);


                    Intent i = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(getBaseContext(),"Write correct mob number",Toast.LENGTH_LONG).show();
                }
            }
        });





    }   private void onSignedInInitialize(String username) {

        Username = username;
    }

    private void onSignedOutCleanup() {
        Username = "ANONYMOUS";
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {

            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }}
    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }

}

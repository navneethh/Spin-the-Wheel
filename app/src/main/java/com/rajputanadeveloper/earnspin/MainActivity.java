package com.rajputanadeveloper.earnspin;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.renderscript.Long2;
import android.renderscript.Script;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements ForceUpdateChecker.OnUpdateNeededListener {
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String Username="anonymus",s;boolean ra=true;Long c=0L,sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    // User is signed in
                    onSignedInInitialize(user.getDisplayName());
                    Log.d("Main activity", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {

                    Intent i = new Intent(MainActivity.this, log_in.class);
                    startActivity(i);
                    onSignedOutCleanup();
                    Toast.makeText(getBaseContext(),"Sign-In to Win CASH",Toast.LENGTH_LONG).show();

//                    // User is signed out
//                    startActivityForResult(
//                            // Get an instance of AuthUI based on the default app
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setIsSmartLockEnabled(false)
//                                    .setAvailableProviders(
//                                            Arrays.asList(
//                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
//                                    .setTheme(R.style.LoginTheme)
//                                    .setLogo(R.mipmap.rajputstatus_icon)
//                                    .build(), RC_SIGN_IN);

                    Log.d("Main activity", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };



        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
    }
  public void openplaaa(View view ){
        Intent i = new Intent(MainActivity.this,pilyy.class);
                startActivity(i);
    }

    public void logout(View view ){
        mAuth.signOut();
        Intent i = new Intent(MainActivity.this, log_in.class);
startActivity(i);
        finish();

    }

    private void onSignedInInitialize(String username) {

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
    protected void onStart() {
        super.onStart();
       if(mAuth.getCurrentUser()==null) {
           mAuth.addAuthStateListener(mAuthListener);
       }else{
            final DatabaseReference use= FirebaseDatabase.getInstance().getReference().child("user").child(mAuth.getCurrentUser().getUid());
            use.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        s=dataSnapshot.child("inv").getValue().toString();
                        ra=(boolean) dataSnapshot.child("rtd").getValue();
                        culminate();
                        cheakspin();

                    }else{
                        Intent i=new Intent(getBaseContext(),log_in.class);
                        startActivity(i);
                        Toast.makeText(getBaseContext(),"First enter your Details",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void cheakspin() {
        final DatabaseReference tyt = FirebaseDatabase.getInstance().getReference().child("score/present").child(mAuth.getCurrentUser().getUid());
        tyt.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long lt = System.currentTimeMillis();

                if(dataSnapshot.child("s").exists()){

                }else {
                    tyt.child("s").setValue(40);
                    tyt.child("l").setValue(lt);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void culminate() {

        final DatabaseReference ttt = FirebaseDatabase.getInstance().getReference().child("score/invite").child(mAuth.getCurrentUser().getUid());
        final DatabaseReference tt1 = FirebaseDatabase.getInstance().getReference().child("score/present").child(mAuth.getCurrentUser().getUid());
        ttt.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        c = c + (Long) messageSnapshot.child("sc").getValue();
                    }
                    tt1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                sc = (Long) dataSnapshot.child("score").getValue();
                                sc = sc + c;
                                tt1.child("score").setValue(sc);
                                ttt.removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onUpdateNeeded(final String updateUrl) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("New version available")
                    .setMessage("Please, update app to new version to continue Earning.")
                    .setPositiveButton("Update",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    redirectStore(updateUrl);
                                    finish();
                                }
                            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                }).setNegativeButton("No, thanks",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).create();
            dialog.show();
        }

    public void openwallet(View view){
        Intent waIntent = new Intent(this,wallet.class);
        waIntent.putExtra("key",mAuth.getCurrentUser().getUid());
        startActivity(waIntent);
    }

    public void Doshare(final View view) {

        Intent waIntent = new Intent();
        waIntent.setAction(Intent.ACTION_SEND);

        waIntent.setType("text/plain");


        String text = "https://play.google.com/store/apps/details?id=com.rajputanadeveloper.earnspin \nDon't forget to enter my Refer code  '" + s + "' ";
        waIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(waIntent);
    }

public void rateearn( View view){
  if(!ra) {
      DatabaseReference use = FirebaseDatabase.getInstance().getReference().child("user").child(mAuth.getCurrentUser().getUid()).child("rtd");
      DatabaseReference use1 = FirebaseDatabase.getInstance().getReference().child("score/deatail").child(mAuth.getCurrentUser().getUid()).push();
      DatabaseReference use2 = FirebaseDatabase.getInstance().getReference().child("score/invite").child(mAuth.getCurrentUser().getUid()).push();
      Long l = 1000L;
      use1.child("sc").setValue(l);
      use2.child("sc").setValue(l);
      use1.child("t").setValue(System.currentTimeMillis());
      use1.child("des").setValue("App Rating");
      ra=true;
      use.setValue(true);

  }else{
      Snackbar.make(view, "Already reviewed", BaseTransientBottomBar.LENGTH_LONG).show();
  }
    try {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.rajputanadeveloper.earnspin")));
        return;
    } catch (ActivityNotFoundException e) {
        startActivity(new Intent("android.intent.action.VIEW",
                Uri.parse("https://play.google.com/store/apps/details?id=com.rajputanadeveloper.earnspin")));
        return;
    }

}


    public void noiti(View view){
        Intent i= new Intent(MainActivity.this,notifi.class);
        startActivity(i);
    }
    public void Referearn(final View view) {
        Snackbar.make(view, "Coming soon... keep inviting", BaseTransientBottomBar.LENGTH_LONG).show();
    }

        private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int i = item.getItemId();
        if (i == R.id.about) {
            openAbout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openAbout() {
        Intent i = new Intent(this, privacy.class);
        startActivity(i);
    }
}


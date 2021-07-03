package com.rajputanadeveloper.earnspin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Random;

public class pilyy extends AppCompatActivity {  ImageView spinImage;
    Button buton;
    TextView textView,spintext;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference, mDatabaseReferenced, mDatabaserferal;
    Random r;
    FirebaseAuth mAuth; Long score=0L,lastspin=System.currentTimeMillis(),scoreserver=0L;
    public InterstitialAd minterstitial; int degree =0 , degreeold= 0;int c=0;
    String uid;
    TextView timer;Long spinleft,s32=0l;
    private RewardedVideoAd mRewardedVideoAd;DatabaseReference mda;

    ProgressDialog progressBar;
 Long t1,t2,twenty=40l;
    private static final float Factor= 10f;
    CountDownTimer ctimer=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilyy);
        buton = (Button) findViewById(R.id.spinbutton);
        textView = (TextView) findViewById(R.id.resultss);
        spinImage= (ImageView) findViewById(R.id.spinimage);
        spintext= (TextView) findViewById(R.id.spi);
        timer= (TextView) findViewById(R.id.timer);
        AdView mAdView =(AdView) findViewById(R.id.adView);

        r= new Random();
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        mDatabaseReference=mFirebaseDatabase.getReference().child("score").child("present").child(uid);
        mDatabaseReferenced=mFirebaseDatabase.getReference().child("score").child("deatail").child(uid);
        mDatabaserferal=mFirebaseDatabase.getReference().child("score").child("referal").child("otheruid");
        progressBar= ProgressDialog.show(this,"Loading","Please wait");


        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.dismiss();
                if (dataSnapshot.exists()) {
                    Long temp=(Long)dataSnapshot.child("score").getValue();
                    spinleft=(Long)dataSnapshot.child("s").getValue();
                    s32=spinleft;
                    lastspin=(Long)dataSnapshot.child("l").getValue();
                    if (c == 0) {
                        Long tim= System.currentTimeMillis()-lastspin;
                        if (tim / 1000 > 120) {
                            long kk = tim / (1000 * 120) * 2;
                            if (spinleft >= twenty) {
                                mDatabaseReference.child("l").setValue(System.currentTimeMillis());
                                timer.setVisibility(View.GONE);
                                Log.e("eoor is here", "time to hide");
                            } else {
                                if ((spinleft + kk) < twenty) {
                                     s32 = spinleft + kk;
                                    if(s32<=0){
                                        buton.setVisibility(View.INVISIBLE);
                                    }
                                    updatesc(System.currentTimeMillis(), spinleft + kk);
                                } else {
                                    updatesc(System.currentTimeMillis(), twenty);
                                    timer.setVisibility(View.INVISIBLE);
                                }


                            }
                            tim = 000000L;
                        } else {
                            if (spinleft >= twenty) {
                                mDatabaseReference.child("l").setValue(System.currentTimeMillis());
                                timer.setVisibility(View.GONE);
                                Log.e("2nd error is here", "time to hide");
                            }
                            if(s32<=0){
                                buton.setVisibility(View.GONE);
                            }
                        }
                        startTimer(tim);

                        textView.setText(temp.toString());
                        c = 1;
                        spintext.setText(spinleft.toString());
                        score = temp;
                    }
                    scoreserver = temp;

                }
            }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });


        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewarded(RewardItem reward) {
                activity();

                // Reward the user.
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int errorCode) {

//                Toast.makeText(getBaseContext(), "Failed To Load video. remove this", Toast.LENGTH_SHORT).show();
                loadinter();
                minterstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        if (t2!=null){

                        minterstitial.show();}
                    }

                    @Override
                    public void onAdFailedToLoad(int i){
//                        Toast.makeText(getBaseContext(), "Failed To Load inter. remove this", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onAdClosed() {
                        activity();
                    }
                });


            }

            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoStarted() {
            }


        });







        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                buton.setClickable(false);
                degreeold = degree % 360;
                s32=s32-1;
                spintext.setText(Long.toString(s32));
                mDatabaseReference.child("s").setValue(s32);
                degree = r.nextInt(360) + 4500;
                final RotateAnimation rotate = new RotateAnimation(degreeold, degree,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(6400);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator(1.5f));
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mda = mDatabaseReferenced.push();
                        Long lt = System.currentTimeMillis();
                        String score1 = currentNumber(360 - (degree % 360));
                        Long ser = Long.parseLong(score1);


                       if (s32<=0){
                           buton.setVisibility(View.GONE);

                       }else{
                           buton.setClickable(true);
                       }


                       if(ser!=0) {
                           AlertDialog dialog = new AlertDialog.Builder(pilyy.this)
                                   .setTitle("You Won")
                                   .setMessage("Claim your Reward " + score1 + " coins")
                                   .setPositiveButton("Claim",
                                           new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {

                                                   if (mRewardedVideoAd.isLoaded()) {
                                                       mRewardedVideoAd.show();
                                                   } else {
                                                       loadRewardedVideoAd();
                                                   }
                                               }
                                           }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                                       @Override
                                       public void onCancel(DialogInterface dialog) {

                                       }
                                   }).setNegativeButton("No, thanks",
                                           new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {

                                               }
                                           }).create();
                           dialog.show();

                           t1 = lt;
                           t2 = ser;
                       }else{
                           Snackbar.make(view, "Zero points! Try Again", BaseTransientBottomBar.LENGTH_LONG).show();

                       }

//                       now writing data for every spin with time


//

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                spinImage.startAnimation(rotate);
            }
        });
        loadRewardedVideoAd();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest); }

    private void updatesc(long l, long sp) {
        s32=sp;
        mDatabaseReference.child("l").setValue(System.currentTimeMillis());
        mDatabaseReference.child("s").setValue(sp);
        spintext.setText(String.valueOf(sp));
        spinleft=sp;

    }

    private void activity() {
        scoreserver=scoreserver+t2;
        mDatabaseReference.child("score").setValue(scoreserver);

        score = score + t2;
        textView.setText(score.toString());
//                       now writing data for every spin with time
        mda.child("t").setValue(t1);
        mda.child("sc").setValue(t2);
        mda.child("des").setValue("Spin");
    }

    private String currentNumber (int degrees){
        String text ="";


        if(degrees >= (Factor*0)&& degrees < (Factor*2)){
            text="0";
        }

        if(degrees >= (Factor*2)&& degrees < (Factor*4)){
            text="100";
        }


        if(degrees >= (Factor*4)&& degrees < (Factor*6)){
            text="4";
        }
        if(degrees >= (Factor*6)&& degrees < (Factor*8)){
            text="200";
        }
        if(degrees >= (Factor*8)&& degrees < (Factor*10)){
            text="5";
        }
        if(degrees >= (Factor*10)&& degrees < (Factor*12)){
            text="6";
        }
        if(degrees >= (Factor*12)&& degrees < (Factor*14)){
            text="300";
        }
        if(degrees >= (Factor*14)&& degrees < (Factor*16)){
            text="7";
        }
        if(degrees >= (Factor*16)&& degrees < (Factor*18)){
            text="100";
        }
        if (degrees >= (Factor * 18) && degrees < (Factor * 20)) {
            text = "0";
        }
        if (degrees >= (Factor * 20) && degrees < (Factor * 22)) {
            text = "100";
        }
        if (degrees >= (Factor * 22) && degrees < (Factor * 24)) {
            text = "9";
        }
        if (degrees >= (Factor * 24) && degrees < (Factor * 26)) {
            text = "0";
        }
        if (degrees >= (Factor * 26) && degrees < ((Factor * 28) + 2)) {
            text = "100";
        }
        if (degrees >= ((Factor * 28) + 2) && degrees < ((Factor * 30) - 2)) {
            text = "500";
        }
        if (degrees >= ((Factor * 30) - 2) && degrees < (Factor * 32)) {
            text = "1";
        }
        if (degrees >= (Factor * 32) && degrees < (Factor * 34)) {
            text = "2";
        }
        if (degrees >= (Factor * 34) && degrees < (Factor * 36)) {
            text = "100";
        }


        return text;

    }

    private void loadRewardedVideoAd() {
//           mRewardedVideoAd.loadAd("ca-app-pub-5419949669621085/6924555449",
        mRewardedVideoAd.loadAd("ca-app-pub-5419949669621085/6924555449", new AdRequest.Builder().build());

//       mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",   TESTID
//                new AdRequest.Builder().build());

    }
    void startTimer(Long tim){
       ctimer= new CountDownTimer(120000-tim,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                long li=(millisUntilFinished/1000);

                String min=String.valueOf(li/60);
                String sec=String.valueOf(li%60);
                String h= min+":"+sec;
                timer.setText(h);

            }
            @Override
            public void onFinish() {
            }
        };
        ctimer.start();
    }
    void cancelTimer(){
        if(ctimer!=null){
            ctimer.cancel();
    }}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
    public void showToast(View view){
        Toast.makeText(pilyy.this,"Get 2 free Spin every 2 minutes",Toast.LENGTH_SHORT).show();
    }

    void loadinter() {
        minterstitial = new InterstitialAd(getBaseContext());
//     minterstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");  TESTID

        minterstitial.setAdUnitId("ca-app-pub-5419949669621085/6683391605");
//     minterstitial.setAdUnitId("ca-app-pub-5419949669621085/6683391605");
        AdRequest ar = new AdRequest.Builder().build();
        minterstitial.loadAd(ar);
    }
}

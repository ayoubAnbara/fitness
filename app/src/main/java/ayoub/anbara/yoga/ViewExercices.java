package ayoub.anbara.yoga;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import ayoub.anbara.yoga.Database.YogaDB;
import ayoub.anbara.yoga.Utils.Common;
import es.dmoral.toasty.Toasty;

public class ViewExercices extends AppCompatActivity {

    int image_id;
    String name;
    TextView timer, title;
    ImageView detail_image;
    Button btnStart;
    boolean isRunning = false;
    YogaDB yogaDB;
    private InterstitialAd mInterstitialAd;
    private CountDownTimer countDownTimer;
    private  MediaPlayer sound_fin;
private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_view_exercices);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
       sound_fin = MediaPlayer.create(this, R.raw.sound_fin);
       sound_fin.setVolume(1f,1f);
        yogaDB = new YogaDB(this);

         prefs = getSharedPreferences(
                 MainActivity.preference_counterAds, Context.MODE_PRIVATE);

        timer = findViewById(R.id.timer);
        title = findViewById(R.id.title);
        detail_image = findViewById(R.id.detail_img);
        btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    btnStart.setText(R.string.DONE);
                    int timeLimit = 0;
                    if (yogaDB.getSettingMode() == 0)
                        timeLimit = Common.TIME_LIMIT_EASY;
                    else if (yogaDB.getSettingMode() == 1)
                        timeLimit = Common.TIME_LIMIT_MEDIUM;
                    else if (yogaDB.getSettingMode() == 2)
                        timeLimit = Common.TIME_LIMIT_HARD;

                    countDownTimer=new CountDownTimer(timeLimit, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            timer.setText("" + millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {

                            //Toast.makeText(ViewExercices.this, getString(R.string.finish_small_capital), Toast.LENGTH_SHORT).show();
                            Toasty.success(ViewExercices.this, getString(R.string.finish_small_capital), Toast.LENGTH_SHORT, true).show();
                            if (sound_fin!=null)
                                sound_fin.start();

                            int counterShowAds= prefs.getInt(MainActivity.preference_counterAds_key,3);
                            counterShowAds++;
                            SharedPreferences.Editor editor=prefs.edit();
                            if (3<=counterShowAds){
                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                    counterShowAds=0;
                              //      Toast.makeText(ViewExercices.this, "show", Toast.LENGTH_SHORT).show();
                                }
                            }
                            editor.putInt(MainActivity.preference_counterAds_key,counterShowAds);
                            editor.apply();

                            finish();

                        }
                    }.start();
                } else {
                    //Toast.makeText(ViewExercices.this, getString(R.string.finish_small_capital), Toast.LENGTH_SHORT).show();
                    Toasty.success(ViewExercices.this, getString(R.string.finish_small_capital), Toast.LENGTH_SHORT, true).show();

                    int counterShowAds= prefs.getInt(MainActivity.preference_counterAds_key,3);
                    counterShowAds++;
                    SharedPreferences.Editor editor=prefs.edit();
                    if (3<=counterShowAds){
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                            counterShowAds=0;
                      //      Toast.makeText(ViewExercices.this, "show", Toast.LENGTH_SHORT).show();

                        }
                    }
                    editor.putInt(MainActivity.preference_counterAds_key,counterShowAds);
                    editor.apply();

                    finish();

                }
                isRunning = !isRunning;
            }
        });
        timer.setText("");


        if (getIntent() != null) {
            image_id = getIntent().getIntExtra("image_id", -1);
            name = getIntent().getStringExtra("name");

            detail_image.setImageResource(image_id);
            title.setText(name);
        }
    }

    @Override
    protected void onPause() {
        if (countDownTimer!=null)
            countDownTimer.cancel();
        finish();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sound_fin != null) {
            sound_fin.release();
            sound_fin = null;
        }
    }
}

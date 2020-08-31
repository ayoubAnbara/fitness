package ayoub.anbara.yoga;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import ayoub.anbara.yoga.Database.YogaDB;
import ayoub.anbara.yoga.Model.Exercices;
import ayoub.anbara.yoga.Utils.Common;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class Daily_Trining extends AppCompatActivity {
    Button btnStart;
    ImageView image_ex;
    TextView txtGetReady, textCountDown, txt_timer, ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;
    int ex_id = 0, limit_time = 0;
    YogaDB yogaDB;
    List<Exercices> list = new ArrayList<>();
    private InterstitialAd mInterstitialAd;
    private  boolean isFinished;
    private MediaPlayer sound_fin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_daily__trining);
        initData();
        initOther();
        progressBar.setMax(list.size());
        setExercicesInformation(ex_id);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getText().toString().toLowerCase().equals(getString(R.string.start).toLowerCase())) {
                    showGetReady();
                    btnStart.setText(R.string.done);
                } else if (btnStart.getText().toString().toLowerCase().equals(getString(R.string.done).toLowerCase())) {
                    if (yogaDB.getSettingMode() == 0)
                        exercicesEasyModeCountDown.cancel();
                    else if (yogaDB.getSettingMode() == 1)
                        exercicesMediumModeCountDown.cancel();
                    else if (yogaDB.getSettingMode() == 2)
                        exercicesHardModeCountDown.cancel();


                    resetTimeCountDown.cancel();
                    if (ex_id < list.size()) {
                        showResetTime();
                        ex_id++;//set new exercice
                        progressBar.setProgress(ex_id);
                        txt_timer.setText("");
                    } else showFinished();
                } else {
                    if (yogaDB.getSettingMode() == 0)
                        exercicesEasyModeCountDown.cancel();
                    else if (yogaDB.getSettingMode() == 1)
                        exercicesMediumModeCountDown.cancel();
                    else if (yogaDB.getSettingMode() == 2)
                        exercicesHardModeCountDown.cancel();
                    resetTimeCountDown.cancel();
                    if (ex_id < list.size())
                        setExercicesInformation(ex_id);
                    else showFinished();
                }

            }
        });
    }

    private void initOther() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9059580756298090/5839801307");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        sound_fin = MediaPlayer.create(this, R.raw.sound_fin);
        yogaDB = new YogaDB(this);
        btnStart = findViewById(R.id.btnStart);
        textCountDown = findViewById(R.id.textCountDown);
        txt_timer = findViewById(R.id.timer);
        image_ex = findViewById(R.id.detail_img);
        layoutGetReady = findViewById(R.id.layout_get_ready);
        progressBar = (MaterialProgressBar) findViewById(R.id.progress_bar);
        ex_name = findViewById(R.id.title);
        txtGetReady = findViewById(R.id.textGetReady);
    }

    private void showResetTime() {
        image_ex.setVisibility(View.INVISIBLE);
        txt_timer.setVisibility(View.INVISIBLE);
        btnStart.setText(R.string.skip);
        btnStart.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        resetTimeCountDown.start();

        txtGetReady.setText(R.string.reset_time);

    }

    private void showFinished() {
        image_ex.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txt_timer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);
        txtGetReady.setText(R.string.finished);
        textCountDown.setText(getString(R.string.congratulation));
        textCountDown.setTextSize(20);
        yogaDB.saveDay("" + Calendar.getInstance().getTimeInMillis());
    isFinished=true;
    }

    private void showGetReady() {
        image_ex.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txt_timer.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText(R.string.get_ready);
        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                textCountDown.setText("" + (l - 1000) / 1000);
            }

            @Override
            public void onFinish() {

                showExercices();
            }
        }.start();
    }

    private void showExercices() {
        if (ex_id < list.size()) {
            image_ex.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);
            if (yogaDB.getSettingMode() == 0)
                exercicesEasyModeCountDown.start();
            else if (yogaDB.getSettingMode() == 1)
                exercicesMediumModeCountDown.start();
            else if (yogaDB.getSettingMode() == 2)
                exercicesHardModeCountDown.start();

            //set data
            image_ex.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());
        } else showFinished();
    }
    private void setExercicesInformation(int id) {
        if (id == 11) {//my modification
           showFinished();
            return;

        }
        image_ex.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText(getString(R.string.start));
        image_ex.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txt_timer.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.INVISIBLE);
    }
    private void initData() {
        list.add(new Exercices(R.drawable.easy_pose, getString(R.string.easy_pose)));
        list.add(new Exercices(R.drawable.cobra_pose, getString(R.string.cobra_pose)));
        list.add(new Exercices(R.drawable.downward_facing_dog, getString(R.string.downward_facing_dog)));
        list.add(new Exercices(R.drawable.boat_pose, getString(R.string.boat_pose)));
        list.add(new Exercices(R.drawable.half_pigeon, getString(R.string.half_pigeon)));
        list.add(new Exercices(R.drawable.low_lunge, getString(R.string.low_lunge)));
        list.add(new Exercices(R.drawable.upward_bow, getString(R.string.upward_bow)));
        list.add(new Exercices(R.drawable.crescent_lunge, getString(R.string.crescent_lunge)));
        list.add(new Exercices(R.drawable.warrior_pose, getString(R.string.warrior_pose)));
        list.add(new Exercices(R.drawable.bow_pose, getString(R.string.bow_pose)));
        list.add(new Exercices(R.drawable.warrior_pose_2, getString(R.string.warrior_pose2)));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isFinished && mInterstitialAd.isLoaded())
            mInterstitialAd.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sound_fin != null) {
            sound_fin.release();
            sound_fin = null;
        }
    }

    CountDownTimer exercicesEasyModeCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY, 1000) {
        @Override
        public void onTick(long l) {
            txt_timer.setText("" + (l / 1000));
        }

        @Override
        public void onFinish() {
            if (sound_fin!=null)
                sound_fin.start();
            if (ex_id < list.size()) {
                ex_id++;
                progressBar.setProgress(ex_id);
                txt_timer.setText("");
                setExercicesInformation(ex_id);
                btnStart.setText(R.string.start);

            } else {
                showFinished();
            }

        }
    };
    CountDownTimer exercicesMediumModeCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY, 1000) {
        @Override
        public void onTick(long l) {
            txt_timer.setText("" + (l / 1000));
        }

        @Override
        public void onFinish() {
            if (sound_fin!=null)
                sound_fin.start();
            if (ex_id < list.size()) {
                ex_id++;
//                if (ex_id==11){
//                    showFinished();
//                    finish();}
                progressBar.setProgress(ex_id);
                txt_timer.setText("");
                setExercicesInformation(ex_id);
                btnStart.setText(getString(R.string.start));

            } else {
                showFinished();
            }

        }
    };
    CountDownTimer exercicesHardModeCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY, 1000) {
        @Override
        public void onTick(long l) {
            txt_timer.setText("" + (l / 1000));
        }

        @Override
        public void onFinish() {
            if (sound_fin!=null)
                sound_fin.start();
            if (ex_id < list.size()) {
                ex_id++;
                progressBar.setProgress(ex_id);
                txt_timer.setText("");
                setExercicesInformation(ex_id);
                btnStart.setText(getString(R.string.start));

            } else {
                showFinished();
            }

        }
    };


    CountDownTimer resetTimeCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long l) {

            textCountDown.setText("" + (l / 1000));
        }

        @Override
        public void onFinish() {
            setExercicesInformation(ex_id);
            showExercices();


        }
    };

}

package ayoub.anbara.yoga;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TimePicker;
import android.widget.Toast;
//import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Calendar;
import java.util.Date;


import ayoub.anbara.yoga.Database.YogaDB;
import es.dmoral.toasty.Toasty;

public class SettingPage extends AppCompatActivity {

    Button btnSave;
    RadioButton rdiEasy, rdiMedium, rdiHard;
    RadioGroup radioGroup;
    YogaDB yogaDB;
    SwitchCompat switchAlarm;
    TimePicker timePicker;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_setting_page);
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

        btnSave = findViewById(R.id.btn_save);
        rdiEasy = findViewById(R.id.rdiEasy);
        rdiMedium = findViewById(R.id.rdiMedium);
        rdiHard = findViewById(R.id.rdiHard);
        radioGroup = findViewById(R.id.rdio_button);
        switchAlarm = findViewById(R.id.switchAlarm);
        timePicker = findViewById(R.id.timePicker);
        yogaDB = new YogaDB(this);


        int mode = yogaDB.getSettingMode();
        setRadioButtonMode(mode);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkOutMode();
                saveAlarm(switchAlarm.isChecked());
                //Toast.makeText(SettingPage.this, R.string.saved, Toast.LENGTH_SHORT).show();
                Toasty.success(SettingPage.this, R.string.saved, Toast.LENGTH_SHORT, true).show();
                finish();
            }
        });
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    timePicker.setBackgroundColor(getResources().getColor(R.color.colorControlActivated));

                else timePicker.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });


    }

    private void saveAlarm(boolean checked) {
        if (checked) {

            f();
        }
//        if (false) {
//            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            Intent intent;
//            PendingIntent pendingIntent;
//            intent = new Intent(SettingPage.this, AlarmNotificationReceiver.class);
//            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//            //set time to alarm
//            Calendar calendar = Calendar.getInstance();
//            Date toDay = Calendar.getInstance().getTime();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                calendar.set(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK), timePicker.getHour(), timePicker.getMinute());
//            } else {
//                Toast.makeText(this, "ghghg", Toast.LENGTH_SHORT).show();
//                //calendar.set(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
//                calendar.set(toDay.getYear(), toDay.getMonth(), toDay.getDay(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
//            }
//
//
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                calendar.set(calendar.get(Calendar.YEAR) - 1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_WEEK),timePicker.getHour(), timePicker.getMinute());
////            }else {
////                calendar.set(calendar.get(Calendar.YEAR) - 1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_WEEK),timePicker.getCurrentHour(), timePicker.getCurrentMinute());
////                calendar.set(toDay.getYear(), toDay.getMonth(), toDay.getDay(), timePicker.getHour(), timePicker.getMinute());
////            }
//
//            //خاصك دير فيرسيو خرين
//            manager.setRepeating(AlarmManager.RTC_WAKEUP
//                    , calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY
//                    , pendingIntent);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                Log.d("DEBUG", "Alarm will wake at: " + timePicker.getHour() + " " + timePicker.getMinute());
//            }
//
        //}
        else {


            //cancel alarm
            Intent intent = new Intent(SettingPage.this, AlarmNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (manager != null) {
                manager.cancel(pendingIntent);
            }
        }

    }

    private void saveWorkOutMode() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == rdiEasy.getId())
            yogaDB.saveSettingMode(0);
        else if (selectedId == rdiMedium.getId())
            yogaDB.saveSettingMode(1);
        else if (selectedId == rdiHard.getId())
            yogaDB.saveSettingMode(2);

    }

    private void setRadioButtonMode(int mode) {
        if (mode == 0)
            radioGroup.check(R.id.rdiEasy);
        else if (mode == 1)
            radioGroup.check(R.id.rdiMedium);
        else if (mode == 2)
            radioGroup.check(R.id.rdiHard);

    }

    private void f() {
        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(SettingPage.this, AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int Hour, Minute;

        if (Build.VERSION.SDK_INT >= 23) {

            Hour = timePicker.getHour();
            Minute = timePicker.getMinute();

        } else {

            Minute = timePicker.getCurrentMinute();
            Hour = timePicker.getCurrentHour();
        }


        Date dat = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY, Hour);
        cal_alarm.set(Calendar.MINUTE, Minute);
        cal_alarm.set(Calendar.SECOND, 10);
        if (cal_alarm.before(cal_now)) {
            cal_alarm.add(Calendar.DATE, 1);
        }


        if (manager != null) {
            manager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
        }
    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        super.onBackPressed();

    }
}


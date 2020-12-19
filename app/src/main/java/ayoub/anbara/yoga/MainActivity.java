package ayoub.anbara.yoga;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.kennyc.bottomsheet.BottomSheetMenuDialogFragment;

import es.dmoral.toasty.Toasty;
import guy4444.smartrate.SmartRate;

public class MainActivity extends AppCompatActivity {
    Button btn_exercice, btn_setting, btn_calendar;
    ImageView btnTraining;
     private InterstitialAd mInterstitialAd;

    public static final String preference_counterAds = "showIntertiatialAds";
    public static final String preference_counterAds_key = "counterShowAds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        // AppLovinSdk.initializeSdk(getApplicationContext());


        btn_calendar = findViewById(R.id.btn_calendar);
        btn_exercice = findViewById(R.id.btn_exercice);
        btn_setting = findViewById(R.id.btn_setting);
        btnTraining = findViewById(R.id.btn_training);
        btn_calendar = findViewById(R.id.btn_calendar);
// Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
          // MobileAds.initialize(this, "ca-app-pub-9059580756298090~2573551700");

        MobileAds.initialize(this);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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

        /*ImageView imageView = findViewById(R.id.adView);
        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MyWebView.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
        );*/

        btn_exercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListExercices.class);
                startActivity(intent);
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingPage.class);
                startActivity(intent);
            }
        });
        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Daily_Trining.class);
                startActivity(intent);


            }
        });
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calendar.class);
                startActivity(intent);


            }
        });

        SharedPreferences prefs = getSharedPreferences(
                preference_counterAds, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(preference_counterAds_key, 0); // initialize counter
        editor.apply();

        showRateUs();
    }


    public void more(View view) {
        new BottomSheetMenuDialogFragment.Builder(this)
                .setSheet(R.menu.menu)
                //.setTitle("menu")
                .setListener(new BottomSheetListener() {
                    @Override
                    public void onSheetShown(BottomSheetMenuDialogFragment bottomSheetMenuDialogFragment, Object o) {

                    }

                    @Override
                    public void onSheetItemSelected(BottomSheetMenuDialogFragment bottomSheetMenuDialogFragment, MenuItem menuItem, Object o) {
                        switch (menuItem.getItemId()) {
                            case R.id.rate:
                                rate();
                                break;
                            case R.id.share:
                                share();
                                break;
                            case R.id.moreApp:
                                moreApp(MainActivity.this);
                                break;
                            case R.id.privacy_policy:
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/yogaathome/home"));
                                startActivity(browserIntent);
                                break;

                        }
                    }

                    @Override
                    public void onSheetDismissed(BottomSheetMenuDialogFragment bottomSheetMenuDialogFragment, Object o, int i) {

                    }
                })
                // .setObject(myObject)
                .show(getSupportFragmentManager());


    }

    private void rate() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void share() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        String txt = getString(R.string.text_to_share) + " " + "https://play.google.com/store/apps/details?id=ayoub.anbara.yoga";
        shareIntent.putExtra(Intent.EXTRA_TEXT, txt);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_app_intent)));


    }

    private void moreApp(Context c) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?q=pub:" + "developer 4you"));
        if (!MyStartActivity(intent, c)) {
            intent.setData(Uri.parse("http://play.google.com/store/search?q=pub:" + "developer 4you"));
            if (!MyStartActivity(intent, c)) {
                // Toast.makeText(MainActivity.this,getString(R.string.dowloand_play_store), Toast.LENGTH_SHORT).show();
                Toasty.warning(MainActivity.this, getString(R.string.dowloand_play_store), Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    public static boolean MyStartActivity(Intent aIntent, Context c) {
        try {
            c.startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        // else  showRateUs();


        //finish();
    }

    private void showRateUs() {
        // For continual calls -
        SmartRate.Rate(MainActivity.this
                , getString(R.string.title_ratingDialog)
                , "Tell others what you think about this app"
                , "Continue"
                , "Please take a moment and rate us on Google Play"
                , "click here"
                , getString(R.string.later)
                , getString(R.string.never)
                , "Cancel"
                , "Thanks for the feedback"
                , Color.parseColor("#2196F3")
                , 3
                , 40
                , 50
        );
    }
}


package ayoub.anbara.yoga;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
//import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import ayoub.anbara.yoga.ratingDialog.GlobalUtils;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    Button btn_exercice, btn_setting, btn_calendar;
    ImageView btnTraining;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        mainActivity=this;
        btn_calendar = findViewById(R.id.btn_calendar);
        btn_exercice = findViewById(R.id.btn_exercice);
        btn_setting = findViewById(R.id.btn_setting);
        btnTraining = findViewById(R.id.btn_training);
        btn_calendar = findViewById(R.id.btn_calendar);
// Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-9059580756298090~2573551700");
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

    }


    public void more(View view) {
        new BottomSheet.Builder(this).sheet(R.menu.menu).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
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
        }).show();

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
    static MainActivity mainActivity;

    public static MainActivity getInstance() {
        return mainActivity;
    }

    public boolean isConnected(Context context) {
// exist deprecation method
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = null;
        if (cm != null) {
            netinfo = cm.getActiveNetworkInfo();
        }

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null &&
                    wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }
    @Override
    public void onBackPressed() {
        SharedPreferences preferences = getSharedPreferences(GlobalUtils.NAME_PREFERENCE_DIALOG_RATING, 0);
        if ((!preferences.getBoolean(GlobalUtils.KEY_IS_NEVER, false)) && isConnected(this))
            GlobalUtils.showDialog(this);
        else {
            super.onBackPressed();
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
        //finish();
    }
}


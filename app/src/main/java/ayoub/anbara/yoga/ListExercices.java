package ayoub.anbara.yoga;

import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import ayoub.anbara.yoga.Adapter.RecycleViewAdapter;
import ayoub.anbara.yoga.Model.Exercices;

public class ListExercices extends AppCompatActivity {

    List<Exercices> exercicesList=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;
  //  private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_exercices);
        initData();
        recyclerView=findViewById(R.id.list_ex);
        adapter=new RecycleViewAdapter(exercicesList,getBaseContext());
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                // Load the next interstitial.
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//            }
//
//        });

    }

    private void initData() {
        exercicesList.add(new Exercices(R.drawable.a1, getString(R.string.easy_pose)+" 1"));
        exercicesList.add(new Exercices(R.drawable.a2, getString(R.string.easy_pose)+" 2"));
        exercicesList.add(new Exercices(R.drawable.easy_pose, getString(R.string.easy_pose)+" 3"));


        exercicesList.add(new Exercices(R.drawable.b1, "position 2_1"));
        exercicesList.add(new Exercices(R.drawable.b2, "position 2_2"));
        exercicesList.add(new Exercices(R.drawable.b3, "position 2_3"));

        exercicesList.add(new Exercices(R.drawable.bow_pose, getString(R.string.bow_pose)));



        exercicesList.add(new Exercices(R.drawable.cobra_pose, getString(R.string.cobra_pose)));
        exercicesList.add(new Exercices(R.drawable.downward_facing_dog, getString(R.string.downward_facing_dog)));
        exercicesList.add(new Exercices(R.drawable.boat_pose, getString(R.string.boat_pose)));
        exercicesList.add(new Exercices(R.drawable.half_pigeon, getString(R.string.half_pigeon)));
        exercicesList.add(new Exercices(R.drawable.low_lunge, getString(R.string.low_lunge)));
        exercicesList.add(new Exercices(R.drawable.upward_bow, getString(R.string.upward_bow)));
        exercicesList.add(new Exercices(R.drawable.crescent_lunge, getString(R.string.crescent_lunge)));

        exercicesList.add(new Exercices(R.drawable.warrior_pose, getString(R.string.warrior_pose)));
        exercicesList.add(new Exercices(R.drawable.d1, ""));


        exercicesList.add(new Exercices(R.drawable.warrior_pose_2, getString(R.string.warrior_pose2)));


        exercicesList.add(new Exercices(R.drawable.f, ""));
        exercicesList.add(new Exercices(R.drawable.f2, ""));
        exercicesList.add(new Exercices(R.drawable.f3, ""));
        exercicesList.add(new Exercices(R.drawable.f4, ""));



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
    }
}

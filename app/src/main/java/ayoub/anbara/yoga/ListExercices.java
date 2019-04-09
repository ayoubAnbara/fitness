package ayoub.anbara.yoga;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ayoub.anbara.yoga.Adapter.RecycleViewAdapter;
import ayoub.anbara.yoga.Model.Exercices;

public class ListExercices extends AppCompatActivity {

    List<Exercices> exercicesList=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecycleViewAdapter adapter;


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


    }

    private void initData() {
        exercicesList.add(new Exercices(R.drawable.easy_pose, getString(R.string.easy_pose)));
        exercicesList.add(new Exercices(R.drawable.cobra_pose, getString(R.string.cobra_pose)));
        exercicesList.add(new Exercices(R.drawable.downward_facing_dog, getString(R.string.downward_facing_dog)));
        exercicesList.add(new Exercices(R.drawable.boat_pose, getString(R.string.boat_pose)));
        exercicesList.add(new Exercices(R.drawable.half_pigeon, getString(R.string.half_pigeon)));
        exercicesList.add(new Exercices(R.drawable.low_lunge, getString(R.string.low_lunge)));
        exercicesList.add(new Exercices(R.drawable.upward_bow, getString(R.string.upward_bow)));
        exercicesList.add(new Exercices(R.drawable.crescent_lunge, getString(R.string.crescent_lunge)));
        exercicesList.add(new Exercices(R.drawable.warrior_pose, getString(R.string.warrior_pose)));
        exercicesList.add(new Exercices(R.drawable.bow_pose, getString(R.string.bow_pose)));
        exercicesList.add(new Exercices(R.drawable.warrior_pose_2, getString(R.string.warrior_pose2)));



    }
}

package ayoub.anbara.yoga.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.AdView;

import java.util.List;

import ayoub.anbara.yoga.Interface.ItemClickListener;
import ayoub.anbara.yoga.Model.Exercices;
import ayoub.anbara.yoga.R;
import ayoub.anbara.yoga.ViewExercices;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private List<Exercices> exercicesList;
    private Context context;


    public RecycleViewAdapter(List<Exercices> exercicesList, Context context) {
        this.exercicesList = exercicesList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == 1) {
            // show banner admob
            View v3 = inflater.inflate(R.layout.item_banner_admob_ads, viewGroup, false);
            return new MyAdViewHolder(v3);
        } else {

            View itemView = inflater.inflate(R.layout.item_exercices, viewGroup, false);
            return new MyViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 10 == 0)
            return 1;  // to show banner
        return 0;      // to show normal item
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder MyViewHolder, int position) {
        if (getItemViewType(position) == 1) return;
        MyViewHolder.image.setImageResource(exercicesList.get(position).getImage_id());
        MyViewHolder.text.setText(exercicesList.get(position).getName());
        MyViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Toast.makeText(context, "click to "+exercicesList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ViewExercices.class);
                intent.putExtra("image_id", exercicesList.get(position).getImage_id());
                intent.putExtra("name", exercicesList.get(position).getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return exercicesList.size();
    }


class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView image;
    public TextView text;
    private ItemClickListener itemClickListener;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.ex_img);
        text = itemView.findViewById(R.id.ex_name);
        itemView.setOnClickListener(this);


    }

    public MyViewHolder(@NonNull View itemView, int i) {
        super(itemView);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());

    }
}

class MyAdViewHolder extends MyViewHolder {
//    private CardView cardViewContainer;
    private AdView adViewIntoRecycle;
    MyAdViewHolder(View view) {
        super(view, 5);
         //cardViewContainer = view.findViewById(R.id.native_ad_container);

        adViewIntoRecycle = view.findViewById(R.id.adViewIntoRecycleView);
        //adView.setAdSize(AdSize.SMART_BANNER);
//        adViewIntoRecycle.setAdSize(AdSize.LARGE_BANNER);
//        adViewIntoRecycle.setAdUnitId(context.getString(R.string.banner_id));
        adViewIntoRecycle.loadAd(new AdRequest.Builder().build());

    }
}}
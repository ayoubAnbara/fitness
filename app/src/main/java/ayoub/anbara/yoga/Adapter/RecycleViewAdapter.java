package ayoub.anbara.yoga.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;

import ayoub.anbara.yoga.Interface.ItemClickListener;
import ayoub.anbara.yoga.Model.Exercices;
import ayoub.anbara.yoga.R;
import ayoub.anbara.yoga.ViewExercices;

class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView image;
    public TextView text;
    private ItemClickListener itemClickListener;


    public RecycleViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.ex_img);
        text = itemView.findViewById(R.id.ex_name);
        itemView.setOnClickListener(this);


    }
    public RecycleViewHolder(@NonNull View itemView,int i) {
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
class MyAdViewHolder extends RecycleViewHolder {

    MyAdViewHolder(View view) {
        super(view, 5);
    }
}

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewHolder> {
    private List<Exercices> exercicesList;
    private Context context;

    public RecycleViewAdapter(List<Exercices> exercicesList, Context context) {
        this.exercicesList = exercicesList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == 1) {
            // show banner admob
            View v3 = inflater.inflate(R.layout.item_banner_admob_ads, viewGroup, false);
            AdView adView = new AdView(context); //ads admob
            //adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdSize(AdSize.LARGE_BANNER);
            adView.setAdUnitId(context.getString(R.string.banner_id));
            CardView cardViewContainer = v3.findViewById(R.id.native_ad_container);
            adView.loadAd(new AdRequest.Builder().build());
            cardViewContainer.addView(adView);
            return new MyAdViewHolder(v3);
        } else {

        View itemView = inflater.inflate(R.layout.item_exercices, viewGroup, false);
        return new RecycleViewHolder(itemView);
    }}
    @Override
    public int getItemViewType(int position) {
        if (position % 5 == 0)
            return 1;  // to show banner
        return 0;      // to show normal item
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int position) {
        if (getItemViewType(position) == 1) return;
        recycleViewHolder.image.setImageResource(exercicesList.get(position).getImage_id());
        recycleViewHolder.text.setText(exercicesList.get(position).getName());
        recycleViewHolder.setItemClickListener(new ItemClickListener() {
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
}

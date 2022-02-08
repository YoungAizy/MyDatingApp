package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.models.NearestPeople;

import java.util.List;


public class NearestPeopleAdapter extends RecyclerView.Adapter<NearestPeopleAdapter.MatchesHolder> {
    private Context context;
    private List<NearestPeople> matches;
    ClickListener listener;


    public NearestPeopleAdapter(Context context, List<NearestPeople> matches, ClickListener listener) {
        this.context = context;
        this.matches = matches;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_nearest_people, viewGroup, false);
        return new MatchesHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesHolder holder, int position) {
        NearestPeople match = matches.get(position);
        holder.name.setText(match.getName());
        holder.distance.setText(match.getDistance());

        if (match.getUrl() != null){

            Glide.with(context)
                    .load(match.getUrl()).override(360, 360).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .circleCrop()
                    .into(holder.img);
        }else {
            holder.img.setImageResource(match.getImgId());
        }

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }


    static class MatchesHolder extends RecyclerView.ViewHolder{

        private RoundedImageView img;
        private TextView name, distance;
        ClickListener clickListener;

        MatchesHolder(View itemView, final ClickListener listener) {
            super(itemView);
            img = itemView.findViewById(R.id.nearest_image);
            name = itemView.findViewById(R.id.nearest_name);
            distance = itemView.findViewById(R.id.nearest_distance);
            clickListener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener{
        void onClick(int position);
    }
}

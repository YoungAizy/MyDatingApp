package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.models.NewPeople;

import java.util.List;


public class NewPeopleAdapter extends RecyclerView.Adapter<NewPeopleAdapter.MatchesHolder> {
    private Context context;
    private List<NewPeople> matches;
    ClickListener listener;


    public NewPeopleAdapter(Context context, List<NewPeople> matches, ClickListener listener) {
        this.context = context;
        this.matches = matches;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_newest_people, viewGroup, false);
        return new MatchesHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesHolder holder, int position) {
        NewPeople match = matches.get(position);
        holder.name.setText(match.getName());
        holder.img.setImageResource(match.getImgId());

        /**Glide.with(context)
                .load(match.getUrl()).override(360, 360).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(holder.img);*/


    }

    @Override
    public int getItemCount() {
        return matches.size();
    }


    static class MatchesHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView name;
        ClickListener listener;

        MatchesHolder(View itemView, final ClickListener listener) {
            super(itemView);
            img = itemView.findViewById(R.id.newest_image);
            name = itemView.findViewById(R.id.new_people_name);
            this.listener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface ClickListener{
        void onClick(int t);
    }
}

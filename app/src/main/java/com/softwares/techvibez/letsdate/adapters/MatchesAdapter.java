package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.models.Matches;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesHolder> {
    private Context context;
    private List<Matches> matches;
    private MatchOnClick onClick;

    public MatchesAdapter(Context context, List<Matches> matches, MatchOnClick onClick) {
        this.context = context;
        this.matches = matches;
        this.onClick = onClick;
    }


    @NonNull
    @Override
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_matches, viewGroup, false);
        return new MatchesHolder(v, onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesHolder holder, int position) {
        Matches match = matches.get(position);
        holder.name.setText(match.getName());

        Glide.with(context)
                .load(match.getUrl()).override(360, 360).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(holder.img);



        final String name = match.getName();
        final String id = match.getId();
        final String url = match.getUrl();


    }

    @Override
    public int getItemCount() {
        return matches.size();
    }


    static class MatchesHolder extends RecyclerView.ViewHolder{

        private CircleImageView img;
        private TextView name;
        MatchOnClick onClickListener;

        MatchesHolder(View itemView, final MatchOnClick onClickListener ) {
            super(itemView);
            img = itemView.findViewById(R.id.matched_pic);
            name = itemView.findViewById(R.id.matched_name);
            this.onClickListener = onClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClick(getAdapterPosition());
                }
            });
        }
    }

    public interface MatchOnClick{
        void OnClick(int t);
    }
}

package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.models.TestModel;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {
    Context context;
    List<TestModel> spot;

    public TestAdapter(Context context, List<TestModel> spot) {
        this.context = context;
        this.spot = spot;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_spot, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(spot.get(i).getName());
        myViewHolder.city.setText(spot.get(i).getCity());
        //myViewHolder.img.setImageResource(spot.get(i).getImage());
        Glide.with(context)
                .load(spot.get(i).getUrl()).placeholder(R.mipmap.ic_launcher_round)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        //Toast.makeText(context, "Failed to fetch Image", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        //Toast.makeText(context, "Fetcing Image", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .into(myViewHolder.img);
    }

    @Override
    public int getItemCount() {
        return spot.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name, city;
        private RoundedImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            city = itemView.findViewById(R.id.item_city);
        }
    }
}

package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.models.Spot;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.MyViewHolder> {
    Context context;
    List<Spot> spot;

    public CardStackAdapter(Context context, List<Spot> spot) {
        this.context = context;
        this.spot = spot;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_spot, viewGroup, false);
        MyViewHolder vH = new MyViewHolder(v);
        return vH;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.name.setText(spot.get(i).getName());
        myViewHolder.city.setText(spot.get(i).getCity());
        Glide.with(context).load(spot.get(i).getUrl()).into(myViewHolder.img);
        //Picasso.with(context).load(spot.get(i).getUrl()).into(myViewHolder.img);
    }

    @Override
    public int getItemCount() {
        return spot.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name, city;
        private ImageView img;

       public MyViewHolder(@NonNull View itemView) {
           super(itemView);

           img = itemView.findViewById(R.id.item_image);
           name = itemView.findViewById(R.id.item_name);
           city = itemView.findViewById(R.id.item_city);
       }
   }
}

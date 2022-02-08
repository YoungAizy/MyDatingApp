package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class arrayAdapter extends ArrayAdapter<TestModel> {

    public arrayAdapter(@NonNull Context context, int resource, @NonNull List<TestModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        TestModel item = getItem(position);

        if (itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_spot, parent, false);
        }

        TextView name = itemView.findViewById(R.id.item_name);
        TextView city = itemView.findViewById(R.id.item_city);
        RoundedImageView img = itemView.findViewById(R.id.item_image);

        name.setText(item.getName());
        city.setText(item.getCity());

        Glide.with(getContext())
                .load(item.getUrl()).placeholder(R.mipmap.ic_launcher_round)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        //Toast.makeText(getContext(), "Failed to fetch Image", Toast.LENGTH_SHORT).show();;
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        //Toast.makeText(getContext(), "Fetched Image", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .into(img);

        return itemView;
    }
}

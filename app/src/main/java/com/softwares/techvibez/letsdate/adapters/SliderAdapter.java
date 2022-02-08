package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.models.Images;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter {

    Context context;

    private final List<Images> pictures;



    public SliderAdapter(Context context, List<Images> pictures) {
        this.context = context;
        this.pictures = pictures;
    }

    public void AddPic(Images pic){
        pictures.add(pic);
    }



    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_image_popup, container, false);

        LinearLayout layout = view.findViewById(R.id.slideLayout);
        ImageView image = view.findViewById(R.id.slideImage);

        image.setImageResource(pictures.get(position).getImg());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}

package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.models.Images;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class UploadPreviewAdapter extends PagerAdapter {

    Context context;

    private final List<Images> pictures;



    public UploadPreviewAdapter(Context context, List<Images> pictures) {
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

        ImageView image = view.findViewById(R.id.slideImage);


        //image.setImageResource(pictures.get(position).getImg());

        final Uri imageUri = pictures.get(position).getUris();
        //final InputStream imgStream = Context.getContentResolver().openInputStream(imageUri);
        //final Bitmap selected = BitmapFactory.decodeStream(imgStream);

        //image.setImageBitmap(selected);
        image.setImageURI(imageUri);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}

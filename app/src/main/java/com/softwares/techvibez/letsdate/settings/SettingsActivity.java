package com.softwares.techvibez.letsdate.settings;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.softwares.techvibez.letsdate.MainActivity;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.adapters.UploadPreviewAdapter;
import com.softwares.techvibez.letsdate.fragments.MatchesActivity;
import com.softwares.techvibez.letsdate.fragments.SettingsFrag;
import com.softwares.techvibez.letsdate.models.Images;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences pref;
    // private String request = "https://pmcvariety.files.wordpress.com/2018/11/drake-gods-plan.jpg?w=1000&h=562&crop=1";

    private TextView name;
    private ImageView img, image;
    private String imgPath, Name;

    private FirebaseUser user;
    private Dialog diag, dialog;

    List<Images> imagePaths;
    TextView imageCount;

    ViewPager viewPager;
    UploadPreviewAdapter sliderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_tab);

        diag = new Dialog(this);
        dialog = new Dialog(this);

        img = findViewById(R.id.profile_picture);
        name = findViewById(R.id.first_name);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup();
            }
        });

        imagePaths = new ArrayList<>();
        viewPager = new ViewPager(this);
        sliderAdapter = new UploadPreviewAdapter(this, imagePaths);


        imageCount = findViewById(R.id.imgs_count);
        //getCount();



    }

    public void ShowPopup(View v){
        TextView closePopup;
        diag.setContentView(R.layout.upgrade_popup);
        closePopup = diag.findViewById(R.id.close);
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diag.dismiss();
            }
        });
        diag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        diag.show();

        /*mbClient = BillingClient.newBuilder(SettingsActivity.this).setListener(this).build();
        mbClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(int responseCode) {
                if (responseCode == BillingClient.BillingResponse.OK){

                }
            }

            @Override
            public void onBillingServiceDisconnected() {

            }
        });**/
    }

    private void ShowPopup(){
        diag.setContentView(R.layout.dialog_add_pics);

        image = diag.findViewById(R.id.imagePreview);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                ViewPics();
            }
        });

        ImageView add = diag.findViewById(R.id.add_img);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick a Pic"), 7 );
            }
        });

        diag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        diag.show();
    }

    private void ViewPics(){
        dialog.setContentView(R.layout.popup_images);
        Button close = dialog.findViewById(R.id.close_popup);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        viewPager = dialog.findViewById(R.id.popup_view_pager);

        //pics.add(new Images(R.drawable.pic4));
        //pics.add(new Images(R.drawable.pic2));
        viewPager.setAdapter(sliderAdapter);

        int item = viewPager.getCurrentItem();
        //current.setText(item);
        //all.setText(imagePaths.size());

        dialog.show();
    }

    public void RetrieveData() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        pref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        Name = pref.getString("name", "John");




        if (user != null){

            //String userId = user.getUid();

            //StorageReference getDP = ref.child("images/" + userId);
            name.setText(Name);

            imgPath = pref.getString("imageUrl", "default");

            Glide.with(getApplicationContext())
                    .load(imgPath).placeholder(R.mipmap.ic_launcher_round)
                    .circleCrop()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            Toast.makeText(SettingsActivity.this, "Failed to fetch Image", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            Toast.makeText(SettingsActivity.this, "Fetcing Image", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                    .into(img);
            //Toast.makeText(SettingsActivity.this, "File Downloaded", Toast.LENGTH_SHORT).show();

            //loading.setVisibility(View.GONE);
            //profile.setVisibility(View.VISIBLE);


           /* try {
                final File local = File.createTempFile("profileImage", "jpg");
                getDP.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        //Picasso.with(getApplicationContext()).load(local).centerCrop().into(img);
                        Glide.with(getApplicationContext()).load(local).into(img);
                        Toast.makeText(SettingsActivity.this, "File Downloaded", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        profile.setVisibility(View.VISIBLE);
                        pref.edit().putString("imageLoaded", "loaded").apply();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        error.setVisibility(View.VISIBLE);

                    }
                });

                String checkFile = pref.getString("imageLoaded", "default");

                if (checkFile.equals("loaded")){
                    Picasso.with(getApplicationContext()).load(local).centerCrop().into(img);
                }
            }catch (IOException e){
                Toast.makeText(this, "caught exception", Toast.LENGTH_SHORT).show();
            }**/
        }else{
            img.setImageResource(R.drawable.pic1);
            //loading.setVisibility(View.GONE);
            //profile.setVisibility(View.VISIBLE);
        }
    }

    private void Upload(){

    }

    @Override
    protected void onStart() {
        super.onStart();

        RetrieveData();
    }

    public void ProfileEdit(View v){
        Intent i = new Intent(this, SettingsFrag.class);
        startActivity(i);
    }

    public void SetupAccount(View v){
        Intent i = new Intent(this, ProfileEdit.class);
        startActivity(i);
    }

    public void Matches(View v){
        Intent i = new Intent(this, MatchesActivity.class);
        startActivity(i);
        finish();
    }
    public void Settings(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void Home(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == Activity.RESULT_OK){

            try{

                final Uri imageUri = data.getData();
                //uri = imageUri;
                final InputStream imgStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selected = BitmapFactory.decodeStream(imgStream);

                image.setImageBitmap(selected);
                imagePaths.add(new Images(imageUri));
                //getCount();


            }catch (FileNotFoundException e){
                e.printStackTrace();

                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Select a Pic", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCount(){
        imageCount.setText(String.valueOf(imagePaths.size()));
    }

}

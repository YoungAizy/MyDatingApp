package com.softwares.techvibez.letsdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.softwares.techvibez.letsdate.models.Images;

import java.util.List;

public class ImagesPreviewActivity extends AppCompatActivity {

    private List<Images> imgsPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_preview);
    }
}

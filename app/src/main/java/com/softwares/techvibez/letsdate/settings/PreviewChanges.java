package com.softwares.techvibez.letsdate.settings;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.fragments.SettingsFrag;

public class PreviewChanges extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
    }

    public void GoBack (View v){
        Intent i = new Intent(this, SettingsFrag.class);
        startActivity(i);
        finish();
    }
}

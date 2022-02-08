package com.softwares.techvibez.letsdate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.softwares.techvibez.letsdate.adapters.arrayAdapter;
import com.softwares.techvibez.letsdate.fragments.MatchesActivity;
import com.softwares.techvibez.letsdate.settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.softwares.techvibez.letsdate.models.TestModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setupButton(){

        Button skip = findViewById(R.id.skip_button);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button rewind = findViewById(R.id.rewind_button);
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button like = findViewById(R.id.like_button);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }





    public void Matches(View v){
        Intent i = new Intent(this, MatchesActivity.class);
        startActivity(i);
        finish();
    }
    public void Settings(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        finish();
    }
}

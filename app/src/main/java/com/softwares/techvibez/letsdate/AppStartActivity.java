package com.softwares.techvibez.letsdate;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AppStartActivity extends AppCompatActivity {
    private FirebaseAuth authy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in);

        authy = FirebaseAuth.getInstance();
    }
}

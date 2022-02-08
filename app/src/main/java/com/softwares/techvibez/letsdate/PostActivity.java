package com.softwares.techvibez.letsdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    private DatabaseReference ref;
    private TextInputEditText editText;
    private SharedPreferences pref;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ref = FirebaseDatabase.getInstance().getReference("Posts");
        uid = FirebaseAuth.getInstance().getUid();

        pref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        editText = findViewById(R.id.status_editText);
    }

    public void PostStatus(View view){
        String msg = editText.getText().toString();
        String imgPath = pref.getString("imageUrl", "default");
        String name = pref.getString("name", "John");

        Map<Object, Object> status = new HashMap<>();
        status.put("profilePic", imgPath);
        status.put("status", msg);
        status.put("name", name);

        ref.child(uid).setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(PostActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PostActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(PostActivity.this, "Sorry Could not upload your message", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

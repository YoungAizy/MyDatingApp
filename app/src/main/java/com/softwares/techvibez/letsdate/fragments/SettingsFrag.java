package com.softwares.techvibez.letsdate.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.softwares.techvibez.letsdate.settings.PreviewChanges;
import com.softwares.techvibez.letsdate.settings.SettingsActivity;


public class SettingsFrag extends AppCompatActivity {
    private EditText about, interests, school, work, Title;
    private String getAbout, getWork, getSchool, gettitle, getInterests;
    private RadioGroup chooser;
    private RadioButton occupation;
    private int selectedOccupation;

    private LinearLayout student, worker;
    private SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.softwares.techvibez.letsdate.R.layout.edit_info);

        chooser = findViewById(com.softwares.techvibez.letsdate.R.id.occupation_chooser);
        student = findViewById(com.softwares.techvibez.letsdate.R.id.student_layout);
        worker = findViewById(com.softwares.techvibez.letsdate.R.id.working_layout);

        chooser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == com.softwares.techvibez.letsdate.R.id.student){

                    if (worker.getVisibility() == View.VISIBLE){
                        worker.setVisibility(View.GONE);
                    }

                    student.setVisibility(View.VISIBLE);
                }else if (checkedId == com.softwares.techvibez.letsdate.R.id.worker){

                    if (student.getVisibility() == View.VISIBLE){
                        student.setVisibility(View.GONE);
                    }
                    worker.setVisibility(View.VISIBLE);
                }
            }
        });


        about = findViewById(com.softwares.techvibez.letsdate.R.id.about_user);
        interests = findViewById(com.softwares.techvibez.letsdate.R.id.interests_user);
        work = findViewById(com.softwares.techvibez.letsdate.R.id.company_user);
        school = findViewById(com.softwares.techvibez.letsdate.R.id.school_user);
        Title = findViewById(com.softwares.techvibez.letsdate.R.id.occupation);

        pref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

    }

    public void Cancel(View v){
        startActivity(new Intent(this, SettingsActivity.class));
        finish();

    }

    public void PreviewAccount(View v){
        getAbout = about.getText().toString();
        getSchool = school.getText().toString();
        gettitle = Title.getText().toString();
        getInterests = interests.getText().toString();
        getWork = work.getText().toString();

        pref.edit().putString("Job", gettitle).apply();
        pref.edit().putString("Interests", getInterests).apply();
        pref.edit().putString("School", getSchool).apply();
        pref.edit().putString("Work", getWork).apply();


        Intent i = new Intent(this, PreviewChanges.class);
        startActivity(i);
    }

}

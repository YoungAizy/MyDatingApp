package com.softwares.techvibez.letsdate.settings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.softwares.techvibez.letsdate.R;

public class ProfileEdit extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_edit);

        SeekBar radius = findViewById(R.id.seekBar);
        CrystalRangeSeekbar age = findViewById(R.id.seekBar_age);
        final TextView minAge = findViewById(R.id.min_age);
        final TextView maxAge = findViewById(R.id.max_age);

        radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(ProfileEdit.this, "Value"+progress , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        age.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minAge.setText(String.valueOf(minValue));
                maxAge.setText(String.valueOf(maxValue));
            }
        });
    }


}

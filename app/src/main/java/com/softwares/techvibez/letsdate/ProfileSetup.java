package com.softwares.techvibez.letsdate;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class ProfileSetup extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    private LinearLayoutCompat bDay, Likes_Layout;

    private TextView uploading;
    private EditText dates, name, email;
    private ImageButton done;
    private ImageView img;
    private Uri uri;

    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference mStorage;
    private FirebaseAuth.AuthStateListener authListener;

    private DatePickerDialog datePicker;
    private SimpleDateFormat dateFormat;

    private RadioGroup gender;
    private RadioButton sex;
    private ProgressBar loading;

    private SharedPreferences pref;
    private String imageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();
        pref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        done = findViewById(R.id.done_setup);
        name = findViewById(R.id.signuo_name);
        email = findViewById(R.id.e_mail);
        gender = findViewById(R.id.gender);
        uploading = findViewById(R.id.uploading);
        loading = findViewById(R.id.progressBar2);
        bDay = findViewById(R.id.bDay_layout);
        Likes_Layout = findViewById(R.id.mail_layout);

        img = findViewById(R.id.img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick a Pic"), READ_REQUEST_CODE );
            }
        });


        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        dates = findViewById(R.id.birthday);
        dates.setInputType(InputType.TYPE_NULL);
        dates.requestFocus();

        dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dates.setText(dateFormat.format(newDate.getTime()));
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        //int regYear = datePicker.getDatePicker().getYear();


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //final String eMail = email.getText().toString();
                final String Name = name.getText().toString();

                img.setFocusable(false);
                img.setClickable(false);
                name.setFocusable(false);
                gender.setFocusable(false);
                bDay.setFocusable(false);
                Likes_Layout.setFocusable(false);
                done.setVisibility(View.INVISIBLE);
                uploading.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);

                if (user != null) {
                    int selectedSex = gender.getCheckedRadioButtonId();
                    sex = findViewById(selectedSex);

                    if(imageUrl == null)
                        imageUrl = "default";

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                    String getSex = sex.getText().toString();
                    String UserId = user.getUid();

                    String randomChar =  UUID.randomUUID().toString();
                    pref.edit().putString("ID", randomChar).apply();
                    pref.edit().putString("sex", getSex).apply();


                    Map<String, Object> userData = new HashMap<>();
                    userData.put("Name", Name);
                    userData.put(getSex, true);
                    userData.put("Url", imageUrl);

                    Map<String, Object> Ids = new HashMap<>();
                    Ids.put("ID", UserId);
                    Ids.put("Data", userData);


                    ref.child(getSex).child(UserId).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();

                                Toast.makeText(ProfileSetup.this, "Account Created", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ProfileSetup.this, "Account creation Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    pref.edit().putString("name", Name).apply();


                    // DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("users").child(sex.getText().toString()).child(UserId).child(Name);
                }


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == Activity.RESULT_OK){

         try{

             final Uri imageUri = data.getData();
             uri = imageUri;
             final InputStream imgStream = getContentResolver().openInputStream(imageUri);
             final Bitmap selected = BitmapFactory.decodeStream(imgStream);

             img.setImageBitmap(selected);

             String Id = user.getUid();

             final StorageReference ref = mStorage.child("images/" + Id);
             UploadTask tasku = ref.putFile(uri);
             tasku.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     Toast.makeText(ProfileSetup.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(ProfileSetup.this, "Upload not successful", Toast.LENGTH_SHORT).show();
                 }
             });


             /*ref.putStream(imgStream).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     Toast.makeText(ProfileSetup.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(ProfileSetup.this, "Upload not successfull", Toast.LENGTH_SHORT).show();
                 }
             });**/
             tasku.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                 @Override
                 public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                     if (!task.isSuccessful()){
                         throw task.getException();
                     }

                     return ref.getDownloadUrl();
                 }
             }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                 @Override
                 public void onSuccess(Uri uri) {
                     imageUrl = uri.toString();
                     pref.edit().putString("imageUrl", imageUrl).apply();

                     Toast.makeText(ProfileSetup.this, "Image Uploaded "+ imageUrl, Toast.LENGTH_SHORT).show();

                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

                     Toast.makeText(ProfileSetup.this, "Couldn't get url", Toast.LENGTH_SHORT).show();
                 }
             });

         }catch (FileNotFoundException e){
             e.printStackTrace();

             Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
         }
        }else {
            Toast.makeText(this, "Select a Pic", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.softwares.techvibez.letsdate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PreHomeActivity extends AppCompatActivity  {
    private LinearLayout resend_and_verify, optional_login, change;
    private ImageButton send_numb;
    private Button verify;
    private EditText input;
    private TextView tctView, phone;

    private FirebaseAuth auth;
    private FirebaseUser user;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId, phone_number;


    private static final int READ_REQUEST_CODE = 42;
    private String TAG = "ROSEVELD";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);

        resend_and_verify = findViewById(R.id.resend_layout);
        change = findViewById(R.id.change_number);
        phone = findViewById(R.id.entered_number);

        input = findViewById(R.id.number_input);
        send_numb = findViewById(R.id.login_btn);
        verify = findViewById(R.id.verify_bttn);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(PreHomeActivity.this, "Verification failed, please check connection", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                //super.onCodeSent(verificationId,token);
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;


            }
        };

        send_numb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_number = input.getText().toString();
                input.setHint("");
                phone.setText(phone_number);

                send_numb.setVisibility(View.GONE);
                resend_and_verify.setVisibility(View.VISIBLE);
                change.setVisibility(View.VISIBLE);


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phone_number,        // Phone number to verify
                        120,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        PreHomeActivity.this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks

            }
        });


        CheckPermissions();

    }

    private void CheckPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 10);
        }

        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, READ_REQUEST_CODE);
        }
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();

                            Intent i = new Intent(getApplicationContext(), ProfileSetup.class);
                            startActivity(i);
                            finish();

                        } else {
                            // Sign in failed, display a message and update the UI
                            //Toast.makeText(PreHomeActivity.this, "Sign in failed, please check network", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                // The verification code entered was invalid
                                Toast.makeText(PreHomeActivity.this, "Incorrect code entered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }



    @Override
    protected void onStart() {
        super.onStart();

        if (user != null){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void Resend(View v){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone_number,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                PreHomeActivity.this,               // Activity (for callback binding)
                mCallbacks);

        Toast.makeText(this, "Code Resent", Toast.LENGTH_SHORT).show();
    }

    public void Verify(View v){

        String verif_code = input.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verif_code);
        signInWithPhoneAuthCredential(credential);
        input.setText("");

        Toast.makeText(this, "Verifying Number, Please Wait", Toast.LENGTH_SHORT).show();

    }

    public void ChangeNumber(View v){

        resend_and_verify.setVisibility(View.GONE);
        change.setVisibility(View.GONE);
        tctView.setVisibility(View.VISIBLE);
        optional_login.setVisibility(View.VISIBLE);
        send_numb.setVisibility(View.VISIBLE);
    }

}

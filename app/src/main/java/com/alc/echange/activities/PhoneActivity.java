package com.alc.echange.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alc.echange.OTPVerificationActivity;
import com.alc.echange.R;
import com.google.android.material.textfield.TextInputEditText;

public class PhoneActivity extends AppCompatActivity {
TextInputEditText mOtpPhone;
Button mContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mOtpPhone = findViewById(R.id.otpPhone);
        mContinue = findViewById(R.id.cont);

        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otpIntent = new Intent(getApplicationContext(), OTPVerificationActivity.class);
                startActivity(otpIntent);
            }
        });

    }
}
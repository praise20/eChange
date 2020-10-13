package com.alc.echange.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alc.echange.R;
import com.alc.echange.api.RetrofitClient;
import com.alc.echange.model.Users;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    EditText mPhone, mPassword;
    Button mLogin;
    TextView regLink;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPhone = findViewById(R.id.etLoginPhone);
        mPassword = findViewById(R.id.etLoginPassword);
        mLogin = findViewById(R.id.btnLogin);
        regLink = findViewById(R.id.tvReg);

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToRegActivity();
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone = mPhone.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Fields must not be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(phone, password);
                }
            }
        });

    }

    private void moveToRegActivity() {
        Intent regIntent = new Intent(getApplicationContext(), Registration.class);
        startActivity(regIntent);
    }

    public void loginUser(String phone, String password) {
        Call<Users> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(phone, password);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("failing", "response" + response);
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Login Failed!, Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
package com.alc.echange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class Registration extends AppCompatActivity {
    private TextInputEditText etEmail, etPhone, etFname, etLname, etPassword;
    private Button btnReg;
    private String email, firstname, lastname, password, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        AndroidNetworking.initialize(getApplicationContext());

        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);
        etPassword = findViewById(R.id.etPassword);
        btnReg = findViewById(R.id.btnReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                firstname = etFname.getText().toString().trim();
                lastname = etLname.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                processInput();

            }
        });
    }

    private void processInput() {

        AndroidNetworking.post("https://mobile-echange.herokuapp.com/api/v1/user")
                .addBodyParameter("firstname", firstname)
                .addBodyParameter("lastname", lastname)
                .addBodyParameter("phone", phone)
                .addBodyParameter("password", password)
                .addBodyParameter("email", email)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("sucess", response.toString());
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("failed", error.toString());
                    }
                });
    }
}
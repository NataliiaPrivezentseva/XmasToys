package com.example.asterisk.xmastoys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailRegistrationActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        Toolbar myToolbar = findViewById(R.id.email_registration_toolbar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
        }

        inputEmail = findViewById(R.id.email_edit_text);
        inputPassword = findViewById(R.id.password_edit_text);

        Button register = findViewById(R.id.register_button);
        TextView login = findViewById(R.id.already_registered_text);

        progressDialog = new ProgressDialog(this);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailRegistrationActivity.this,
                        EmailLoginActivity.class));
            }
        });
    }

    private void registerUser(){
        // Get email and password from edit texts
        String email = inputEmail.getText().toString().trim();
        String password  = inputPassword.getText().toString().trim();

        // Check if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, R.string.enter_email,Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, R.string.enter_password,Toast.LENGTH_LONG).show();
            return;
        }

        //TODO other validations of input

        // If e-mail and password are OK, display a progress dialog
        progressDialog.setMessage(getString(R.string.registering_wait));
        progressDialog.show();

        // Create a new user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //todo log results
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(EmailRegistrationActivity.this,
                                    R.string.successfully_registered,Toast.LENGTH_LONG).show();
                            //TODO log in user
                        } else {
                            //display some message here
                            Toast.makeText(EmailRegistrationActivity.this,
                                    R.string.registration_error,Toast.LENGTH_LONG).show();
                            //todo send message to developer
                        }
                        progressDialog.dismiss();

                    }
                });
    }
}
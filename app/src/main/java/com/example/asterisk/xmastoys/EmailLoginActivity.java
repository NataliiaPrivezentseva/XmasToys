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

public class EmailLoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        Toolbar myToolbar = findViewById(R.id.email_login_toolbar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
        }

        progressDialog = new ProgressDialog(this);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        inputEmail = findViewById(R.id.log_email_edit_text);
        inputPassword = findViewById(R.id.log_password_edit_text);

        Button logIn = findViewById(R.id.login_button);
        TextView resetPassword = findViewById(R.id.forgot_password_text);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailLoginActivity.this,
                        ResetPasswordActivity.class));
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser(){
        //TODO logic for log in + log in with data from EmailRegistrationActivity

        String email = inputEmail.getText().toString().trim();
        String password  = inputPassword.getText().toString().trim();


        // Check if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,R.string.enter_email,Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,R.string.enter_password,Toast.LENGTH_LONG).show();
            return;
        }

        // If e-mail and password are not empty, display a progress dialog
        progressDialog.setMessage(getString(R.string.logging_wait));
        progressDialog.show();

        // Log in the user
        // TODO see again at this logic
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            //TODO order of lines?
                            //TODO getApplicationContext() in intent?
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}
package com.example.asterisk.xmastoys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class EmailLoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail;
    private android.support.design.widget.TextInputEditText inputPassword;
    private ProgressDialog progressDialog;
    private TextView eMailError;
    private TextView passwordError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        setTitle(R.string.sign_in_with_e_mail);

        progressDialog = new ProgressDialog(this);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.log_email_edit_text);
        inputPassword = findViewById(R.id.log_password);

        if (getIntent().getExtras() != null){
            inputEmail.setText(getIntent().getExtras().getString("eMail"));
        }

        TextView resetPassword = findViewById(R.id.forgot_password_text);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle infoToSend = new Bundle();
                infoToSend.putString("eMail", inputEmail.getText().toString());

                Intent intent = new Intent(EmailLoginActivity.this,
                        ResetPasswordActivity.class);
                intent.putExtras(infoToSend);
                startActivity(intent);
            }
        });

        Button logIn = findViewById(R.id.login_button);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        eMailError = findViewById(R.id.error_email_text_vew);
        passwordError = findViewById(R.id.error_password_text_vew);
    }

    private boolean validateEmail(String email) {
        // Check if email is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, R.string.enter_email, Toast.LENGTH_LONG).show();
            return false;
        }

        // Check if e-mail contains proper symbols
        if (!email.matches("[-\\w]+(\\.[-\\w]+)*@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")) {
            Toast.makeText(this, R.string.check_email, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean validatePassword(String password) {
        // Check if password is empty
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.enter_password, Toast.LENGTH_LONG).show();
            return false;
        }

        // Check password for its length
        if (password.length() < 6) {
            Toast.makeText(this, R.string.password_length, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean validateInputs(String email, String password) {
        if (!validateEmail(email)) {
            eMailError.setVisibility(View.VISIBLE);
            return false;
        } else {
            eMailError.setVisibility(View.INVISIBLE);
        }

        if (!validatePassword(password)) {
            passwordError.setVisibility(View.VISIBLE);
            return false;
        } else {
            passwordError.setVisibility(View.INVISIBLE);
        }

        return true;
    }

    private void loginUser() {
        // Get email and password from edit texts
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Validate inputs
        if (!validateInputs(email, password)) {
            return;
        }

        // If e-mail and password are valid, display a progress dialog
        progressDialog.setMessage(getString(R.string.logging_wait));
        progressDialog.show();

        // Log in the user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EmailLoginActivity.this,
                                    R.string.successfully_logged_in, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            // Removing activity from back stack
                            finish();
                        } else {
                            //display some message here
                            Toast.makeText(EmailLoginActivity.this,
                                    R.string.login_error, Toast.LENGTH_LONG).show();
                            if (task.getException() != null) {
                                Log.e("LOG_IN_USER", task.getException().getMessage());
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void registerUser() {
        // Get email and password from edit texts
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Validate inputs
        if (!validateInputs(email, password)) {
            return;
        }

        // If e-mail and password are OK, display a progress dialog
        progressDialog.setMessage(getString(R.string.registering_wait));
        progressDialog.show();

        // Create a new user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(EmailLoginActivity.this,
                                    R.string.successfully_registered, Toast.LENGTH_LONG).show();
                            loginUser();
                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(EmailLoginActivity.this,
                                    "User with this email already exist.", Toast.LENGTH_SHORT).show();
                            loginUser();
                        } else {
                            //display some message here
                            Toast.makeText(EmailLoginActivity.this,
                                    R.string.registration_error, Toast.LENGTH_LONG).show();
                            if (task.getException() != null) {
                                Log.e("REGISTER_USER", task.getException().getMessage());
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}
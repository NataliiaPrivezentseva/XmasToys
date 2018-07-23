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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Toolbar myToolbar = findViewById(R.id.reset_password_toolbar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
        }

        progressDialog = new ProgressDialog(this);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.registered_email_edit_text_view);

        Button reset = findViewById(R.id.reset_password_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = inputEmail.getText().toString().trim();

        // Check if email is empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,R.string.enter_email,Toast.LENGTH_LONG).show();
            return;
        }

        //TODO do we need to  check, if this e-mail has been registered?

        // If e-mail is not empty, display a progress dialog
        progressDialog.setMessage(getString(R.string.sending_instructions_wait));
        progressDialog.show();

        //todo log results!
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, R.string.sent_instructions,
                                    Toast.LENGTH_SHORT).show();

                            Bundle infoToSend = new Bundle();
                            infoToSend.putString("eMail", inputEmail.getText().toString());

                            Intent intent = new Intent(ResetPasswordActivity.this,
                                    EmailLoginActivity.class);
                            intent.putExtras(infoToSend);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, R.string.failed_to_send_instructions,
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}
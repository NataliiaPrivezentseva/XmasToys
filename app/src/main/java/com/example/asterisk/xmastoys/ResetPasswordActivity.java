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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail;
    private ProgressDialog progressDialog;

    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        setTitle(R.string.reset_password);

        progressDialog = new ProgressDialog(this);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.registered_email_edit_text_view);

        if (getIntent().getExtras() != null){
            Bundle receivedInfo =  getIntent().getExtras();
            inputEmail.setText(receivedInfo.getString("eMail"));
        }

        Button reset = findViewById(R.id.reset_password_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        email = inputEmail.getText().toString().trim();

        // Check if email is empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,R.string.enter_email,Toast.LENGTH_LONG).show();
            return;
        }

        // If e-mail is not empty, display a progress dialog
        progressDialog.setMessage(getString(R.string.sending_instructions_wait));
        progressDialog.show();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, R.string.sent_instructions,
                                    Toast.LENGTH_SHORT).show();
                            Log.i("RESET_PASSWORD", "Further instructions have been sent to " + email);

                            Bundle infoToSend = new Bundle();
                            infoToSend.putString("eMail", inputEmail.getText().toString());

                            Intent intent = new Intent(ResetPasswordActivity.this,
                                    EmailLoginActivity.class);
                            intent.putExtras(infoToSend);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, R.string.failed_to_send_instructions,
                                    Toast.LENGTH_SHORT).show();
                            Log.e("RESET_PASSWORD", getString(R.string.failed_to_send_instructions));
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}
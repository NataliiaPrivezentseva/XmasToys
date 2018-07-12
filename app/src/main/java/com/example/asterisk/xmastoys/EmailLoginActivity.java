package com.example.asterisk.xmastoys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class EmailLoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        Toolbar myToolbar = findViewById(R.id.email_login_toolbar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
        }

        TextView resetPassword = findViewById(R.id.forgot_password_text);

    }
}
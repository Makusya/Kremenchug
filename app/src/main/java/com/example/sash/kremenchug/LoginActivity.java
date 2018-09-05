package com.example.sash.kremenchug;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.Instant;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmaleText;
    private EditText loginpassText;
    private Button loginbtn;
    private Button Register_btn;

    private ProgressBar loginBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        loginEmaleText = (EditText) findViewById(R.id.email);
        loginpassText = (EditText) findViewById(R.id.password);
        loginbtn = (Button) findViewById(R.id.btn_login);
        Register_btn = (Button) findViewById(R.id.btn_register);
        loginBar = (ProgressBar) findViewById(R.id.login_progress);

        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(logIntent);
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textLogin = loginEmaleText.getText().toString();
                String textPassword = loginpassText.getText().toString();

                if(!TextUtils.isEmpty(textLogin) && !TextUtils.isEmpty(textPassword)) {
                    loginBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(textLogin, textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendToMap();
                            }else {
                                String error = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                            }
                            loginBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
           sendToMap();
        }
    }

    private void sendToMap() {
        Intent loginIntent = new Intent(LoginActivity.this, ImagesActivity.class);
        startActivity(loginIntent);
        finish();
    }

}

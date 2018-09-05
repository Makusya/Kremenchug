package com.example.sash.kremenchug;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email1);
        password = (EditText) findViewById(R.id.password1);
        confirm_password = (EditText) findViewById(R.id.confirm_password1);
        Button reg_btn = (Button) findViewById(R.id.btn_register1);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String Confirm_pass = confirm_password.getText().toString();

                if (!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password) & !TextUtils.isEmpty(Confirm_pass)){
                    if (Password.equals(Confirm_pass)){
                        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    sendToMain();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "error: " + task.getException().getMessage(),Toast.LENGTH_LONG  ).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RegisterActivity.this, "error: password and confirm password doesn't match",Toast.LENGTH_LONG  ).show();
                    }

                }else {
                    Toast.makeText(RegisterActivity.this, "error: field is empty",Toast.LENGTH_LONG  ).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentuser = mAuth.getCurrentUser();
        if(currentuser!= null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}

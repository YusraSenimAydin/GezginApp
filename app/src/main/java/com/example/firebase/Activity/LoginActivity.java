package com.example.firebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
EditText userEmailEt,userPasswordEt;
Button loginBtn,registerBtn;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEmailEt=(EditText)findViewById(R.id.useremailEt);
        userPasswordEt=(EditText)findViewById(R.id.userPasswordEt);
        loginBtn=(Button)findViewById(R.id.loginBtn);
        registerBtn=(Button) findViewById(R.id.registerBtn);
        mAuth=FirebaseAuth.getInstance();
        
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=userEmailEt.getText().toString().trim();
                String userPassword=userPasswordEt.getText().toString().trim();
                if (!userEmail.isEmpty()&&!userPassword.isEmpty()){
                    login(userEmail,userPassword);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Email ya da Parola Boş Bırakılmaz!!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void login(String userEmail, String userPassword) {
        mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                    Log.d("Email","SignInWith:succes");

                }else {Log.v("Fail","SignInWith:Failure",task.getException());}
            }
        });
    }

    private void goToRegister() {
        Intent registerIntent=new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(registerIntent);
    }
}

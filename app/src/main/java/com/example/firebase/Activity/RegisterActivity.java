package com.example.firebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText userEmailet,userPasswordet,userConfirmPasswordet;
    Button registerBtn;
    FirebaseAuth mAuth;
    String UserEmail,UserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userEmailet=(EditText)findViewById(R.id.useremailet);
        userPasswordet=(EditText)findViewById(R.id.userpasswordet);
        userConfirmPasswordet=(EditText)findViewById(R.id.userConfrimPasswordet);
        registerBtn=(Button)findViewById(R.id.registerBtnn);
        mAuth=FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInformRegister();
            }
        });
    }

    private void getUserInformRegister() {

        UserEmail=userEmailet.getText().toString().trim();
        UserPassword=userPasswordet.getText().toString().trim();
       String UserConfirmPassword=userConfirmPasswordet.getText().toString().trim();
        if(!UserEmail.isEmpty()&& !UserPassword.isEmpty()&& !UserConfirmPassword.isEmpty()) {
            if(UserPassword.equals(UserConfirmPassword)){
                register();
            }
            else{
                Toast.makeText(getApplicationContext(),"Kayıt için Tüm Alanları doldur!!",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void register() {
        mAuth.createUserWithEmailAndPassword(UserEmail,UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser=mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),"Kayıt işlemi başarılı",Toast.LENGTH_SHORT).show();
                    Intent LoginIntent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(LoginIntent);

                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthException){
                    if(((FirebaseAuthException)e).getErrorCode().equals("ERROR_WEAK_PASSWORD")){
                        Toast.makeText(getApplicationContext(),"Eksik şifre",Toast.LENGTH_SHORT).show();

                    }else if(((FirebaseAuthException)e).getErrorCode().equals("ERROR_INVALID_EMAİL")){
                        Toast.makeText(getApplicationContext(),"Geçersiz mail!",Toast.LENGTH_SHORT).show();
                    }else if(((FirebaseAuthException)e).getErrorCode().equals("ERROR_EMAİL_ALREADY_IN_USE")){
                        Toast.makeText(getApplicationContext(),"Mail zaten kayıtlı!",Toast.LENGTH_SHORT).show();
                }
                }
            }
        });
    }
}

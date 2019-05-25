package com.example.firebase.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebase.R;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoteActivity extends AppCompatActivity {
EditText userNoteEt;
Button addNoteBtn,goToNotesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        userNoteEt=(EditText)findViewById(R.id.userNoteEt);
        addNoteBtn=(Button)findViewById(R.id.addNoteBtn);
        goToNotesBtn=(Button)findViewById(R.id.goToNotesBtn);


        goToNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//geriTusu
            }
        });
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteBtn();
            }
        });

    }

    private void addNoteBtn() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference mRef=firebaseDatabase.getReference().child("GezdigimYerler");
        String NotesId=mRef.push().getKey();
        String receivedUserNote=userNoteEt.getText().toString();
        if(receivedUserNote.length()>0){
            mRef.child(NotesId).child("sehirAdi").setValue(receivedUserNote);
            showDialog("başarılı!","Notunuz Kaydedildi!");

        }else {
            showDialog("başarısız!","Not alanı boş bırakılmaz!");
        }
        userNoteEt.setText("");

    }
private void showDialog(String title, String message){
        final AlertDialog.Builder builder=new AlertDialog.Builder(AddNoteActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("TAMAM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
}

}

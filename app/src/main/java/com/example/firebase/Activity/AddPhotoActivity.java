package com.example.firebase.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddPhotoActivity extends AppCompatActivity {
    ImageView userPhoto;
    Button SelectPhotoBtn,SavePhotoBtn;
    FirebaseStorage firebaseStorage;
    FirebaseAuth mAuth;
    Uri filePath;
    private ProgressDialog progressDialog;
    private static final int IMAGE_REQUEST=111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        userPhoto=(ImageView)findViewById(R.id.userPhoto);
        SelectPhotoBtn=(Button)findViewById(R.id.SelectPhotoBtn);
        SavePhotoBtn=(Button)findViewById(R.id.SavePhotoBtn);


        mAuth= FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        showPhoto();
        SavePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePhoto();
            }
        });
        SelectPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPhoto();
            }
        });

    }

    private void SavePhoto() {
        if(filePath!=null){
            showProgressDialog();
            StorageReference StrogeRef=firebaseStorage.getReference();
            StrogeRef.child("userPRofilPhoto").putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dismissProgresDialog();
                    Toast.makeText(AddPhotoActivity.this,"Fotoğraf Başarılı Bir Şekilde Kaydedildi!!",Toast.LENGTH_SHORT).show();
                    onBackPressed();//kaydedip cıkıması icin
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dismissProgresDialog();
                    Toast.makeText(AddPhotoActivity.this, "Fotoğraf Kaydedilemedi!!",Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

    private void showPhoto() {

        showProgressDialog();
        StorageReference mRef=firebaseStorage.getReference();
        mRef.child("userPRofilPhoto").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                progressDialog.dismiss();

                Picasso.with(AddPhotoActivity.this).load(filePath).fit().centerCrop().into(userPhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissProgresDialog();
                Toast.makeText(AddPhotoActivity.this, "Fotoğraf yükleme işlemi başarılı!!",Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void SelectPhoto() {//galerinden fotografı secmek icin
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Resim Seçiniz.."),IMAGE_REQUEST);


    }
    private void showProgressDialog(){//sürec dialogu islesin fotograf acilirken
        progressDialog=new ProgressDialog(AddPhotoActivity.this);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void dismissProgresDialog(){//sürec dialogunu durdumak icin
        progressDialog.dismiss();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK&& data!=null && data.getData()!=null){
            filePath=data.getData();
            try {

                Picasso.with(AddPhotoActivity.this).load(filePath).fit().centerCrop().into(userPhoto);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

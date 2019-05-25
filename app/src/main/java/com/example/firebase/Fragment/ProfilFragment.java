package com.example.firebase.Fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.firebase.Activity.AddPhotoActivity;
import com.example.firebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView ProfilPhoto,ProfilUserInstagram,ChangedProfilPhoto;
    TextView profilname, ProfilBio;
    ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View ProfilView=inflater.inflate(R.layout.fragment_profil, container, false);
       profilname=(TextView)ProfilView.findViewById(R.id.profilname);
       ProfilBio=(TextView)ProfilView.findViewById(R.id.ProfilBio);
       ProfilPhoto=(ImageView)ProfilView.findViewById(R.id.ProfilPhoto);
       ProfilUserInstagram=(ImageView) ProfilView.findViewById(R.id.ProfilUserInstagram);
       ChangedProfilPhoto=(ImageView)ProfilView.findViewById(R.id.ChangeProfilPhoto) ;
       ChangedProfilPhoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getContext(), AddPhotoActivity.class);
               startActivity(intent);
           }
       });
       ProfilUserInstagram.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               OpenInstagram();
           }
       });
       return ProfilView;
    }
    private  void showProfilPhoto(){
        showProgressDialog();
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference mRef=firebaseStorage.getReference();
        mRef.child("userProfilPhoto").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                progressDialog.dismiss();
                Glide.with(getContext())
                        .load(uri)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(200,200) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                ProfilPhoto.setImageBitmap(resource);
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            progressDialog.dismiss();
            }
        });
    }
    private void showProgressDialog(){

        progressDialog =new ProgressDialog(getContext());
        progressDialog.setMessage("Yükleniyorrrr...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void OpenInstagram(){
        Uri instagramUri=Uri.parse("https://www.instagram.com/gezList");
        Intent intent=new Intent(Intent.ACTION_VIEW,instagramUri);
        intent.setPackage("com.instagram.android");
        try{
            startActivity(intent);
        }
        catch(ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW));
            instagramUri.parse("https://www.instagram.com/gezList");


        }




    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.firebase.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebase.PostModel.PostModel;
import com.example.firebase.R;

import java.util.List;

public class CustomPostAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<PostModel>postModelList;

    public CustomPostAdapter(LayoutInflater layoutInflater, List<PostModel> postModelList) {
        this.layoutInflater = layoutInflater;
        this.postModelList = postModelList;
    }

    @Override
    public int getCount() {
        return postModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return postModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View PostView=layoutInflater.inflate(R.layout.post_list,null);
        ImageView postPicture=PostView.findViewById(R.id.list_image);
        TextView postName=PostView.findViewById(R.id.List_ad);
        TextView postDescription=PostView.findViewById(R.id.List_aciklama);

        PostModel postModel=postModelList.get(position);
        postPicture.setImageResource(postModel.getPostPicture());
        postName.setText(postModel.getPostName());
        postDescription.setText(postModel.getPostDescription());



        return PostView;
    }
}

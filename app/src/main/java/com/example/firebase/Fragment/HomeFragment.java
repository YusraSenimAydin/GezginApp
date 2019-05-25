package com.example.firebase.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.firebase.Adapter.CustomPostAdapter;
import com.example.firebase.PostModel.PostModel;
import com.example.firebase.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   // List<PostModel> postList=new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<PostModel> postList=new ArrayList<PostModel>();

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ListView HomeList=(ListView)view.findViewById(R.id.HomeList);


        postList.add(new PostModel(R.drawable.foto3, "İstanbul","İstanbul'un tarihi ana hatlarıyla beş büyük döneme ayrılabilir: Tarih öncesi dönem, Byzantion dönemi, Doğu Roma dönemi, Osmanlı dönemi ve Türkiye dönemi. "));
        postList.add(new PostModel(R.drawable.foto4, "İzmir", "Eski İzmir kenti körfezin kuzeydoğusunda yer alan ve yüzölçümü yaklaşık yüz dönüm olan bir adacık üzerinde kurulmuştu"));
        postList.add(new PostModel(R.drawable.foto5,"Hatay", "Hatay, Türkiye'nin en eski yerleşim yerlerinden biridir. Araştırmacılar, eldeki bilgilere göre yörenin iskân tarihinin M.Ö. yüzbinli yıllara rastlayan orta paleolitik döneme kadar uzandığını ifade etmekte, bunun 2,5 milyon yıl öncesine kadar uzanabileceğini belirtmektedirler."));

        CustomPostAdapter customPostAdapter=
                new CustomPostAdapter(getLayoutInflater(),postList);
        HomeList.setAdapter(customPostAdapter);

    HomeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            builder.setTitle("Bilgiler");
            String SelecetedName=postList.get(position).getPostName();
            String SelecetedDescription=postList.get(position).getPostDescription();
            int SelectedPicture=postList.get(position).getPostPicture();

            String message=SelecetedName+""+SelecetedDescription;
            builder.setIcon(SelectedPicture);
            builder.setMessage(message);
            builder.setNegativeButton("TAMAM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    });




        return view;
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

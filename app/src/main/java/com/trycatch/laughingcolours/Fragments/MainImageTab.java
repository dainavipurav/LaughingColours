package com.trycatch.laughingcolours.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trycatch.laughingcolours.Activities.ImageFullScreen;
import com.trycatch.laughingcolours.PojoClass.ImagePojo;
import com.trycatch.laughingcolours.Networks.API_Client;
import com.trycatch.laughingcolours.Adapters.ImageAdapter;
import com.trycatch.laughingcolours.Networks.API_Interface;
import com.trycatch.laughingcolours.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainImageTab extends Fragment implements ImageAdapter.Img_OnItemClickListener {
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    API_Interface api_interface;
    ArrayList<ImagePojo> imagePojos;
    ArrayList<ImagePojo> img_data = new ArrayList<>();
    Context context = this.getActivity();
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    public MainImageTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_tab, container, false);
        recyclerView = rootView.findViewById(R.id.img_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView = rootView.findViewById(R.id.img_recycler);
        imagePojos = new ArrayList<>();
        generateList(rootView);
        imageAdapter = new ImageAdapter(imagePojos, getContext());

        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageAdapter = new ImageAdapter(imagePojos,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imageAdapter);
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
    }

    public void generateList(View rootView)
    {
        api_interface = API_Client.getAPI_Client().create(API_Interface.class);

        String category_id = getActivity().getIntent().getStringExtra("cat_id");
        Call<ArrayList<ImagePojo>> call = api_interface.getImages(category_id);

        call.enqueue(new Callback<ArrayList<ImagePojo>>() {
            @Override
            public void onResponse(Call<ArrayList<ImagePojo>> call, Response<ArrayList<ImagePojo>> response) {
                img_data = response.body();
                Images(img_data);
            }

            @Override
            public void onFailure(Call<ArrayList<ImagePojo>> call, Throwable throwable) {
                Toast.makeText(getActivity(),"Check",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Images(ArrayList<ImagePojo> imagePojos){
        imageAdapter = new ImageAdapter(imagePojos,getContext());
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(MainImageTab.this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2 ,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        progressDialog.dismiss();
    }

    @Override
    public void onImageClick(int img_position , String img_url) {
        Intent intent = new Intent(this.getActivity(), ImageFullScreen.class);
        intent.putParcelableArrayListExtra("img_data", img_data);
        intent.putExtra("img_url",img_url);
        intent.putExtra("img_position",img_position);
        startActivity(intent);
    }
}

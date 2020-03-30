package com.trycatch.laughingcolours.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trycatch.laughingcolours.Activities.FavoriteImageFullScreen;
import com.trycatch.laughingcolours.Adapters.FavoriteImageAdapter;
import com.trycatch.laughingcolours.R;
import com.trycatch.laughingcolours.Tables.Database_Adapter;
import com.trycatch.laughingcolours.Tables.FavoriteImages;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteImageTab extends Fragment implements FavoriteImageAdapter.FavImg_OnItemClickListener{

    RecyclerView recyclerView;
    Database_Adapter database_adapter;
    FavoriteImageAdapter favoriteImageAdapter;
    List<FavoriteImages> favoriteImages = new ArrayList<>();

    public FavoriteImageTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_image_tab, container, false);
        // Inflate the layout for this fragment
        database_adapter = new Database_Adapter(this.getActivity());
        recyclerView = view.findViewById(R.id.fav_img_recyclerview);

        /*ProgressDialog progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();*/

        favoriteImageAdapter = new FavoriteImageAdapter(database_adapter.getAllFavoriteImages(),getContext());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2 ,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(favoriteImageAdapter);
        favoriteImageAdapter.setOnItemClickListener(FavoriteImageTab.this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteImageAdapter.setFavoriteImages(database_adapter.getAllFavoriteImages());
    }

    @Override
    public void onFavImageClick(int fav_img_position, String fav_img_url) {
        Intent intent = new Intent(this.getActivity(), FavoriteImageFullScreen.class);
        favoriteImages = database_adapter.getAllFavoriteImages();
        intent.putExtra("fav_img_position",fav_img_position);
        intent.putExtra("fav_img_url",fav_img_url);
        startActivity(intent);
    }
}

package com.trycatch.laughingcolours.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trycatch.laughingcolours.Activities.FavoriteTextFullScreen;
import com.trycatch.laughingcolours.Adapters.FavoriteTextAdapter;
import com.trycatch.laughingcolours.R;
import com.trycatch.laughingcolours.Tables.Database_Adapter;
import com.trycatch.laughingcolours.Tables.FavoriteTexts;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTextTab extends Fragment implements FavoriteTextAdapter.FavTxt_OnItemClickListener {

    RecyclerView recyclerView;
    Database_Adapter database_adapter;
    FavoriteTextAdapter favoriteTextAdapter;
    List<FavoriteTexts> favoriteTexts = new ArrayList<>();

    public FavoriteTextTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_text_tab, container, false);

        database_adapter = new Database_Adapter(this.getActivity());

        recyclerView = view.findViewById(R.id.fav_txt_recyclerview);

        favoriteTextAdapter = new FavoriteTextAdapter(database_adapter.getAllFavoriteTexts(),getContext());
        //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2 ,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(favoriteTextAdapter);
        favoriteTextAdapter.setOnItemClickListener(FavoriteTextTab.this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteTextAdapter.setFavoriteTexts(database_adapter.getAllFavoriteTexts());
    }

    @Override
    public void onFavTextClick(int fav_txt_position, String fav_txt_post_desc) {
        Intent intent = new Intent(this.getActivity(), FavoriteTextFullScreen.class);
        favoriteTexts = database_adapter.getAllFavoriteTexts();
        intent.putExtra("fav_txt_position",fav_txt_position);
        intent.putExtra("fav_txt_url",fav_txt_post_desc);
        startActivity(intent);
    }
}

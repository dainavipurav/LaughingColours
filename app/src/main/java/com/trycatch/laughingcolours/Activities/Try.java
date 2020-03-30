package com.trycatch.laughingcolours.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.trycatch.laughingcolours.Adapters.FavoriteImageAdapter;
import com.trycatch.laughingcolours.R;
import com.trycatch.laughingcolours.Tables.Database_Adapter;
import com.trycatch.laughingcolours.Tables.FavoriteImages;

public class Try extends AppCompatActivity {

    RecyclerView recyclerView;
    Database_Adapter database_adapter;
    FavoriteImages favoriteImages;
    FavoriteImageAdapter favoriteImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);

        database_adapter = new Database_Adapter(Try.this);

        recyclerView = findViewById(R.id.fav_img_recy);
        setAdapter();
    }

    private void setAdapter() {
        favoriteImageAdapter = new FavoriteImageAdapter(database_adapter.getAllFavoriteImages(),Try.this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2 ,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(favoriteImageAdapter);
        favoriteImageAdapter.setFavoriteImages(database_adapter.getAllFavoriteImages());
    }
}

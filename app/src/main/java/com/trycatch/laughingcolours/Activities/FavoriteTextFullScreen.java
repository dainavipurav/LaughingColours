package com.trycatch.laughingcolours.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.trycatch.laughingcolours.Adapters.FavoriteTextAdapter;
import com.trycatch.laughingcolours.Fragments.FavTextFragment;
import com.trycatch.laughingcolours.R;
import com.trycatch.laughingcolours.Tables.Database_Adapter;
import com.trycatch.laughingcolours.Tables.FavoriteTexts;

import java.util.List;

public class FavoriteTextFullScreen extends AppCompatActivity {

    Context context;
    int fav_txt_position;
    String fav_txt_post_desc;
    List<FavoriteTexts> favoriteTexts;
    Database_Adapter database_adapter;
    FavoriteTextAdapter favoriteTextAdapter;
    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_text_full_screen);

        database_adapter = new Database_Adapter(this);
        favoriteTexts = database_adapter.getAllFavoriteTexts();
        final ViewPager viewPager = findViewById(R.id.fav_txt_vp);
        context = this;

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bnv_fav_txt);

        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.getMenu().getItem(1).setCheckable(false);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int position = viewPager.getCurrentItem();
                final int id = favoriteTexts.get(position).getId();
                final String post_desc = favoriteTexts.get(position).getTxtpost_desc();
                final String fav_txt_id = favoriteTexts.get(position).getTxt_id();

                switch (menuItem.getItemId()) {
                    case R.id.share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, post_desc);
                        startActivity(Intent.createChooser(intent, "Share"));
                        break;

                    case R.id.copy:
                        ClipboardManager clipboard = (ClipboardManager)
                                getSystemService(Context.CLIPBOARD_SERVICE);
                        Uri copyUri = Uri.parse(post_desc);
                        ClipData clip = ClipData.newUri(getContentResolver(), "URI", copyUri);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(context,"Copied To Clipboard",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.remove_favorite:
                        final int has_txt = database_adapter.checkSingleFavoriteText(fav_txt_id);
                        if (has_txt == 1) {
                            deleteFavoriteText(fav_txt_id,position);
                        }
                        break;
                }
                return true;
            }
        });


        Intent intent = getIntent();
        fav_txt_position = intent.getIntExtra("fav_txt_position",0);
        fav_txt_post_desc = intent.getStringExtra("fav_txt_url");
        //Picasso.with(context).load(fav_img_url).into(imageView);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(fav_txt_position);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            bundle.putString("FavoriteText", favoriteTexts.get(i).getTxtpost_desc());
            FavTextFragment favTextFragment = new FavTextFragment();
            favTextFragment.setArguments(bundle);
            return favTextFragment;
        }

        @Override
        public int getCount() {
            return favoriteTexts.size();
        }
    }

    public void deleteFavoriteText(String id,int position)
    {
        count = database_adapter.deleteFavoriteText(id);
        Toast.makeText(FavoriteTextFullScreen.this,"Removed From FavoriteImages",Toast.LENGTH_SHORT).show();
        /*if (count > 0)
        {
            favoriteTextAdapter.deleteFavoriteText(position);
        }*/
    }

}

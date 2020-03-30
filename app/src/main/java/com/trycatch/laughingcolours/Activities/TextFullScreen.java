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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.trycatch.laughingcolours.Fragments.TextFragment;
import com.trycatch.laughingcolours.PojoClass.TextPojo;
import com.trycatch.laughingcolours.R;
import com.trycatch.laughingcolours.Tables.Database_Adapter;
import com.trycatch.laughingcolours.Tables.FavoriteTexts;

import java.util.ArrayList;
import java.util.List;

public class TextFullScreen extends AppCompatActivity {

    List<TextPojo> txt_data = new ArrayList<>();
    int txt_position;
    Context context;
    Toolbar toolbar;
    Database_Adapter database_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_full_screen);

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        setTitle("");

        context = this;

        database_adapter = new Database_Adapter(TextFullScreen.this);

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/

        final ViewPager viewPager = findViewById(R.id.txt_pager);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.getMenu().getItem(1).setCheckable(false);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);
        bottomNavigationView.getMenu().getItem(3).setCheckable(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int position = viewPager.getCurrentItem();
                final String id = txt_data.get(position).getId();
                final String txt = txt_data.get(position).getPostDesc();

                switch (menuItem.getItemId()){
                    case R.id.home:
                        Intent intent_home = new Intent(TextFullScreen.this,CategoryActivity.class);
                        startActivity(intent_home);
                        break;

                    case R.id.copy:
                        ClipboardManager clipboard = (ClipboardManager)
                                getSystemService(Context.CLIPBOARD_SERVICE);
                        Uri copyUri = Uri.parse(txt);
                        ClipData clip = ClipData.newUri(getContentResolver(), "URI", copyUri);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(context,"Copied To Clipboard",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, txt);
                        startActivity(Intent.createChooser(intent, "Share"));
                        break;

                    case R.id.favourite:
                        final int has_txt = database_adapter.checkSingleFavoriteText(id);
                        if (has_txt == 1) {
                            deleteFavoriteText(id);
                        }
                        else {
                            addToFavoriteText(id, txt);
                        }
                        break;

                    /*case R.id.copy:
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        String getstring = txt;*/
                }
                    return true;
            }
        });

        Intent intent = getIntent();
        txt_data = intent.getParcelableArrayListExtra("txt_data");
        txt_position = intent.getIntExtra("txt_position",txt_position);

        Txt_ViewPagerAdapter txt_viewPagerAdapter = new Txt_ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(txt_viewPagerAdapter);
        viewPager.setCurrentItem(txt_position);
    }

    public class Txt_ViewPagerAdapter extends FragmentPagerAdapter
    {

        public Txt_ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            bundle.putString("Text",txt_data.get(i).getPostDesc());
            TextFragment textFragment = new TextFragment();
            textFragment.setArguments(bundle);
            return textFragment;
        }

        @Override
        public int getCount() {
            return txt_data.size();
        }
    }



    public void addToFavoriteText(String id , String txt)
    {
        FavoriteTexts favoriteTexts = new FavoriteTexts();
        favoriteTexts.setTxt_id(id);
        favoriteTexts.setTxtpost_desc(txt);

        long last_id = database_adapter.insertFavoriteText(favoriteTexts);

        if (last_id > 0)
        {
            /*FavoriteImages favoriteImages1 = database_adapter.gatSingleFavoriteImage(last_id);
            favoriteImageAdapter.insertFavoriteImage(favoriteImages1);*/
            Toast.makeText(TextFullScreen.this,"Added To FavoriteImages",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(TextFullScreen.this,"Can't Added to FavoriteImages",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteFavoriteText(String id)
    {
        int count = database_adapter.deleteFavoriteText(id);
        Toast.makeText(TextFullScreen.this,"Removed From FavoriteImages",Toast.LENGTH_SHORT).show();
    }
}

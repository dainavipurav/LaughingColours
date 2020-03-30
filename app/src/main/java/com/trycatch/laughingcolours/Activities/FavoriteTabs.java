package com.trycatch.laughingcolours.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.trycatch.laughingcolours.R;
import com.trycatch.laughingcolours.TabAdapters.PageAdapter;
import com.trycatch.laughingcolours.Fragments.FavoriteImageTab;
import com.trycatch.laughingcolours.Fragments.FavoriteTextTab;

public class FavoriteTabs extends AppCompatActivity {

    Toolbar toolbar,toolbartabs;
    ViewPager viewPager;
    TabLayout tabLayout;

    public static PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_tabs);

        toolbar = findViewById(R.id.tool_bar);
        toolbartabs = findViewById(R.id.toolbartab);
        viewPager = findViewById(R.id.demotab_vp);
        tabLayout = findViewById(R.id.tablayout);

        pageAdapter = new PageAdapter(getSupportFragmentManager());
        pageAdapter.addFragment(new FavoriteImageTab(),"Fav. Images");
        pageAdapter.addFragment(new FavoriteTextTab(),"Fav. Texts");

        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager);


        /*tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
                    case 0 :
                        getWindow().setStatusBarColor(Color.GRAY);
                        toolbar.setBackgroundColor(Color.GRAY);
                        toolbartabs.setBackgroundColor(Color.GRAY);
                        tabLayout.setBackgroundColor(Color.GRAY);
                        break;


                    case 1 :
                        getWindow().setStatusBarColor(Color.GRAY);
                        toolbar.setBackgroundColor(Color.GRAY);
                        toolbartabs.setBackgroundColor(Color.GRAY);
                        tabLayout.setBackgroundColor(Color.GRAY);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
    }

}

package com.trycatch.laughingcolours.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.trycatch.laughingcolours.Adapters.CategoryAdapter;
import com.trycatch.laughingcolours.Fragments.MainTab;
import com.trycatch.laughingcolours.Networks.API_Client;
import com.trycatch.laughingcolours.Networks.API_Interface;
import com.trycatch.laughingcolours.PojoClass.CategoryPojo;
import com.trycatch.laughingcolours.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.Cat_OnItemClickListener {

    private CategoryAdapter cat_adapter;
    private RecyclerView cat_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private API_Interface api_interface;
    Toolbar toolbar;
    ProgressDialog progressDialog;

   // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        cat_recyclerView = findViewById(R.id.cat_recycler);
        layoutManager = new LinearLayoutManager(this);
        cat_recyclerView.setLayoutManager(layoutManager);
        cat_recyclerView.setHasFixedSize(true);
        toolbar = findViewById(R.id.tool_bar);

        progressDialog = new ProgressDialog(CategoryActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        setSupportActionBar(toolbar);
        setTitle("");
        api_interface = API_Client.getAPI_Client().create(API_Interface.class);

        Call<List<CategoryPojo>> call = api_interface.getCategories();

        call.enqueue(new Callback<List<CategoryPojo>>() {
            @Override
            public void onResponse(Call<List<CategoryPojo>> call, Response<List<CategoryPojo>> response)
            {
                Categories(response.body());
                /*cat_adapter = new RecyclerAdapter(categories);
                cat_recyclerView.setAdapter(cat_adapter);*/

            }

            @Override
            public void onFailure(Call<List<CategoryPojo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Check your Internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Categories(final List<CategoryPojo> categories)
    {

        cat_adapter = new CategoryAdapter(categories , this);

        cat_recyclerView.setAdapter(cat_adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2 ,StaggeredGridLayoutManager.VERTICAL);
        cat_recyclerView.setLayoutManager(staggeredGridLayoutManager);
        progressDialog.dismiss();
        cat_adapter.setOnItemClickListener(CategoryActivity.this);
    }

    @Override
    public void onCategoryClick(String cat_id) {
        Intent intent = new Intent(CategoryActivity.this, MainTab.class);
        intent.putExtra("cat_id",cat_id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String shareBody = "https://play.google.com/store/apps/details?id=com.trycatch.laughterstation&hl=en";
        int id = item.getItemId();



        if (id == R.id.act_fav)
        {
                Intent intent = new Intent(CategoryActivity.this, FavoriteTabs.class);
                startActivity(intent);
        }
        else if (id == R.id.act_share)
        {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            /*Intent intent = new Intent(CategoryActivity.this,Try.class);
            startActivity(intent);*/
        }
        return super.onOptionsItemSelected(item);
    }

}

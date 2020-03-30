package com.trycatch.laughingcolours.Activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.trycatch.laughingcolours.Fragments.ImageFragment;
import com.trycatch.laughingcolours.PojoClass.ImagePojo;
import com.trycatch.laughingcolours.R;
import com.trycatch.laughingcolours.Tables.Database_Adapter;
import com.trycatch.laughingcolours.Tables.FavoriteImages;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageFullScreen extends AppCompatActivity {

    List<ImagePojo> img_data = new ArrayList<>();
    int img_position;
    String img_url = "";
    Context context;
    private static final int PERMISSION_STORAGE_CODE = 100;
    Database_Adapter database_adapter;
    //FavoriteImageAdapter favoriteImageAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);

        /*toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        setTitle("");*/

        database_adapter = new Database_Adapter(ImageFullScreen.this);

        context = this;

        final ViewPager viewPager = findViewById(R.id.img_pager);


        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.getMenu().getItem(1).setCheckable(false);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);
        bottomNavigationView.getMenu().getItem(3).setCheckable(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int position = viewPager.getCurrentItem();
                final String id = img_data.get(position).getId();
                final String url = img_data.get(position).getImages();

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent_home = new Intent(ImageFullScreen.this, CategoryActivity.class);
                        startActivity(intent_home);
                        break;

                    case R.id.share:
                        shareImage(url, context);
                        break;

                    case R.id.download:
                        saveImage(url);
                        break;

                    case R.id.favourite:
                        //menuItem.setIcon(R.drawable.ic_fav_not_selected);
                        final int has_img = database_adapter.checkSingleFavoriteImage(id);
                        if (has_img == 1) {
                            deleteFavoriteImage(id);
                            //menuItem.setIcon(R.drawable.ic_fav_not_selected);
                        }
                        else {
                            addToFavoriteImage(id, url);
                            //menuItem.setIcon(R.drawable.ic_fav_selected);
                        }
                        break;
                }

                return true;
            }
        });

        Intent intent = getIntent();
        img_position = intent.getIntExtra("img_position", 0);
        img_data = intent.getParcelableArrayListExtra("img_data");
        img_url = intent.getStringExtra("img_url");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(img_position);
    }




    public void shareImage(String url, final Context context) {
        Picasso.with(context).load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                context.startActivity(Intent.createChooser(i, "Share Image"));
            }
            @Override public void onBitmapFailed(Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }
    static public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }



    public void saveImage(String url){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED)
            {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_STORAGE_CODE);
            }
            else
            {
                startDownloading(url);
            }
        }
        else
        {
            startDownloading(url);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
                    //startDownloading(img_url);
                }
                else
                {
                    Toast.makeText(context,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /*public void startDownloading(String murl)
    {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(murl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Download");
        request.setDescription("Downloading ... ");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , "" + System.currentTimeMillis());
        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast.makeText(context,"Downloaded",Toast.LENGTH_SHORT).show();

    }*/


    public void startDownloading(String url){
        Bitmap finalBitmap = getBitmapFromURL(url);
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        String fname = "Image-"+ System.currentTimeMillis()  +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                    DownloadManager.Request.NETWORK_WIFI);
            //request.setTitle("Download");
            request.setDescription("Downloading ... ");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS+myDir, "" + System.currentTimeMillis());
            DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
            Toast.makeText(context,"Downloading",Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




    public void addToFavoriteImage(String id , String url)
    {
        FavoriteImages favoriteImages = new FavoriteImages();
        favoriteImages.setImg_id(id);
        favoriteImages.setImg_url(url);

        long last_id = database_adapter.insertFavoriteImage(favoriteImages);

        if (last_id > 0)
        {
            /*FavoriteImages favoriteImages1 = database_adapter.gatSingleFavoriteImage(last_id);
            favoriteImageAdapter.insertFavoriteImage(favoriteImages1);*/
            Toast.makeText(ImageFullScreen.this,"Added To FavoriteImages",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ImageFullScreen.this,"Can't Added to FavoriteImages",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteFavoriteImage(String id)
    {
        int count = database_adapter.deleteFavoriteImage(id);
        Toast.makeText(ImageFullScreen.this,"Removed From FavoriteImages",Toast.LENGTH_SHORT).show();
    }







    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            bundle.putString("Image", img_data.get(i).getImages());
            ImageFragment imageFragment = new ImageFragment();
            imageFragment.setArguments(bundle);
            return imageFragment;
        }

        @Override
        public int getCount() {
            return img_data.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.img_bottom_navigation, menu);
        return true;
    }

}

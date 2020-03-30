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
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.trycatch.laughingcolours.Adapters.FavoriteImageAdapter;
import com.trycatch.laughingcolours.Fragments.FavImageFragment;
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
import java.util.List;


public class FavoriteImageFullScreen extends AppCompatActivity {

    ImageView imageView;
    Context context;
    int fav_img_position;
    String fav_img_url;
    List<FavoriteImages> favoriteImages;
    private static final int PERMISSION_STORAGE_CODE = 200;
    Database_Adapter database_adapter;
    FavoriteImageAdapter favoriteImageAdapter;
    ImageFullScreen imageFullScreen;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_image_full_screen);
        database_adapter = new Database_Adapter(this);
        context = this;
        favoriteImages = database_adapter.getAllFavoriteImages();
        final ViewPager viewPager = findViewById(R.id.fav_img_vp);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bnv_fav_img);

        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.getMenu().getItem(1).setCheckable(false);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int position = viewPager.getCurrentItem();
                final int id = favoriteImages.get(position).getId();
                final String url = favoriteImages.get(position).getImg_url();
                final String fav_img_id = favoriteImages.get(position).getImg_id();

                switch (menuItem.getItemId()) {
                    case R.id.share:
                        shareImage(url,context);
                        break;

                    case R.id.download:
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
                        break;

                    case R.id.remove_favorite:
                        final int has_img = database_adapter.checkSingleFavoriteImage(fav_img_id);
                        if (has_img == 1) {
                            deleteFavoriteImage(fav_img_id,position);
                        }
                        break;
                }

                return true;
            }
        });

        Intent intent = getIntent();
        fav_img_position = intent.getIntExtra("fav_img_position",0);
        fav_img_url = intent.getStringExtra("fav_img_url");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(fav_img_position);
    }


        static public void shareImage(String url, final Context context) {
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


    public int deleteFavoriteImage(String id,int position)
    {
        int count = database_adapter.deleteFavoriteImage(id);
        Toast.makeText(FavoriteImageFullScreen.this,"Removed From FavoriteImages",Toast.LENGTH_SHORT).show();

        /*if (count > 0)
        {
            favoriteImageAdapter.deleteFavoriteImage(position);
        }*/
        return count;
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            bundle.putString("FavoriteImage", favoriteImages.get(i).getImg_url());
            FavImageFragment favImageFragment = new FavImageFragment();
            favImageFragment.setArguments(bundle);
            return favImageFragment;
        }

        @Override
        public int getCount() {
            return favoriteImages.size();
        }

       /* @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(position);
        }*/
    }
}
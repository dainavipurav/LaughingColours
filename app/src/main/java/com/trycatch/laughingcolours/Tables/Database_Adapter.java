package com.trycatch.laughingcolours.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database_Adapter extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorites_db";
    public static final int DATABASE_VERSION = 1;
    public Database_Adapter(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavoriteImages.CREATE_TABLE);
        db.execSQL(FavoriteTexts.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




    public long insertFavoriteImage(FavoriteImages favoriteImages)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoriteImages.COLUMN_IMG_ID, favoriteImages.getImg_id());
        contentValues.put(FavoriteImages.COLUMN_IMG_URL, favoriteImages.getImg_url());

        long id = database.insert(FavoriteImages.TABLE_NAME,null,contentValues);
        database.close();
        return id;
    }

    public long insertFavoriteText(FavoriteTexts favoriteTexts)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoriteTexts.COLUMN_TXT_ID, favoriteTexts.getTxt_id());
        contentValues.put(FavoriteTexts.COLUMN_TXT_POST_DESC, favoriteTexts.getTxtpost_desc());

        long id = database.insert(FavoriteTexts.TABLE_NAME,null,contentValues);
        database.close();
        return id;
    }





    public int deleteFavoriteImage(String id)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        String[] whereargs = {id};

        int count_delete = database.delete(FavoriteImages.TABLE_NAME, FavoriteImages.COLUMN_IMG_ID + " = ?",whereargs);

        database.close();

        return count_delete;
    }

    public int deleteFavoriteText(String id)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        String[] whereargs = {id};

        int count_delete = database.delete(FavoriteTexts.TABLE_NAME, FavoriteTexts.COLUMN_TXT_ID + " = ?",whereargs);

        database.close();

        return count_delete;
    }




    public List<FavoriteImages> getAllFavoriteImages()
    {
        SQLiteDatabase database = this.getWritableDatabase();

        List<FavoriteImages> favoriteImagesList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + FavoriteImages.TABLE_NAME , null);

        if (cursor.moveToFirst())
        {
            do {
                FavoriteImages favoriteImages = new FavoriteImages();
                favoriteImages.setId(cursor.getInt(cursor.getColumnIndex(FavoriteImages.COLUMN_ID)));
                favoriteImages.setImg_id(cursor.getString(cursor.getColumnIndex(FavoriteImages.COLUMN_IMG_ID)));
                favoriteImages.setImg_url(cursor.getString(cursor.getColumnIndex(FavoriteImages.COLUMN_IMG_URL)));

                favoriteImagesList.add(favoriteImages);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return favoriteImagesList;
    }

    public List<FavoriteTexts> getAllFavoriteTexts()
    {
        SQLiteDatabase database = this.getWritableDatabase();

        List<FavoriteTexts> favoriteTextsList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + FavoriteTexts.TABLE_NAME , null);

        if (cursor.moveToFirst())
        {
            do {
                FavoriteTexts favoriteTexts = new FavoriteTexts();
                favoriteTexts.setId(cursor.getInt(cursor.getColumnIndex(FavoriteTexts.COLUMN_ID)));
                favoriteTexts.setTxt_id(cursor.getString(cursor.getColumnIndex(FavoriteTexts.COLUMN_TXT_ID)));
                favoriteTexts.setTxtpost_desc(cursor.getString(cursor.getColumnIndex(FavoriteTexts.COLUMN_TXT_POST_DESC)));

                favoriteTextsList.add(favoriteTexts);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return favoriteTextsList;
    }




    public int checkSingleFavoriteImage(String id)
    {
        try {

            SQLiteDatabase database = this.getReadableDatabase();

            String[] columns = {FavoriteImages.COLUMN_ID, FavoriteImages.COLUMN_IMG_ID, FavoriteImages.COLUMN_IMG_URL};

            Cursor cursor = database.query(FavoriteImages.TABLE_NAME,columns, FavoriteImages.COLUMN_IMG_ID + " = " + '\'' + id + '\'', null,null,null,null);

            if (cursor.moveToFirst())
            {
                cursor.close();
                return 1;
            }
        }
        catch (Exception e)
        {
            Log.e("dberror",e.getLocalizedMessage());
        }

        return 0;
    }

    public int checkSingleFavoriteText(String id)
    {
        try {

            SQLiteDatabase database = this.getReadableDatabase();

            String[] columns = {FavoriteTexts.COLUMN_ID, FavoriteTexts.COLUMN_TXT_ID, FavoriteTexts.COLUMN_TXT_POST_DESC};

            Cursor cursor = database.query(FavoriteTexts.TABLE_NAME,columns, FavoriteTexts.COLUMN_TXT_ID + " = " + '\'' + id + '\'', null,null,null,null);

            if (cursor.moveToFirst())
            {
                cursor.close();
                return 1;
            }
        }
        catch (Exception e)
        {
            Log.e("dberror",e.getLocalizedMessage());
        }

        return 0;
    }





    /*public FavoriteImages checkSingleFavoriteImage(String id)
    {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = {FavoriteImages.COLUMN_ID,FavoriteImages.COLUMN_IMG_ID,FavoriteImages.COLUMN_IMG_URL};

        Cursor cursor = database.query(FavoriteImages.TABLE_NAME,columns,FavoriteImages.COLUMN_IMG_ID + " = " + id , null,null,null,null);

        FavoriteImages favorites = new FavoriteImages();

        if (cursor.moveToFirst())
        {
            favorites.setId(cursor.getInt(cursor.getColumnIndex(FavoriteImages.COLUMN_ID)));
            favorites.setImg_id(cursor.getString(cursor.getColumnIndex(FavoriteImages.COLUMN_IMG_ID)));
            favorites.setImg_url(cursor.getString(cursor.getColumnIndex(FavoriteImages.COLUMN_IMG_URL)));
        }

        cursor.close();
        database.close();
        return favorites;
    }*/
}

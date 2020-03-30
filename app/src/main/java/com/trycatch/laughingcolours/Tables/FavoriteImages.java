package com.trycatch.laughingcolours.Tables;

public class FavoriteImages {

    public static final String TABLE_NAME = "favorite_images_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMG_ID = "img_id";
    public static final String COLUMN_IMG_URL = "img_url";

    int id;
    String img_id,img_url;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + COLUMN_IMG_ID + " TEXT , "
            + COLUMN_IMG_URL + " TEXT )";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}

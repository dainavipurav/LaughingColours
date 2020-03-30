package com.trycatch.laughingcolours.Tables;

public class FavoriteTexts {

    public static final String TABLE_NAME = "favorites_texts_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TXT_ID = "txt_id";
    public static final String COLUMN_TXT_POST_DESC = "txt_post_desc";

    int id;
    String txt_id,txtpost_desc;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + COLUMN_TXT_ID + " TEXT , "
            + COLUMN_TXT_POST_DESC + " TEXT )";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxt_id() {
        return txt_id;
    }

    public void setTxt_id(String txt_id) {
        this.txt_id = txt_id;
    }

    public String getTxtpost_desc() {
        return txtpost_desc;
    }

    public void setTxtpost_desc(String txtpost_desc) {
        this.txtpost_desc = txtpost_desc;
    }
}

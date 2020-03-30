package com.trycatch.laughingcolours.PojoClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TextPojo implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("post_desc")
    @Expose
    private String postDesc;

    protected TextPojo(Parcel in) {
        id = in.readString();
        postDesc = in.readString();
    }

    public static final Creator<TextPojo> CREATOR = new Creator<TextPojo>() {
        @Override
        public TextPojo createFromParcel(Parcel in) {
            return new TextPojo(in);
        }

        @Override
        public TextPojo[] newArray(int size) {
            return new TextPojo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(postDesc);
    }
}

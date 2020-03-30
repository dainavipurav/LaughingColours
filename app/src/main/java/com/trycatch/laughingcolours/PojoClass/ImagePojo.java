
package com.trycatch.laughingcolours.PojoClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagePojo implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("images")
    @Expose
    private String images;

    protected ImagePojo(Parcel in) {
        id = in.readString();
        images = in.readString();
    }

    public static final Creator<ImagePojo> CREATOR = new Creator<ImagePojo>() {
        @Override
        public ImagePojo createFromParcel(Parcel in) {
            return new ImagePojo(in);
        }

        @Override
        public ImagePojo[] newArray(int size) {
            return new ImagePojo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(images);
    }
}

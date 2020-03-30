package com.trycatch.laughingcolours.Networks;

import com.trycatch.laughingcolours.PojoClass.CategoryPojo;
import com.trycatch.laughingcolours.PojoClass.ImagePojo;
import com.trycatch.laughingcolours.PojoClass.TextPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Interface
{

    @GET("/v3/mob_jokes/category_list")
    Call<List<CategoryPojo>> getCategories();

    @GET("/v3/mob_jokes/image_post_list?")
    Call<ArrayList<ImagePojo>> getImages(@Query("category_id") String category_id);

    @GET("/v3/mob_jokes/text_post_list?")
    Call<ArrayList<TextPojo>> getTexts(@Query("category_id") String category_id);

}


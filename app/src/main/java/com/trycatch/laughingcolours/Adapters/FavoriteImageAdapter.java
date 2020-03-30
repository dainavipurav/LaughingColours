package com.trycatch.laughingcolours.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.trycatch.laughingcolours.R;
import com.trycatch.laughingcolours.Tables.FavoriteImages;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteImageAdapter extends RecyclerView.Adapter<FavoriteImageAdapter.FavoriteImage_MyViewHolder> {

    Context context;
    List<FavoriteImages> favoriteImages;
    FavImg_OnItemClickListener favImg_onItemClickListener;

    public interface FavImg_OnItemClickListener
    {
        void onFavImageClick(int fav_img_position , String fav_img_url);
    }

    public void setOnItemClickListener(FavImg_OnItemClickListener listener)
    {
        this.favImg_onItemClickListener = listener;
    }

    public void deleteFavoriteImage(int position)
    {
        favoriteImages.remove(position);
        //notifyItemRemoved(position);
    }

    public FavoriteImageAdapter(List<FavoriteImages> allFavoriteImages, Context context) {
        favoriteImages = new ArrayList<>();
        this.context = context;
    }

    public void setFavoriteImages(List<FavoriteImages> favoriteImages)
    {
        this.favoriteImages = favoriteImages;
        notifyDataSetChanged();
    }

    /*deleteFavoriteImage(int position)
    {
        favoriteImages.remove(position);
        notifyItemMoved(position);
    }*/

    @NonNull
    @Override
    public FavoriteImage_MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_img_demo,viewGroup,false);
        return new FavoriteImage_MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteImage_MyViewHolder favoriteImage_myViewHolder, int i) {
        final FavoriteImages current_position = favoriteImages.get(i);
        final String imgurl = current_position.getImg_url();

        Picasso.with(context).load(imgurl).into(favoriteImage_myViewHolder.fav_iv, new Callback() {
            @Override
            public void onSuccess() {
                favoriteImage_myViewHolder.fav_pb.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteImages.size();
    }

    public class FavoriteImage_MyViewHolder extends RecyclerView.ViewHolder {
        ImageView fav_iv;
        ProgressBar fav_pb;
        CardView fav_cv;
        public FavoriteImage_MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fav_cv = itemView.findViewById(R.id.favorite_img_demo_cv);
            fav_iv = itemView.findViewById(R.id.favorite_img_demo_iv);
            fav_pb = itemView.findViewById(R.id.favorite_img_demo_pb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (favImg_onItemClickListener != null)
                    {
                        int position = getAdapterPosition();
                        final FavoriteImages current_item = favoriteImages.get(position);
                        String url = current_item.getImg_url();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            favImg_onItemClickListener.onFavImageClick(position , url);
                        }
                    }
                }
            });
         }
    }
}

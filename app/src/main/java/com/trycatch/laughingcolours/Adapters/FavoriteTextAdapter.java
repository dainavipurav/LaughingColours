package com.trycatch.laughingcolours.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trycatch.laughingcolours.R;
import com.trycatch.laughingcolours.Tables.FavoriteTexts;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTextAdapter extends RecyclerView.Adapter<FavoriteTextAdapter.FavoriteText_MyViewHolder> {

    Context context;
    List<FavoriteTexts> favoriteTexts;
    FavTxt_OnItemClickListener favTxt_onItemClickListener;

    public interface FavTxt_OnItemClickListener
    {
        void onFavTextClick(int fav_txt_position , String fav_txt_post_desc);
    }

    public void setOnItemClickListener(FavTxt_OnItemClickListener listener)
    {
        this.favTxt_onItemClickListener = listener;
    }

    public FavoriteTextAdapter(List<FavoriteTexts> allFavoriteTexts, Context context) {
        favoriteTexts = new ArrayList<>();
        this.context = context;
    }

    public void setFavoriteTexts(List<FavoriteTexts> favoriteTexts)
    {
        this.favoriteTexts = favoriteTexts;
        notifyDataSetChanged();
    }

    public void deleteFavoriteText(int position)
    {
        favoriteTexts.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public FavoriteText_MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_txt_demo,viewGroup,false);
        return new FavoriteText_MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteText_MyViewHolder favoriteText_myViewHolder, int i) {
        final FavoriteTexts current_position = favoriteTexts.get(i);
        final String post_desc = current_position.getTxtpost_desc();

        favoriteText_myViewHolder.fav_tv.setText(post_desc);
        favoriteText_myViewHolder.fav_pb.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return favoriteTexts.size();
    }

    public class FavoriteText_MyViewHolder extends RecyclerView.ViewHolder {
        TextView fav_tv;
        ProgressBar fav_pb;
        CardView fav_cv;
        public FavoriteText_MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fav_cv = itemView.findViewById(R.id.favorite_txt_demo_cv);
            fav_tv = itemView.findViewById(R.id.favorite_txt_demo_tv);
            fav_pb = itemView.findViewById(R.id.favorite_txt_demo_pb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (favTxt_onItemClickListener != null)
                    {
                        int position = getAdapterPosition();
                        final FavoriteTexts current_item = favoriteTexts.get(position);
                        String post_desc = current_item.getTxtpost_desc();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            favTxt_onItemClickListener.onFavTextClick(position , post_desc);
                        }
                    }
                }
            });
         }
    }
}

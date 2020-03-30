package com.trycatch.laughingcolours.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.trycatch.laughingcolours.PojoClass.ImagePojo;
//import com.example.purav.myapplication.PojoClass.ImagePojo;
//import com.example.purav.myapplication.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trycatch.laughingcolours.R;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Img_MyViewHolder> {

    private List<ImagePojo> imagePojos;
    private Context img_context;
    private Img_OnItemClickListener img_listener;

    public interface Img_OnItemClickListener
    {
        void onImageClick(int img_position , String img_url);
    }

    public void setOnItemClickListener(Img_OnItemClickListener listener)
    {
        this.img_listener = listener;
    }

    public ImageAdapter(List<ImagePojo> imagePojos, Context img_context)
    {
        this.imagePojos = imagePojos;
        this.img_context = img_context;
    }

    @NonNull
    @Override
    public Img_MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.img_demo, viewGroup , false);
        return new Img_MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Img_MyViewHolder img_viewHolder, int img_position) {
        final ImagePojo img_current_imagePojo = imagePojos.get(img_position);
        final String img_imgurl = img_current_imagePojo.getImages();
        //final String image_id = img_current_imagePojo.getId();
        Picasso.with(img_context).load(img_imgurl).into(img_viewHolder.img_imageView, new Callback() {
            @Override
            public void onSuccess() {
                img_viewHolder.img_progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return imagePojos.size();
    }

    public class Img_MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_imageView;
        ProgressBar img_progress;
        public Img_MyViewHolder(@NonNull View img_itemView) {
            super(img_itemView);
            img_imageView = img_itemView.findViewById(R.id.img_demo_iv);
            img_progress = img_itemView.findViewById(R.id.img_demo_pb);

            img_itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (img_listener != null)
                    {
                        int position = getAdapterPosition();
                        final ImagePojo current_item = imagePojos.get(position);
                        String url = current_item.getImages();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            img_listener.onImageClick(position , url);
                        }
                    }
                }
            });
        }
    }
}

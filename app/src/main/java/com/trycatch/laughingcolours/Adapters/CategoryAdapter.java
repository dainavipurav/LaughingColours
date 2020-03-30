package com.trycatch.laughingcolours.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trycatch.laughingcolours.PojoClass.CategoryPojo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trycatch.laughingcolours.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Cat_MyViewHolder> {

    private List<CategoryPojo> categories;
    private Context cat_context;
    private Cat_OnItemClickListener cat_listener;

    public interface Cat_OnItemClickListener
    {
        void onCategoryClick(String cat_id);
    }

    public void setOnItemClickListener(Cat_OnItemClickListener listener)
    {
        this.cat_listener = listener;
    }

    public CategoryAdapter(List<CategoryPojo> categories , Context cat_context)
    {
        this.categories = categories;
        this.cat_context = cat_context;
    }

    @NonNull
    @Override
    public Cat_MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_demo, viewGroup , false);
        return new Cat_MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Cat_MyViewHolder cat_viewHolder, int cat_position)
    {
        final CategoryPojo cat_current_item = categories.get(cat_position);
        final String cat_imgurl = cat_current_item.getCatImage();
        final String category_id = cat_current_item.getId();

        Picasso.with(cat_context).load(cat_imgurl).into(cat_viewHolder.cat_imageView, new Callback() {
            @Override
            public void onSuccess() {
                cat_viewHolder.cat_progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

        cat_viewHolder.cat_textView.setText(categories.get(cat_position).getCatName());

        /*cat_viewHolder.cat_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cat_id = category_id;
                Intent cat_intent = new Intent(cat_context, MainTab.class);
                cat_intent.putExtra("category_id" ,cat_id);
                cat_context.startActivity(cat_intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class Cat_MyViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout linearLayout;
        CardView cat_cardView;
        ImageView cat_imageView;
        TextView cat_textView;
        ProgressBar cat_progress;

        public Cat_MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_imageView = itemView.findViewById(R.id.cat_demo_iv);
            cat_textView = itemView.findViewById(R.id.cat_demo_tv);
            //cat_cardView = itemView.findViewById(R.id.cat_demo_cv);
            cat_progress = itemView.findViewById(R.id.cat_demo_pb);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cat_listener != null)
                    {
                        int position = getAdapterPosition();
                        final CategoryPojo current_item = categories.get(position);
                        String id = current_item.getId();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            cat_listener.onCategoryClick(id);
                        }
                    }
                }
            });

        }
    }
}

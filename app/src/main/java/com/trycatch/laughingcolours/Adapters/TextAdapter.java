package com.trycatch.laughingcolours.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trycatch.laughingcolours.PojoClass.TextPojo;
import com.trycatch.laughingcolours.R;

import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.Txt_MyViewHolder> {
    List<TextPojo> textPojos;
    Context txt_context;
    Txt_OnItemClickListener txt_listener;

    public interface Txt_OnItemClickListener
    {
        void onTextClick(int position);
    }

    public void setOnItemClickListener(Txt_OnItemClickListener text_listener)
    {
        this.txt_listener = text_listener;
    }

    public TextAdapter(List<TextPojo> textPojos , Context txt_context) {
        this.textPojos = textPojos;
        this.txt_context = txt_context;
    }

    @NonNull
    @Override
    public Txt_MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.txt_demo, viewGroup , false);
        return new Txt_MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Txt_MyViewHolder txt_viewHolder, int txt_position) {

        final TextPojo txt_current_imagePojo = textPojos.get(txt_position);
        final String txt_content = txt_current_imagePojo.getPostDesc();
        txt_viewHolder.txt_textview.setText(txt_content);
        txt_viewHolder.txt_progress.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return textPojos.size();
    }

    public class Txt_MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_textview;
        ProgressBar txt_progress;
        public Txt_MyViewHolder(@NonNull View txt_itemView) {
            super(txt_itemView);
            txt_textview = txt_itemView.findViewById(R.id.txt_demo_tv);
            txt_progress = txt_itemView.findViewById(R.id.txt_demo_pb);

            txt_itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txt_listener != null)
                    {
                        int txt_position = getAdapterPosition();
                        if (txt_position != RecyclerView.NO_POSITION)
                        {
                            txt_listener.onTextClick(txt_position);
                        }
                    }
                }
            });
        }
    }
}

package com.trycatch.laughingcolours.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trycatch.laughingcolours.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavTextFragment extends Fragment {


    public FavTextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        String text = bundle.getString("FavoriteText");

        TextView textView = view.findViewById(R.id.frag_fav_txt_tv);

        ProgressBar progressBar = view.findViewById(R.id.frag_fav_txt_pb);

        textView.setText(text);

        progressBar.setVisibility(View.GONE);

    }
}

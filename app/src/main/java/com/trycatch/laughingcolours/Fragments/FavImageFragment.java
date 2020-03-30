package com.trycatch.laughingcolours.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trycatch.laughingcolours.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavImageFragment extends Fragment {


    public FavImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        String image = bundle.getString("FavoriteImage");

        ImageView imageView = view.findViewById(R.id.frag_fav_img_iv);

        final ProgressBar progressBar = view.findViewById(R.id.frag_fav_img_pb);

        Picasso.with(getActivity().getApplicationContext())
                .load(image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}

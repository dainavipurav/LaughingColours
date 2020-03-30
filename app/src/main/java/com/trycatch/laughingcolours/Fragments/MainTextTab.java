package com.trycatch.laughingcolours.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trycatch.laughingcolours.Activities.TextFullScreen;
import com.trycatch.laughingcolours.PojoClass.TextPojo;
import com.trycatch.laughingcolours.Networks.API_Client;
import com.trycatch.laughingcolours.Adapters.TextAdapter;
import com.trycatch.laughingcolours.Networks.API_Interface;
import com.trycatch.laughingcolours.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainTextTab extends Fragment implements TextAdapter.Txt_OnItemClickListener {

    RecyclerView recyclerView;
    TextAdapter textAdapter;
    API_Interface api_interface;
    ArrayList<TextPojo> txt_data = new ArrayList<>();
    ArrayList<TextPojo> textPojos;
    Context context = this.getActivity();
    private RecyclerView.LayoutManager layoutManager;

    public MainTextTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_text_tab, container, false);
        recyclerView = rootView.findViewById(R.id.txt_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView = rootView.findViewById(R.id.txt_recycler);
        textPojos = new ArrayList<>();
        txt_generateList(rootView);
        textAdapter = new TextAdapter(textPojos, getContext());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textAdapter = new TextAdapter(textPojos , getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(textAdapter);
    }

    public void txt_generateList(View rootView)
    {
        api_interface = API_Client.getAPI_Client().create(API_Interface.class);

        String category_id = getActivity().getIntent().getStringExtra("cat_id");
        Call<ArrayList<TextPojo>> call = api_interface.getTexts(category_id);

        call.enqueue(new Callback<ArrayList<TextPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<TextPojo>> call, Response<ArrayList<TextPojo>> response) {
                txt_data = response.body();
                Texts(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<TextPojo>> call, Throwable throwable) {
                Toast.makeText(getActivity(),"Check",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Texts (ArrayList<TextPojo> textPojos)
    {
        textAdapter = new TextAdapter(textPojos , getContext());
        recyclerView.setAdapter(textAdapter);
        textAdapter.setOnItemClickListener(MainTextTab.this);
    }

    @Override
    public void onTextClick(int txt_position) {
        Intent intent = new Intent(this.getActivity(), TextFullScreen.class);
        intent.putParcelableArrayListExtra("txt_data",txt_data);
        intent.putExtra("txt_position",txt_position);
        startActivity(intent);
    }
}
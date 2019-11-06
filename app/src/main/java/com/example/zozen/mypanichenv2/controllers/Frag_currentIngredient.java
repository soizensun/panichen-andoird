package com.example.zozen.mypanichenv2.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zozen.mypanichenv2.Models.Ingredient;
import com.example.zozen.mypanichenv2.R;
import com.example.zozen.mypanichenv2.adapters.Adapter_current_Ingredient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Frag_currentIngredient extends Fragment {

    public RecyclerView recyclerView;
    public Adapter_current_Ingredient adapter_current_ingredient;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String[] disPlayName;

    final List<Ingredient> mokUpList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        disPlayName = firebaseUser.getEmail().split("@");


        View view =  inflater.inflate(R.layout.frag_currentingredient, container, false);
        recyclerView = view.findViewById(R.id.ingredientLV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        getData();
        return view;
    }

    public void getData(){
        db.collection("ingredients").whereEqualTo("user", disPlayName[0]).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
                    Log.d("fireLig", "onEvent: " + e.getMessage());
                }
                mokUpList.clear();
                for (DocumentSnapshot doc : documentSnapshots){
                    String name = doc.getString("name");
                    Double amount = doc.getDouble("amount");
                    String unit = doc.getString("unit");
                    String id = doc.getId() + "";
                    String user = "aaa";
                    mokUpList.add(new Ingredient(user, name, amount, unit ,id));
                    adapter_current_ingredient = new Adapter_current_Ingredient(getActivity(), mokUpList);
                    recyclerView.setAdapter(adapter_current_ingredient);
                }
            }
        });
    }

}

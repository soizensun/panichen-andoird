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

import com.example.zozen.mypanichenv2.Models.Menu;
import com.example.zozen.mypanichenv2.R;
import com.example.zozen.mypanichenv2.adapters.Adapter_suggest_menu;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Frag_suggestMenu extends Fragment {
    RecyclerView recyclerView;
    private Adapter_suggest_menu Adapter_suggest_menu;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_suggestmenu, container, false);

        final List<Menu> tempList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.menuRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        db.collection("menus").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
                    Log.d("fireLig", "onEvent: " + e.getMessage());
                }
                for (DocumentSnapshot doc : documentSnapshots){
                    String nameMenu = doc.getString("name");
                    String type = doc.getString("type");
                    String id = doc.getId();

                    tempList.add(new Menu(nameMenu, type, id));
                    Adapter_suggest_menu = new Adapter_suggest_menu(getActivity(), tempList);
                    recyclerView.setAdapter(Adapter_suggest_menu);
                }
            }
        });
        return view;
    }
}

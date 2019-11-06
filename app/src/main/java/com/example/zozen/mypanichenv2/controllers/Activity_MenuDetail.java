package com.example.zozen.mypanichenv2.controllers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.zozen.mypanichenv2.Models.Ingredient;
import com.example.zozen.mypanichenv2.R;
import com.example.zozen.mypanichenv2.adapters.Adapter_detail_menu;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Activity_MenuDetail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_detail_menu Adapter_detail_menu;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__menu_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String menuName = getIntent().getStringExtra("menuName");
        String menuID = getIntent().getStringExtra("idMenu");

        getSupportActionBar().setTitle(menuName);

        final List<Ingredient> test = new ArrayList<>();

        recyclerView = findViewById(R.id.menuDetailView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db.collection("menus").document(menuID).collection("ingredients").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
//                    Log.d("fireLig", "onEvent: " + e.getMessage());
                }
                for (DocumentSnapshot doc : documentSnapshots){
                    String nameMenu = doc.getString("name");
                    Double amount = doc.getDouble("amount");
                    String unit = doc.getString("unit");

                    test.add(new Ingredient("", nameMenu, amount, unit));
                    Adapter_detail_menu = new Adapter_detail_menu(context, test);
                    recyclerView.setAdapter(Adapter_detail_menu);
                }
            }
        });

    }
}

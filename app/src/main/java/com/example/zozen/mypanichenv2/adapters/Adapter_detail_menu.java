package com.example.zozen.mypanichenv2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zozen.mypanichenv2.Models.Ingredient;
import com.example.zozen.mypanichenv2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashSet;
import java.util.List;

public class Adapter_detail_menu extends RecyclerView.Adapter<Adapter_detail_menu.MenuDetailViewHolder> {
    private Context context;
    private List<Ingredient> ingredientList;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String[] disPlayName;

    public Adapter_detail_menu(Context context, List<Ingredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public MenuDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.a_card_of_ingredient, null);
        return new MenuDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuDetailViewHolder menuDetailViewHolder, int i) {
        final Ingredient ingredient = ingredientList.get(i);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        disPlayName = firebaseUser.getEmail().split("@");

        db.collection("ingredients").whereEqualTo("user", disPlayName[0]).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                final HashSet<Ingredient> currentIngredientList = new HashSet<>();
                if(e != null){
                    Log.d("fireLig", "onEvent: " + e.getMessage());
                }
                int count = 0;
                for (DocumentSnapshot doc : documentSnapshots){
                    count ++;
                    String name = doc.getString("name");
                    Double amount = doc.getDouble("amount");
                    String unit = doc.getString("unit");

                    Log.d("Ing1--", "onEvent: " + name + " " + amount + " " + unit + " " + count);
                    currentIngredientList.add(new Ingredient("", name, amount, unit));

                    menuDetailViewHolder.nameTVC.setText(ingredient.getName());
                    menuDetailViewHolder.amountTVC.setText(ingredient.getAmount() + "");
                    menuDetailViewHolder.unitTVC.setText(ingredient.getUnit());

                    if(ingredient.getName().equals(name)){
                        if(ingredient.getAmount() <= amount){
                            if(ingredient.getUnit().equals(unit)){
                                menuDetailViewHolder.ingredientLayout.setBackgroundColor(0xFF82E0AA);
                                menuDetailViewHolder.idTVC.setTextColor(0xFF82E0AA);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class MenuDetailViewHolder extends RecyclerView.ViewHolder{
        TextView nameTVC, amountTVC, unitTVC, idTVC;
        RelativeLayout ingredientLayout;

        public MenuDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientLayout = itemView.findViewById(R.id.ingredientLayout);
            nameTVC = itemView.findViewById(R.id.nameTVC);
            amountTVC = itemView.findViewById(R.id.amountTVC);
            unitTVC = itemView.findViewById(R.id.unitTVC);
            idTVC = itemView.findViewById(R.id.idTVC);

        }
    }
}

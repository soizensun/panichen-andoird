package com.example.zozen.mypanichenv2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zozen.mypanichenv2.Models.Ingredient;
import com.example.zozen.mypanichenv2.Models.Menu;
import com.example.zozen.mypanichenv2.R;
import com.example.zozen.mypanichenv2.controllers.Activity_MenuDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Adapter_suggest_menu extends RecyclerView.Adapter<Adapter_suggest_menu.MenuViewHolder>{
    private Context context;
    private List<Menu> menuList;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String[] disPlayName;

    public Adapter_suggest_menu(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.a_care_of_menu, null);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder menuViewHolder, int i) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        disPlayName = firebaseUser.getEmail().split("@");

        final Menu menu = menuList.get(i);
        menuViewHolder.nameMenuTVC.setText(menu.getName());
        menuViewHolder.typeTVC.setText(menu.getType());
        menuViewHolder.idTVC.setText(menu.getID());

        db.collection("ingredients").whereEqualTo("user", disPlayName[0]).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                final HashSet<Ingredient> currentIngredientList = new HashSet<>();
                if(e != null){
                    Log.d("fireLig", "onEvent: " + e.getMessage());
                }
                int count = 0;
                for (DocumentSnapshot doc : queryDocumentSnapshots){
                    count ++;
                    String name = doc.getString("name");
                    Double amount = doc.getDouble("amount");
                    String unit = doc.getString("unit");

                    Log.d("Ing1", "onEvent: " + name + " " + amount + " " + unit + " " + count);
                    Log.d("sizeSet", currentIngredientList.size() + "");
                    currentIngredientList.add(new Ingredient("", name, amount, unit));
                }
                db.collection("menus").document(menu.getID()).collection("ingredients").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<Ingredient> menuIngredient = new ArrayList<>();
                        if(e != null){
                            Log.d("fireLig", "onEvent: " + e.getMessage());
                        }
                        int count = 0;
                        for (DocumentSnapshot doc : queryDocumentSnapshots){
                            count ++;
                            String name = doc.getString("name");
                            Double amount = doc.getDouble("amount");
                            String unit = doc.getString("unit");
//                                Log.d("Ing2", "onEvent: " + name + " " + amount + " " + unit + " " + count);
                            menuIngredient.add(new Ingredient("", name, amount, unit));

                        }
//                        Log.d("Ing2", "--" + menuIngredient.toString() + "");
//                        Log.d("Ing33", "all" + currentIngredientList.toString() + "");
                        int check = 0;
                        for (Ingredient ingredient : currentIngredientList) {
                            for (Ingredient i : menuIngredient) {
                                if(check == 0){
                                    menuViewHolder.menuCard.setBackgroundColor(0xFFF4ECF7);
                                    menuViewHolder.idTVC.setTextColor(0xFFF4ECF7);
                                    menuViewHolder.nameMenuTVC.setTextColor(0x99000000);
                                    menuViewHolder.typeTVC.setTextColor(0x99000000);
                                }
                                if(ingredient.getName().equals(i.getName())
                                    && ingredient.getAmount() >= i.getAmount()
                                    && ingredient.getUnit().equals(i.getUnit()))
                                {
                                    check ++;
                                    if(check == menuIngredient.size()){
                                        Log.d("greencheck", "check in green : " + check);
                                        // green
                                        menuViewHolder.menuCard.setBackgroundColor(0xFF82E0AA);
                                        menuViewHolder.idTVC.setTextColor(0xFF82E0AA);
                                        menuViewHolder.nameMenuTVC.setTextColor(0x88000000);
                                        menuViewHolder.typeTVC.setTextColor(0x88000000);
                                    }
                                    if(check < menuIngredient.size() && check != 0) {
                                        // yellow
                                        Log.d("yellocheck", "check in yellow : " + check);
                                        menuViewHolder.menuCard.setBackgroundColor(0xFFFAD7A0);
                                        menuViewHolder.idTVC.setTextColor(0xFFFAD7A0);
                                        menuViewHolder.nameMenuTVC.setTextColor(0x99000000);
                                        menuViewHolder.typeTVC.setTextColor(0x99000000);
                                    }
//                                    else{
//                                        menuViewHolder.menuCard.setBackgroundColor(0xFFF4ECF7);
//                                        menuViewHolder.idTVC.setTextColor(0xFFF4ECF7);
//                                        menuViewHolder.nameMenuTVC.setTextColor(0x99000000);
//                                        menuViewHolder.typeTVC.setTextColor(0x99000000);
//                                    }
                                }
                            }
                        }
                    }
                });
            }
        });

        menuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "click : " + menuViewHolder.nameMenuTVC.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Activity_MenuDetail.class);
                intent.putExtra("menuName", menuViewHolder.nameMenuTVC.getText());
                intent.putExtra("idMenu", menuViewHolder.idTVC.  getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    class MenuViewHolder extends RecyclerView.ViewHolder{
        TextView nameMenuTVC, typeTVC, idTVC;
        RelativeLayout menuCard;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            menuCard = itemView.findViewById(R.id.menuCard);
            nameMenuTVC = itemView.findViewById(R.id.nameMenuTVC);
            typeTVC = itemView.findViewById(R.id.typeTVC);
            idTVC  = itemView.findViewById(R.id.idTVC);
        }
    }

}

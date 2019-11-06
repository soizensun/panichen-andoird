package com.example.zozen.mypanichenv2.Models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseManagement {
    private Map<String, Object> tempMap, test;
    private FirebaseFirestore db;

    public FirebaseManagement() {
        db = FirebaseFirestore.getInstance();
    }

    public void addDatabase(Ingredient ingredient) {
//        Log.i("test", "addDatabase: " + ingredient.toString());
        tempMap = new HashMap<String, Object>() {};
        tempMap.put("user", ingredient.getUser());
        tempMap.put("name", ingredient.getName());
        tempMap.put("amount", ingredient.getAmount());
        tempMap.put("unit", ingredient.getUnit());

        db.collection("ingredients")
                .add(tempMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("success", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("fail", "Error adding document", e);
                    }
                });

    }

    public ArrayList getData() {
        final ArrayList<Ingredient> ingredientsList = new ArrayList();
        db.collection("menu")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("name---", document.getData().get("name").toString());
                                String tmpuser = (document.getData().get("user").toString());
                                String tmpName = (document.getData().get("name").toString());
                                Double tmpAmount = (Double.parseDouble(document.getData().get("amount") + ""));
                                String tmpUnit = (document.getData().get("unit").toString());

                                Ingredient tempIngredient = new Ingredient(tmpuser, tmpName, tmpAmount, tmpUnit);
                                ingredientsList.add(tempIngredient);
                            }
                        } else {
                            Log.w("error", "Error getting documents.", task.getException());
                        }
                    }
                });
        Log.i("##", "ingredient form " + ingredientsList);
        return ingredientsList;
    }
}

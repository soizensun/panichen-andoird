package com.example.zozen.mypanichenv2.controllers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zozen.mypanichenv2.Models.FirebaseManagement;
import com.example.zozen.mypanichenv2.Models.Ingredient;
import com.example.zozen.mypanichenv2.R;

import java.util.ArrayList;

public class Activity_Add_Ingredient extends AppCompatActivity {
    private FloatingActionButton addIngredient;
    private Spinner unitSpinner;
    private EditText ingredientNameET;
    private EditText ingredientAmountET;

    private FirebaseManagement firebaseManagement;
    private Ingredient ingredient;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add__ingredient);
        getSupportActionBar().setTitle("Add ingredient");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        unitSpinner = findViewById(R.id.unitSpinner);
        ingredientNameET = findViewById(R.id.ingredientNameET);
        ingredientAmountET = findViewById(R.id.ingredientAmountET);

        ingredient = new Ingredient();
        firebaseManagement = new FirebaseManagement();
        userName = getIntent().getStringExtra("userName");

        ArrayList<String> unitList = new ArrayList<>();
        unitList.add(0, " - CHOOSE UNIT -");
        unitList.add("milliliter  - - ml");
        unitList.add("liter  - - l");
        unitList.add("cubic centimeter  - - cc");
        unitList.add("gallon  - - gl");
        unitList.add("gram  - - g");

        final ArrayAdapter dataAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, unitList);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        unitSpinner.setAdapter(dataAdapter);
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getItemAtPosition(position).equals(" - CHOOSE UNIT -")){
                    String unit = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), unit, Toast.LENGTH_SHORT).show();
                    String[] tmpUnit = unit.split(" - - ");
                    ingredient.setUnit(tmpUnit[1]);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void handleAddIngredientButton(View view){
        String ingredientName =  ingredientNameET.getText().toString();
        String ingredientAmount = ingredientAmountET.getText().toString();


        if(!ingredientName.equals("") && !ingredientAmount.equals("")){
            ingredient.setName(ingredientName);
            ingredient.setAmount(Double.parseDouble(ingredientAmount));
        }
        if(TextUtils.isEmpty(ingredientName) ) {
            ingredientNameET.setError("Enter your ingredient name");
        }
        if(TextUtils.isEmpty(ingredientAmount)){
            ingredientAmountET.setError("Enter your ingredient amount");
        }
        if(ingredient.getUnit() == null){
            Toast.makeText(getApplicationContext(),"Please select your UNIT",Toast.LENGTH_SHORT).show();
        }
        if(ingredient.getName() != null && ingredient.getAmount() != null && ingredient.getUnit() != null){
            // add ingredient to firestore ingredients document
            ingredient.setUser(userName);
            firebaseManagement.addDatabase(ingredient);

            // change to Main page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}

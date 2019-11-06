package com.example.zozen.mypanichenv2.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zozen.mypanichenv2.Models.Ingredient;
import com.example.zozen.mypanichenv2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Adapter_current_Ingredient extends RecyclerView.Adapter<Adapter_current_Ingredient.IngredientViewHolder>{

    private Context context;
    private List<Ingredient> ingredientList;
    private Adapter_suggest_menu adapter_suggest_menu;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Adapter_current_Ingredient(Context context, List<Ingredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.a_card_of_ingredient, null);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientViewHolder ingredientViewHolder, int i) {
        Ingredient ingredient = ingredientList.get(i);

        ingredientViewHolder.nameTVC.setText(ingredient.getName());
        ingredientViewHolder.amountTVC.setText(ingredient.getAmount() + "");
        ingredientViewHolder.unitTVC.setText(ingredient.getUnit());
        ingredientViewHolder.idTVC.setText(ingredient.getID());

        ingredientViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Long click to delete : " + ingredientViewHolder.nameTVC.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        ingredientViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(v.getContext(), "click : " + ingredientViewHolder.nameTVC.getText(), Toast.LENGTH_SHORT).show();
                String name = (String) ingredientViewHolder.nameTVC.getText();
                String amount = (String) ingredientViewHolder.amountTVC.getText();
                String unit = (String) ingredientViewHolder.unitTVC.getText();
                String id = (String) ingredientViewHolder.idTVC.getText();
                int position = ingredientViewHolder.getLayoutPosition();
                openIsDeleteDialog(name, amount, unit, id, ingredientViewHolder, position);
                return true;
            }

        });
    }

    private void openIsDeleteDialog(String name, String amount, String unit, final String id, final IngredientViewHolder ingredientViewHolder, final int position) {
        AlertDialog.Builder isDeleteDialog = new AlertDialog.Builder(this.context)
                .setTitle("Are you sure to delete ?")
                .setMessage(name + " " + amount + "  " + unit + "  " )
                .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
//                        adapter_suggest_menu.onBindViewHolder();
                        db.collection("ingredients").document(id).delete();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "click cancel", Toast.LENGTH_SHORT).show();
                    }
                });
        isDeleteDialog.show();
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView nameTVC, amountTVC, unitTVC, idTVC;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTVC = itemView.findViewById(R.id.nameTVC);
            amountTVC = itemView.findViewById(R.id.amountTVC);
            unitTVC = itemView.findViewById(R.id.unitTVC);
            idTVC = itemView.findViewById(R.id.idTVC);
        }
    }
}

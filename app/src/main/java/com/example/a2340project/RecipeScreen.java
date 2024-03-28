package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for the placeholder page for listing recipes
 */
public class RecipeScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private CookBook cookBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("cookbook database");
        cookBook = new CookBook(mDatabase);
        Button recipeButton = findViewById(R.id.addRecipeButton);
        recipeButton.setOnClickListener(v -> showAddRecipeDialog());

        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddRecipeDialog();
            }
        });


        //nav buttons
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.bottom_recipes);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int buttonID = item.getItemId();
            if (buttonID == R.id.bottom_meals) {
                startActivity(new Intent(RecipeScreen.this, InputMealScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_recipes) {
                return true;
            } else if (buttonID == R.id.bottom_shopping) {
                startActivity(new Intent(RecipeScreen.this, ShoppingListScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_ingredients) {
                startActivity(new Intent(RecipeScreen.this, IngredientScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_profile) {
                startActivity(new Intent(RecipeScreen.this, ProfileScreen.class));
                return true;
            } else {
                return buttonID == R.id.bottom_recipes;
            }
        });
    }

    private void showAddRecipeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Recipe");

        // Inflate the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.activity_add_recipe_dialog, null);
        builder.setView(customLayout);

        // EditText variables initialization
        EditText ingredients = customLayout.findViewById(R.id.ingredientName);
        EditText name = customLayout.findViewById(R.id.recipeName);

        // create the hashmap
        builder.setPositiveButton("Add", (dialog, id) -> {
            String ingredientsStr = ingredients.getText().toString();
            String recipeName = name.getText().toString();
            if (!(ingredientsStr.equals("")) && !(recipeName.equals(""))) {
                double quantity = 0;
                String ingredientName;
                String[] ing = ingredientsStr.split(", ");
                Map<String, Double> lines = new HashMap<>();
                for (String i: ing) {
                    String[] couple = i.split(":");
                    ingredientName = couple[0];
                    try {
                        quantity = Double.parseDouble(couple[1]);
                        if (quantity > 0) {
                            lines.put(ingredientName, quantity);
                        }
                    } catch (NumberFormatException e) {
                        //if quantity input not number
                    }
                }
                // add to database
                mDatabase.orderByChild("name").equalTo(recipeName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // Ingredient does not exist, add new ingredient
                            mDatabase.push().setValue(new Recipe(recipeName, lines));
                        }
                        // If ingredient exists, do nothing
                        // maybe later add error pop up?? but not necessary right now
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // nothing needed at the moment
                    }

                });
            }
        });

        builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
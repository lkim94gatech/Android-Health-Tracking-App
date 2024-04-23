package com.example.a2340project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2340project.CookBook;
import com.example.a2340project.Observer;
import com.example.a2340project.PantryIngredientsModel;
import com.example.a2340project.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeScreen extends AppCompatActivity implements Observer{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userId;
    private ArrayList<Recipe> recipeList;
    private ArrayAdapter<Recipe> arr;
    private Map<String,Double> pantry;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //IntializesView
        setContentView(R.layout.activity_recipe);
        //Observer Registration
        PantryIngredientsModel pantryIngredientsModel = new PantryIngredientsModel();
        pantryIngredientsModel.registerObserver(this);
        //Firebase setup
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        Map<String,Double> pantry = new HashMap<>();
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("ingredients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    Ingredient ingredient = snap.getValue(Ingredient.class);
                    String ingredientName = ingredient.getName();
                    Double ingredientQuantity = ingredient.getQuantity();
                    pantry.put(ingredientName, ingredientQuantity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Recipes Fetched
        mDatabase = FirebaseDatabase.getInstance().getReference().child("cookbook database");
        //RecipeList populated tied to Array Adapter
        ArrayList<Recipe> recipeList = new ArrayList<>();
        mDatabase.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Recipe recipe = snap.getValue(Recipe.class);
                    boolean contains = false;
                    for (Recipe recipes : recipeList) {
                        if ((recipes.getName().equals(recipe.getName()))) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        recipeList.add(recipe);
                    }
                }
                for (Recipe recipe : recipeList) {
                    boolean makeable = true;
                    Map<String, Double> currRecipeIngredients = recipe.getIngredientMap();
                    for (String recipeIngredient : currRecipeIngredients.keySet()) {
                        if (!pantry.containsKey(recipeIngredient)) { //if pantry doesn't have ingredient
                            makeable = false;
                        } else {    //the ingredient is in pantry but do we have enough
                            if (!(pantry.get(recipeIngredient) >= currRecipeIngredients.get(recipeIngredient))) {
                                makeable = false;
                            }
                        }
                        recipe.setCanMake(makeable);
                        arr.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        arr = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeList);
        ListView recipeListView = findViewById(R.id.recipeList);
        recipeListView.setAdapter(arr);

        //Buttons
        Button recipeButton = findViewById(R.id.addRecipeButton);
        Switch sort = (Switch) findViewById(R.id.switch1);
        //Listeners
        recipeButton.setOnClickListener(v -> showAddRecipeDialog());
        sort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sortRecipeList(isChecked);
                arr.notifyDataSetChanged();
            }
        });
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = (Recipe) parent.getItemAtPosition(position);
                Dialog dialog = new Dialog(RecipeScreen.this);
                String ingredients = recipe.getIngredientMap().toString();
                ingredients = ingredients.replaceAll("[{\\s]", "");
                ingredients = ingredients.replaceAll("[}\\s]", "");
                ingredients = ingredients.replaceAll("=", " - ");
                String[] ingredientPairs = ingredients.split(",");
                String[] ingred = ingredientPairs;
                ArrayList<String> viewArray = new ArrayList<>();
                for (String key : recipe.getIngredientMap().keySet()) {
                    viewArray.add(key + "-" + recipe.getIngredientMap().get(key));
                }
                if (recipe.getCanMake()){
                    Intent i = new Intent(RecipeScreen.this, CookingScreen.class);
                    i.putExtra("ingredients", viewArray.toString());
                    i.putExtra("recipe", recipe.getName());
                    startActivity(i);
                } else {
                    dialog.setContentView(R.layout.activity_recipe_buy_ingredients_dialog);
                    dialog.setTitle(recipe.getName());
                    // button for buying remaining ingredients
                    Button buyButton = dialog.findViewById(R.id.buyIngredientsButton);
                    buyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (userId != null) {
                                mDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (String pair: ingred) {
                                            String[] split = pair.split(" - ");
                                            String name = split[0];
                                            double quantity = Double.parseDouble(split[1]);
                                            for (DataSnapshot snap: snapshot
                                                    .child("ingredients").getChildren()) {
                                                Ingredient ingredient = snap
                                                        .getValue(Ingredient.class);
                                                if (ingredient.getName().equals(name)) {
                                                    int finalQuantity = (int) (quantity - ingredient
                                                            .getQuantity());
                                                    boolean found = false;
                                                    for (DataSnapshot dataSnap: snapshot
                                                            .child("shopping_list")
                                                            .getChildren()) {
                                                        ShoppingListItem item = dataSnap
                                                                .getValue(ShoppingListItem.class);
                                                        if (item.getName().equals(name)) {
                                                            found = true;
                                                            dataSnap.getRef()
                                                                    .setValue(finalQuantity
                                                                            + item.getQuantity());
                                                        }
                                                    }
                                                    if (!found) {
                                                        mDatabase.child("shopping_list")
                                                                .push().setValue(new
                                                                        ShoppingListItem(name,
                                                                        finalQuantity, 0));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        return;
                                    }
                                });
                            }
                            dialog.dismiss();
                        }
                    });
                }
                dialog.show();
            }
        });

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void update() {

    }

    private void sortRecipeList(boolean isChecked) {
        if (isChecked) {
            recipeList.sort(new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    int comp;
                    if (o1.getCanMake() && !o2.getCanMake()) {
                        comp = -1;
                    } else if (!o1.getCanMake() && o2.getCanMake()) {
                        comp = 1;
                    } else {
                        comp = 0;
                    }
                    return comp;
                }
            });
        } else {
            recipeList.sort(new Comparator<Recipe>() {
                @Override
                public int compare(Recipe o1, Recipe o2) {
                    return (o1.getName().compareTo(o2.getName()));
                }
            });
        }
    }

    private void showAddRecipeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Recipe");

        // Inflate the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.activity_add_recipe_dialog,
                null);
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
                Query query = mDatabase.orderByChild("name").equalTo(recipeName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // Recipe does not exist, add new recipe
                            mDatabase.push().setValue(new Recipe(recipeName, lines));
                        }
                        // If Recipe exists, do nothing
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
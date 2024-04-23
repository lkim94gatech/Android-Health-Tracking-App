package com.example.a2340project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for the placeholder page for listing recipes
 * Observer Implementation- This method will handle updates when the
 * PantryIngredientsModel notifies it of changes
 */
public class RecipeScreen extends AppCompatActivity implements Observer {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private CookBook cookBook;
    private String user;
    private FirebaseUser currentUser;
    private ArrayAdapter<Recipe> arr;
    private List<Recipe> recipeList = new ArrayList<>();
    private PantryIngredientsModel pantryIngredientsModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeList = new ArrayList<>();
        arr = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeList);
        PantryIngredientsModel pantryIngredientsModel = new PantryIngredientsModel();
        pantryIngredientsModel.registerObserver(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("cookbook database");
        cookBook = new CookBook(mDatabase);
        Button recipeButton = findViewById(R.id.addRecipeButton);
        updateRecipeList();
        checkForCookable();
        Switch sort = (Switch) findViewById(R.id.switch1);
        ListView recipeListView = findViewById(R.id.recipeList);
        arr.notifyDataSetChanged();
        recipeListView.setAdapter(arr);
        ArrayAdapter<Recipe> arr = new ArrayAdapter<Recipe>(this,
                android.R.layout.simple_list_item_1, recipeList);
        recipeListView.setAdapter(arr);
        checkForCookable();
        updateRecipeList();
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = (Recipe) parent.getItemAtPosition(position);
                showItemDialog(recipe);
            }
        });
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = (Recipe) parent.getItemAtPosition(position);
                showItemDialog(recipe);
            }
        });
        sort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sortRecipeList(isChecked);
                arr.notifyDataSetChanged();
            }
        });
        mDatabase.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    Recipe recipe = snap.getValue(Recipe.class);
                    boolean contains = false;
                    for (Recipe recipes: recipeList) {
                        if ((recipes.getName().equals(recipe.getName()))) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        recipeList.add(recipe);
                    }
                }
                arr.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        FirebaseDatabase.getInstance().getReference()
                .child("users").child(user)
                .child("ingredients").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (Recipe recipe: recipeList) { //for each recipe
                            Map<String, Double> map = recipe.getIngredientMap();
                            boolean hasIngredients = true;
                            for (DataSnapshot snap: snapshot.getChildren()) {
                                Ingredient ingredient = snap.getValue(Ingredient.class);
                                String ing = ingredient.getName(); //ingredient name
                                if (map.containsKey(ing)) { //if ingredient is in map
                                    double pantry = ingredient.getQuantity();
                                    double recipeQuanitity = map.get(ing);
                                    if (!(pantry >= recipeQuanitity)) {
                                        hasIngredients = false;
                                    } else { // there is enough of it
                                        map.remove(ing);
                                    }
                                }
                            }
                            if (!map.isEmpty()) {
                                recipe.setCanMake(false);
                            } else {
                                recipe.setCanMake(true);
                            }
                        }
                        arr.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        recipeButton.setOnClickListener(v -> showAddRecipeDialog());

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

    // factory pattern
    private void onCookButtonClick(Recipe recipe, String mealType) {
        MealPrep meal = MealFactory.createMeal(recipe, mealType);
        meal.cook();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    @Override
    public void update() {
        // Logic to refresh the recipe list based on updated pantry ingredients
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Make sure the update is performed on the UI thread
                recipeList.clear();
                // This method 'getUpdatedRecipes()' needs to be defined in your
                // PantryIngredientsModel class
                recipeList.addAll(pantryIngredientsModel.getUpdatedRecipes());
                arr.notifyDataSetChanged(); // Notify the adapter to refresh the ListView
            }
        });
    }

    private void showItemDialog(Recipe recipe) {
        String ingredients = recipe.getIngredientMap().toString();
        ingredients = ingredients.replaceAll("[{\\s]", "");
        ingredients = ingredients.replaceAll("[}\\s]", "");
        ingredients = ingredients.replaceAll("=", " - ");
        String[] ingredientPairs = ingredients.split(",");
        String[] ingred = ingredientPairs;
        Dialog dialog = new Dialog(RecipeScreen.this);
        if (recipe.getCanMake()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(RecipeScreen.this,
                    android.R.layout.simple_list_item_1, ingredientPairs);
            dialog.setContentView(R.layout.activity_recipe_ingredients_dialog);
            dialog.setTitle(recipe.getName());
            ListView list = (ListView) dialog.findViewById(R.id.ingredientList);
            list.setAdapter(adapter);
            Button cookButton = dialog.findViewById(R.id.cookButton);

            cookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Calendar.HOUR >= 15) {
                        onCookButtonClick(recipe, "dinner");
                    } else {
                        onCookButtonClick(recipe, "breakfast");
                    }
                    arr.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
        } else {
            dialog.setContentView(R.layout.activity_recipe_buy_ingredients_dialog);
            dialog.setTitle(recipe.getName());
            // button for buying remaining ingredients
            Button buyButton = dialog.findViewById(R.id.buyIngredientsButton);
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user != null) {
                        FirebaseDatabase.getInstance().getReference()
                                .child("users").child(user)
                                .child("shopping_list").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Map<String, Double> recipeIngredients = recipe.getIngredientMap();
                                        for (Map.Entry<String, Double> entry : recipeIngredients.entrySet()) {
                                            String ingredientName = entry.getKey();
                                            Double quantityNeeded = entry.getValue();

                                            boolean found = false;
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                ShoppingListItem item = snapshot.getValue(ShoppingListItem.class);
                                                if (item.getName().equals(ingredientName)) {
                                                    // Ingredient already exists in the shopping list, update quantity
                                                    snapshot.getRef().child("quantity").setValue(item.getQuantity() + quantityNeeded);
                                                    found = true;
                                                    break;
                                                }
                                            }

                                            if (!found) {
                                                // Ingredient not found in shopping list, add it
                                                FirebaseDatabase.getInstance().getReference()
                                                        .child("users").child(user)
                                                        .child("shopping_list").push()
                                                        .setValue(new ShoppingListItem(ingredientName, quantityNeeded.intValue(), 0));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        // Handle onCancelled event
                                    }
                                });
                    }
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
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
    public void updateRecipeList() {
        FirebaseDatabase.getInstance().getReference()
                .child("users").child(user)
                .child("ingredients").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (Recipe recipe: recipeList) {
                            Map<String, Double> map = recipe.getIngredientMap();
                            for (DataSnapshot snap: snapshot.getChildren()) {
                                Ingredient ingredient = snap.getValue(Ingredient.class);
                                String ing = ingredient.getName();
                                if (map.containsKey(ing)) {
                                    double pantry = ingredient.getQuantity();
                                    double recipeQuanitity = map.get(ing);
                                    if (pantry >= recipeQuanitity) {
                                        recipe.setCanMake(true);
                                    }
                                }
                            }
                        }
                        arr.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void checkForCookable() {
        FirebaseDatabase.getInstance().getReference()
                .child("users").child(user)
                .child("ingredients").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (Recipe recipe: recipeList) { //for each recipe
                            Map<String, Double> map = recipe.getIngredientMap();
                            boolean hasIngredients = true;
                            for (DataSnapshot snap: snapshot.getChildren()) { //pantry
                                Ingredient ingredient = snap.getValue(Ingredient.class);
                                String ing = ingredient.getName(); //ingredient name
                                if (map.containsKey(ing)) { //if ingredient is in map
                                    double pantry = ingredient.getQuantity();
                                    double recipeQuanitity = map.get(ing);
                                    if (!(pantry >= recipeQuanitity)) {
                                        hasIngredients = false;
                                    } else { // there is enough of it
                                        map.remove(ing);
                                    }
                                }
                            }
                            if (!map.isEmpty()) {
                                recipe.setCanMake(false);
                            } else {
                                recipe.setCanMake(true);
                            }
                        }
                        arr.notifyDataSetChanged();
                        updateRecipeList();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
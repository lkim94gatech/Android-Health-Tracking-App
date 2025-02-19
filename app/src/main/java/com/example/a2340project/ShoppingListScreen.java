package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * twenty one pilots is releasing new music on the 29th apparently so that is hype
 *
 * ski mask the slump god is dropping a studio album soon too which is also poggers
 * because it's the first one in 6 years
 */
public class ShoppingListScreen extends AppCompatActivity implements RecyclerViewInterface {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    private Button buyItemsButton;
    private Button addButton;
    private RecyclerView recyclerView;
    private ArrayList<ShoppingListItem> shoppingListItems;
    private ShoppingListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.bottom_shopping);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int buttonID = item.getItemId();
            if (buttonID == R.id.bottom_meals) {
                startActivity(new Intent(ShoppingListScreen.this, InputMealScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_recipes) {
                startActivity(new Intent(ShoppingListScreen.this, RecipeScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_shopping) {
                return true;
            } else if (buttonID == R.id.bottom_ingredients) {
                startActivity(new Intent(ShoppingListScreen.this, IngredientScreen.class));
                return true;
            } else if (buttonID == R.id.bottom_profile) {
                startActivity(new Intent(ShoppingListScreen.this, ProfileScreen.class));
                return true;
            } else {
                return buttonID == R.id.bottom_shopping;
            }
        });
        addButton = findViewById(R.id.shoppingListAddButton);
        buyItemsButton = findViewById(R.id.buyItems);
        recyclerView = findViewById(R.id.shoppingList);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListItems = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users").child(currentUser.getUid()).child("shopping_list");
        adapter = new ShoppingListAdapter(this, shoppingListItems, this);
        recyclerView.setAdapter(adapter);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shoppingListItems.clear(); // Clear the list before adding items
                for (DataSnapshot data : snapshot.getChildren()) {
                    ShoppingListItem item = data.getValue(ShoppingListItem.class);
                    shoppingListItems.add(item);
                }
                adapter.notifyDataSetChanged(); // Notify adapter after updating the list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // cancel handling (not needed atm)
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });

        //retrieving current shopping list data in firebase
        //when clicking buyItems, removes item(s) from list, listview, and database.
        buyItemsButton.setOnClickListener(new View.OnClickListener() {
            private ArrayList<Integer> positionsList = new ArrayList<>();
            @Override
            public void onClick(View v) {
                ArrayList<ShoppingListItem> items = new ArrayList<>();
                for (ShoppingListItem item: shoppingListItems) {
                    if (item.getChecked()) {
                        //add item to pantry, borrowed from IngredientScreen lol
                        String ingredientName = item.getName();
                        try {
                            double quantity = item.getQuantity();
                            double calories = item.getCalories();
                            String expirationDate = null;

                            DatabaseReference ingredientsRef = FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("users").child(currentUser.getUid())
                                    .child("ingredients");
                            Query query = ingredientsRef.orderByChild("name")
                                    .equalTo(ingredientName);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {
                                        // Ingredient does not exist, add new ingredient
                                        ingredientsRef.push()
                                                .setValue(new Ingredient(ingredientName,
                                                quantity, calories, expirationDate));
                                    } else {
                                        for (DataSnapshot snap: dataSnapshot.getChildren()) {
                                            Ingredient ingredient = snap.getValue(Ingredient.class);
                                            if (ingredientName.equals(ingredient.getName())) {
                                                snap.getRef().child("quantity")
                                                        .setValue(quantity + ingredient
                                                                .getQuantity());
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // nothing needed at the moment
                                }
                            });
                        } catch (NumberFormatException e) {
                            // nothing needed at the moment
                        }
                        items.add(item);
                    }
                }
                DatabaseReference listRef = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(currentUser.getUid()).child("shopping_list");
                for (ShoppingListItem item: items) {
                    String name = shoppingListItems.get(shoppingListItems.indexOf(item)).getName();
                    Query query = listRef.orderByChild("name").equalTo(name);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snappy: dataSnapshot.getChildren()) {
                                    snappy.getRef().removeValue();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // nothing needed at the moment
                            }
                        });


                    shoppingListItems.remove(item);
                    System.out.println("removed");
                }
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Shopping List Item");

        // Inflate the custom layout
        final View customLayout = getLayoutInflater()
                .inflate(R.layout.activity_add_shopping_list_item_dialog, null);
        builder.setView(customLayout);

        // EditText variables initialization
        EditText nameField = customLayout.findViewById(R.id.itemName);
        EditText quantityField = customLayout.findViewById(R.id.itemQuantity);
        EditText caloriesField = customLayout.findViewById(R.id.itemCalories);

        // Add action button
        builder.setPositiveButton("Add", (dialog, id) -> {
            String itemName = nameField.getText().toString().trim();
            try {
                double quantity = Double.parseDouble(quantityField.getText().toString().trim());
                double calories = Double.parseDouble(caloriesField.getText().toString().trim());
                int quantityInt = (int) quantity;
                int caloriesInt = (int) calories;

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null && quantity > 0
                        && !itemName.contains("\\S+")
                        && !itemName.equals("")) {
                    boolean itemExists = false;
                    for (ShoppingListItem item : shoppingListItems) {
                        if (item.getName().equals(itemName)) {
                            itemExists = true;
                            break;
                        }
                    }
                    if (!itemExists) {
                        // Item is not in shopping list, add it
                        ShoppingListItem newItem = new ShoppingListItem(itemName,
                                quantityInt, caloriesInt);
                        mDatabase.push().setValue(newItem);
                        shoppingListItems.add(newItem);
                        adapter.notifyDataSetChanged();
                    } else {
                        Query query = mDatabase.orderByChild("name").equalTo(itemName);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    //update quantity

                                    for (DataSnapshot snap : snapshot.getChildren()) {
                                        String dbName = String.valueOf(snap.child("name"));
                                        if (dbName.equals(itemName)) {
                                            snap.getRef().child("quantity").setValue(quantityInt);
                                        }
                                    }
                                    for (ShoppingListItem listItem : shoppingListItems) {
                                        if (listItem.getName().equals(itemName)) {
                                            listItem.setQuantity(quantityInt);
                                        }
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                return;
                            }
                        });
                    }
                }
            } catch (NumberFormatException e) {
                // nothing needed at the moment
            }
        });

        builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public ArrayList<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    };
    public void onItemClick(int pos) {
        ShoppingListItem item = shoppingListItems.get(pos);
    }
}

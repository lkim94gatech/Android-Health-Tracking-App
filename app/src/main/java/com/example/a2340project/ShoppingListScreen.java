package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;
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
        // get text and buttons
        addButton = findViewById(R.id.shoppingListAddButton);
        buyItemsButton = findViewById(R.id.buyItems);
        // recycler view
        recyclerView = findViewById(R.id.shoppingList);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListItems = new ArrayList<>();
        // getting firebase instance
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users").child(currentUser.getUid()).child("shopping_list");
        // set up recycler view
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    String itemName = data.child("name").getValue().toString();
                    int itemQuantity = Integer.parseInt(data.child("quantity")
                            .getValue().toString());
                    int itemCalories = 0;

                    shoppingListItems.add(new ShoppingListItem(itemName, itemQuantity, itemCalories));

                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
        adapter = new ShoppingListAdapter(this, shoppingListItems, this);
        recyclerView.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });
        //retrieving current shopping list data in firebase
        //when clicking buyItems, removes item(s) from list, listview, and database.
        buyItemsButton.setOnClickListener(new View.OnClickListener() {
            ArrayList<Integer> positionsList = new ArrayList<>();
            @Override
            public void onClick(View v) {
                //debugging
                for (ShoppingListItem item: shoppingListItems){
                    System.out.println(item.toString());
                }
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
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // nothing needed at the moment
                                }
                            });
                            int position = shoppingListItems.indexOf(item);
                            positionsList.add(position);

                        } catch (NumberFormatException e) {
                            // nothing needed at the moment
                        }


                    }
                }
                for (Integer posit: positionsList) {
                    shoppingListItems.remove((int) posit);
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
                    Query query = mDatabase.orderByChild("name").equalTo(itemName);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                //item is not in shopping list, add it
                                ShoppingListItem newItem = new
                                        ShoppingListItem(itemName, quantityInt, caloriesInt);
                                mDatabase.push().setValue(newItem);
                                shoppingListItems.add(newItem);
                            } else if (snapshot.exists()) {
                                //update quantity

                                for (DataSnapshot snap: snapshot.getChildren()) {
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

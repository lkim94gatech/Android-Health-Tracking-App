package com.example.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
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
    private EditText shoppingListItem;
    private EditText shoppingListQuantity;
    private Button buyItemsButton;
    private Button addButton;
    private RecyclerView recyclerView;
    private ArrayList<ShoppingListItem> shoppingListItems;
    private ShoppingListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        //nav buttons
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
        shoppingListItem = findViewById(R.id.addShoppingListItem);
        shoppingListQuantity = findViewById(R.id.addShoppingListQuantity);
        addButton = findViewById(R.id.shoppingListAddButton);
        TextView error = findViewById(R.id.ShoppingListError);
        buyItemsButton = findViewById(R.id.buyItems);

        // recycler view
        recyclerView = findViewById(R.id.shoppingList);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListItems = new ArrayList<>();
        adapter = new ShoppingListAdapter(this, shoppingListItems, this);
        recyclerView.setAdapter(adapter);

        // getting firebase instance
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users").child(currentUser.getUid()).child("shopping_list");


        /* mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ShoppingListItem listItem = dataSnapshot.getValue(ShoppingListItem.class);
                    String itemName = listItem.getName();
                    boolean contains = false;
                    for (ShoppingListItem item: shoppingListItems) {
                        if (item.getName().equals(itemName)) {
                            contains = true;
                        }
                    }
                    if (!contains) {
                        shoppingListItems.add(listItem);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        }); */

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pulls text from EditText
                String itemName = shoppingListItem.getText().toString();
                String itemQuantity = shoppingListQuantity.getText().toString();
                if (itemName.contains("\\S+") || itemQuantity.contains("\\S+")
                        || itemName.equals("") || itemQuantity.equals("")) {
                    itemName = "";
                    itemQuantity = "";
                    error.setVisibility(View.VISIBLE);
                } else {
                    //converts quantity string to integer
                    int quantityInt = Integer.parseInt(itemQuantity);
                    String currentItemName = itemName;
                    final boolean[] alreadyListed = {false};
                    Query query = mDatabase.orderByChild("name").equalTo(itemName);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                //item is not in shopping list, add it
                                ShoppingListItem newItem = new ShoppingListItem(currentItemName, quantityInt);
                                mDatabase.push().setValue(newItem);
                                shoppingListItems.add(newItem);
                            } else if (snapshot.exists()) {
                                //update quantity

                                for (DataSnapshot snap: snapshot.getChildren()) {
                                    String dbName = String.valueOf(snap.child("name"));
                                    if (dbName.equals(currentItemName)) {
                                        snap.getRef().child("quantity").setValue(quantityInt);
                                    }
                                }

                                for (ShoppingListItem listItem : shoppingListItems) {
                                    if (listItem.getName().equals(currentItemName)) {
                                            shoppingListItems.remove(listItem);
                                    }
                                }
                                shoppingListItems.add(new ShoppingListItem(currentItemName, quantityInt));

                            }
                            adapter.notifyDataSetChanged();

                            shoppingListItem.setText("");
                            shoppingListQuantity.setText("");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            return;
                        }
                    });
                }
            }
        });






        //retrieving current shopping list data in firebase


        //when clicking buyItems, removes item(s) from list, listview, and database.
        buyItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                for (ShoppingListItem listItem: shoppingListItems) {
                    if (listItem.getChecked()) {
                        //add item to pantry, borrowed from IngredientScreen lol
                        String ingredientName = listItem.getName();
                        try {
                            double quantity = listItem.getQuantity();
                            double calories = 0;
                            String expirationDate = null;

                            if (currentUser != null && quantity > 0) {
                                DatabaseReference ingredientsRef = FirebaseDatabase.getInstance().getReference()
                                        .child("users").child(currentUser.getUid()).child("ingredients");
                                Query query = ingredientsRef.orderByChild("name").equalTo(ingredientName);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (!dataSnapshot.exists()) {
                                            // Ingredient does not exist, add new ingredient
                                            ingredientsRef.push().setValue(new Ingredient(ingredientName,
                                                    quantity, calories, expirationDate));
                                        }
                                        // If ingredient exists, do nothing
                                        // maybe later add error pop up?? but not necessary right now
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // nothing needed at the moment
                                    }

                                });
                                int position = shoppingListItems.indexOf(listItem);
                                adapter.removeItem(position);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (NumberFormatException e) {
                            // nothing needed at the moment
                        }
                        adapter.notifyDataSetChanged();







                    }
                }

            }
        });
    }

    public ArrayList<ShoppingListItem> getShoppingListItems() {return shoppingListItems;};
    public void onItemClick(int pos) {
        ShoppingListItem item = shoppingListItems.get(pos);
    }
}

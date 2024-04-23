package com.example.a2340project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter
        .ShoppingListViewHolder> {

    private Context context;
    private ArrayList<ShoppingListItem> shoppingListItems;

    private final RecyclerViewInterface recyclerInterface;

    public ShoppingListAdapter(Context context, ArrayList<ShoppingListItem> shoppingListItems,
                                 RecyclerViewInterface recyclerInterface) {
        this.context = context;
        this.shoppingListItems = shoppingListItems;
        this.recyclerInterface = recyclerInterface;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shoppinglistrow,
                parent, false);
        return new ShoppingListViewHolder(view, recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        ShoppingListItem listItem = shoppingListItems.get(position);
        holder.itemName.setText(listItem.getName());
        holder.itemQuantity.setText(String.valueOf(listItem.getQuantity()));
        //sets check status for item when checkbox is clicked
        holder.itemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listItem.setChecked(holder.itemCheckBox.isChecked());
        });
    }

    public void removeItem(int position) {
        shoppingListItems.remove(position);
        ShoppingListAdapter.super.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return shoppingListItems.size();
    }

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemQuantity;
        private CheckBox itemCheckBox;
        private TextView itemCalories;
        private static RecyclerViewInterface recyclerInterface;

        public ShoppingListViewHolder(@NonNull View itemView,
                                    RecyclerViewInterface recycleInterface) {
            super(itemView);
            this.recyclerInterface = recycleInterface;

            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemCheckBox = itemView.findViewById(R.id.itemCheckBox);
            itemCalories = itemView.findViewById(R.id.itemCalories);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recycleInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}

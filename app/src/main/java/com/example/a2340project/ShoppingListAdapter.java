package com.example.a2340project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter
        .ShoppingListViewHolder> {

    private Context context;
    private ArrayList<ShoppingListItem> listItemArray;

    private final RecyclerViewInterface recyclerInterface;

    public ShoppingListAdapter(Context context, ArrayList<ShoppingListItem> listItemArray,
                                 RecyclerViewInterface recyclerInterface) {
        this.context = context;
        this.listItemArray = listItemArray;
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
        ShoppingListItem listItem = listItemArray.get(position);
        holder.itemName.setText(listItem.getName());
        holder.itemQuantity.setText(String.valueOf(listItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return listItemArray.size();
    }

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemQuantity;
        private static RecyclerViewInterface recyclerInterface;

        public ShoppingListViewHolder(@NonNull View itemView,
                                    RecyclerViewInterface recycleInterface) {
            super(itemView);
            this.recyclerInterface = recycleInterface;

            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
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

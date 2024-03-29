package com.example.a2340project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter
        .IngredientViewHolder> {

    private Context context;
    private ArrayList<Ingredient> ingredientArr;

    private final recyclerViewInterface recyclerInterface;

    public IngredientListAdapter(Context context, ArrayList<Ingredient> ingredientArr, recyclerViewInterface recyclerInterface) {
        this.context = context;
        this.ingredientArr = ingredientArr;
        this.recyclerInterface = recyclerInterface;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item,
                parent, false);
        return new IngredientViewHolder(view, recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientArr.get(position);
        holder.ingredientName.setText(ingredient.getName());
        holder.ingredientQuantity.setText(String.valueOf(ingredient.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return ingredientArr.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientName;
        private TextView ingredientQuantity;
        private static recyclerViewInterface recyclerInterface;

        public IngredientViewHolder(@NonNull View itemView, recyclerViewInterface recycleInterface) {
            super(itemView);
            this.recyclerInterface = recycleInterface;

            ingredientName = itemView.findViewById(R.id.ingredientText);
            ingredientQuantity = itemView.findViewById(R.id.quantityText);
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

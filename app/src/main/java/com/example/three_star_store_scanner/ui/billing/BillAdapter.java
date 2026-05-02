package com.example.three_star_store_scanner.ui.billing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.three_star_store_scanner.R;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private List<BillItem> billItems;
    private OnQuantityChangedListener listener;

    public interface OnQuantityChangedListener {
        void onQuantityChanged();
    }

    public BillAdapter(List<BillItem> billItems, OnQuantityChangedListener listener) {
        this.billItems = billItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        BillItem item = billItems.get(position);
        holder.nameText.setText(item.name);
        holder.priceText.setText("₹" + item.price);
        holder.quantityText.setText(String.valueOf(item.quantity));

        holder.increaseQty.setOnClickListener(v -> {
            item.quantity++;
            holder.quantityText.setText(String.valueOf(item.quantity));
            listener.onQuantityChanged();
        });

        holder.decreaseQty.setOnClickListener(v -> {
            if (item.quantity > 1) {
                item.quantity--;
                holder.quantityText.setText(String.valueOf(item.quantity));
                listener.onQuantityChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return billItems.size();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, priceText, quantityText;
        Button increaseQty, decreaseQty;

        BillViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.itemName);
            priceText = itemView.findViewById(R.id.itemPrice);
            quantityText = itemView.findViewById(R.id.itemQuantity);
            increaseQty = itemView.findViewById(R.id.increaseQty);
            decreaseQty = itemView.findViewById(R.id.decreaseQty);
        }
    }
}

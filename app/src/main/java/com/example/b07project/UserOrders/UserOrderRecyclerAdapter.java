package com.example.b07project.UserOrders;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.OwnerOrders.Order;
import com.example.b07project.Product;
import com.example.b07project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserOrderRecyclerAdapter extends RecyclerView.Adapter<UserOrderRecyclerAdapter.CustomViewHolder> {

    FirebaseDatabase db;
    Context context;
    ArrayList<UserOrder> userOrders;
    PastOrdersFragment caller;

    String date;

    public UserOrderRecyclerAdapter(Context context, ArrayList<UserOrder> userOrders, PastOrdersFragment caller, String date) {
        this.context = context;
        this.userOrders = userOrders;
        this.caller = caller;
        this.db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        this.date = date;
    }

    @NonNull
    @Override
    public UserOrderRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.past_order_layout, parent, false);

        return new UserOrderRecyclerAdapter.CustomViewHolder(view, caller);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderRecyclerAdapter.CustomViewHolder holder, int position) {
        UserOrder orders = userOrders.get(holder.getBindingAdapterPosition());
        Order o;
        TableRow r;
        TextView itemName;
        TextView itemAmount;
        TextView dateView = new TextView(caller.getContext());
        dateView.setText(date.substring(0, 10));
        r = new TableRow(caller.getContext());
        r.addView(dateView);
        holder.table.addView(r);

        // holder.name.setText(orders.getUserID());
        double total = 0;
        for (int i = 0; i < orders.getOrders().size(); i++) {
            o = orders.getOrders().get(i);

            total += o.getQuantity()*o.getPrice();
            r = new TableRow(caller.getContext());

            int backgroundColor;

            if (o.isPickedUp()) {
                backgroundColor = R.color.pickedup_product_background;
            } else if (o.isVerified()) {
                backgroundColor = R.color.verified_product_background;
            } else {
                backgroundColor = R.color.default_product_background_color;
            }
            r.setBackgroundResource(backgroundColor);

            //r.generateLayoutParams();
            itemName = new TextView(caller.getContext());
            itemAmount = new TextView(caller.getContext());
            itemName.setText(o.getTitle());
            itemName.setGravity(Gravity.START);
            itemAmount.setText(o.getQuantity() + "");
            itemAmount.setGravity(Gravity.END);

            r.addView(itemName);
            r.addView(itemAmount);
            holder.table.addView(r);
        }
        holder.price.setText("Total: $"+total);
    }

    @Override
    public int getItemCount() {
        return userOrders.size();
    }

    private Product getProduct(DataSnapshot snapshot, int productID) {
        for (DataSnapshot prodSnap:snapshot.child("products").getChildren()){
            Product p = prodSnap.getValue(Product.class);
            if (p != null && p.getProductID() == productID){
                return p;
            }
        }
        return null;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        TableLayout table;
        TextView date;
        TextView price;
        int orderNum;
        public CustomViewHolder(@NonNull View itemView, PastOrdersFragment caller) {
            super(itemView);
            table = itemView.findViewById(R.id.past_order_detail_table);
            // name = itemView.findViewById(R.id.past_order_user_id);
            price = itemView.findViewById(R.id.past_order_total);
        }
    }
}
package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.OwnerOrders.OrdersFragment;
import com.example.b07project.OwnerProducts.OwnerProductsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerOrderRecyclerAdapter extends RecyclerView.Adapter<OwnerOrderRecyclerAdapter.CustomViewHolder>{
    Context context;
    ArrayList<UserOrder> userOrders;
    OrdersFragment caller;

    public OwnerOrderRecyclerAdapter(Context context, ArrayList<UserOrder> userOrders, OrdersFragment caller) {
        this.context = context;
        this.userOrders = userOrders;
        this.caller = caller;
    }

    @NonNull
    @Override
    public OwnerOrderRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_layout,parent,false);

        return new CustomViewHolder(view,caller);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerOrderRecyclerAdapter.CustomViewHolder holder, int position) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference query = db.getReference().child("stores").
                child(OwnerProductsFragment.KEY);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("orders").getChildrenCount() > 0){
                    UserOrder orders = userOrders.get(holder.getAdapterPosition());
                    Order o = orders.getOrders().get(0);
                    TableRow r;
                    TextView itemName;
                    TextView itemAmount;
                    Product p;

                    holder.name.setText(o.getUserID()+""); //Swap this for actual data looked up from database

                    for (int i = 0; i < orders.getOrders().size(); i++) {
                        o = orders.getOrders().get(i);
                        p = snapshot.child("products").child(String.valueOf(o.getProductID())).getValue(Product.class);


                        r = new TableRow(caller.getContext());
                        itemName = new TextView(caller.getContext());
                        itemAmount = new TextView(caller.getContext());

                        itemName.setText(p.getTitle());
                        itemAmount.setText(o.amount+"");


                        r.addView(itemName);
                        r.addView(itemAmount);
                        holder.table.addView(r);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return userOrders.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        TableLayout table;
        TextView name;
        int orderNum;
        public CustomViewHolder(@NonNull View itemView,OrdersFragment caller) {
            super(itemView);
            table = itemView.findViewById(R.id.order_detail_table);
            name = itemView.findViewById(R.id.order_user_id);
        }
    }
}
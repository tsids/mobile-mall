package com.example.b07project.OwnerOrders;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.CartPackage.CartProduct;
import com.example.b07project.OwnerProducts.OwnerProductsFragment;
import com.example.b07project.Product;
import com.example.b07project.R;
import com.example.b07project.UserOrders.UserOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerOrderRecyclerAdapter extends RecyclerView.Adapter<OwnerOrderRecyclerAdapter.CustomViewHolder>{
    FirebaseDatabase db;
    Context context;
    ArrayList<UserOrder> userOrders;
    ArrayList<String> orderIDs;
    OrdersFragment caller;

    public OwnerOrderRecyclerAdapter(Context context, ArrayList<UserOrder> userOrders, ArrayList<String> orderIDs, OrdersFragment caller) {
        this.context = context;
        this.userOrders = userOrders;
        this.caller = caller;
        this.orderIDs = orderIDs;
        this.db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
    }

    @NonNull
    @Override
    public OwnerOrderRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_layout,parent,false);

        return new OwnerOrderRecyclerAdapter.CustomViewHolder(view,caller);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerOrderRecyclerAdapter.CustomViewHolder holder, int position) {
        UserOrder orders = userOrders.get(holder.getBindingAdapterPosition());
        CartProduct o;
        TableRow r;
        TextView itemName;
        TextView itemAmount;
        holder.table.removeAllViews();

        holder.name.setText(orders.getUserID()); //Swap this for actual data looked up from database
        holder.pickedup.setChecked(orders.getOrders().get(0).getPickedUp());
        holder.verified.setChecked(orders.getOrders().get(0).getVerified());
        double total = 0;
        for (int i = 0; i < orders.getOrders().size(); i++) {
            o = orders.getOrders().get(i);

            total += o.getQuantity()*o.getPrice();
            r = new TableRow(caller.getContext());
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

        holder.pickedup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseReference q = db.getReference();

                if (holder.verified.isChecked()) {
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot orderSnapshot: snapshot.child("users").child(orders.getUserID()).child("pastOrders").child(orderIDs.get(position)).child("orders").getChildren()) {
                                CartProduct order = orderSnapshot.getValue(CartProduct.class);
                                int i = 0;
                                if (order.getStoreID() == userOrders.get(position).getOrders().get(0).getStoreID()) {
                                    orderSnapshot.child("pickedUp").getRef().setValue(b);
                                    userOrders.get(position).getOrders().get(i).setVerified(b);
                                    i++;

                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    holder.pickedup.setChecked(false);
                }


            }
        });
        holder.verified.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseReference q = db.getReference();
                if (!holder.pickedup.isChecked()) {
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot orderSnapshot: snapshot.child("users").child(orders.getUserID()).child("pastOrders").child(orderIDs.get(position)).child("orders").getChildren()) {
                                CartProduct order = orderSnapshot.getValue(CartProduct.class);
                                int i = 0;
                                if (order.getStoreID() == userOrders.get(position).getOrders().get(0).getStoreID()) {
                                    orderSnapshot.child("verified").getRef().setValue(b);
                                    userOrders.get(position).getOrders().get(i).setVerified(b);
                                    i++;

                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    holder.verified.setChecked(true);
                }

            }
        });
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

    @Override
    public int getItemCount() {
        return userOrders.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        TableLayout table;
        TextView name;
        TextView price;
        CheckBox verified;
        CheckBox pickedup;
        int orderNum;
        public CustomViewHolder(@NonNull View itemView,OrdersFragment caller) {
            super(itemView);
            table = itemView.findViewById(R.id.order_detail_table);
            name = itemView.findViewById(R.id.order_user_id);
            price = itemView.findViewById(R.id.order_total);
            verified = itemView.findViewById(R.id.order_verified);
            pickedup=itemView.findViewById(R.id.order_picked_up);
        }
    }
}
package com.example.b07project.OwnerOrders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.b07project.CartPackage.CartProduct;
import com.example.b07project.OwnerProducts.OwnerProductsFragment;
import com.example.b07project.R;
import com.example.b07project.UserOrders.UserOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.b07project.OwnerOrders.OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    FirebaseDatabase db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private ArrayList<UserOrder> userOrders;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_orders, container, false);
        // Inflate the layout for this fragment
        DatabaseReference query = db.getReference();

        RecyclerView recycler = v.findViewById(R.id.order_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        String username = getActivity().getIntent().getExtras().get("USERNAME").toString();


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int storeID = snapshot.child("stores").child(username).child("storeID").getValue(Integer.class);
                ArrayList<String> orderIDs = new ArrayList<>();
                userOrders = new ArrayList<UserOrder>();
                for (DataSnapshot userSnapshot: snapshot.child("users").getChildren()) {

                    String user = userSnapshot.child("username").getValue(String.class);
                    for(DataSnapshot pastOrderSnapshot: userSnapshot.child("pastOrders").getChildren()) {
                        String orderID = pastOrderSnapshot.child("createdAt").getValue(String.class);
                        UserOrder userOrder = new UserOrder();
                        for (DataSnapshot userOrderSnapshot: pastOrderSnapshot.child("orders").getChildren()) {
                            CartProduct order = userOrderSnapshot.getValue(CartProduct.class);
                            if (order.getStoreID() == storeID) {
                                userOrder.getOrders().add(order);
                            }
                        }
                        if (userOrder.getOrders().size() > 0) {
                            userOrder.setUserID(user);
                            userOrders.add(userOrder);
                            orderIDs.add(orderID);
                        }

                    }
                }
                recycler.setAdapter(new OwnerOrderRecyclerAdapter(getContext(), userOrders, orderIDs,OrdersFragment.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("Orders");
        toolbar.setNavigationIcon(null);
    }
}
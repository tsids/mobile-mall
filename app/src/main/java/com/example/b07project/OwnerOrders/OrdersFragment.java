package com.example.b07project.OwnerOrders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.b07project.OwnerProducts.OwnerProductsFragment;
import com.example.b07project.R;
import com.example.b07project.UserOrders.UserOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.b07project.OwnerOrders.OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<UserOrder> userOrders;
    FirebaseDatabase db;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new com.example.b07project.OwnerOrders.OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOrders(DataSnapshot snapshot, RecyclerView recycler){
        userOrders = new ArrayList<UserOrder>();
        for (DataSnapshot orderBundle:snapshot.getChildren()){
            UserOrder userOrder = new UserOrder();
            userOrders.add(userOrder);
            for (DataSnapshot itemOrder:orderBundle.child("orders").getChildren()){
                Order o = itemOrder.getValue(Order.class);
                if (o != null) {
                    if (Objects.equals(userOrder.getUserID(), "")) {
                        userOrder.setUserID(String.valueOf(o.userID));
                    }
                    userOrder.getOrders().add(o);
                }
            }
        }
        recycler.setAdapter(new OwnerOrderRecyclerAdapter(getContext(), userOrders,this));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_orders, container, false);
        // Inflate the layout for this fragment
        DatabaseReference query = db.getReference().child("stores")
                .child(getActivity().getIntent().getExtras().get("USERNAME").toString()).
                child("orders");

        RecyclerView recycler = v.findViewById(R.id.order_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setOrders(snapshot,recycler);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }
}
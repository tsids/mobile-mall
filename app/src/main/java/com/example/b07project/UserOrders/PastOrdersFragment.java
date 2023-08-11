package com.example.b07project.UserOrders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.b07project.CartPackage.CartProduct;
import com.example.b07project.Navbar.UserNavActivity;
import com.example.b07project.Product;
import com.example.b07project.OwnerOrders.Order;
import com.example.b07project.R;
import com.example.b07project.Stores.StoresFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PastOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastOrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<UserOrder> userOrders;
    FirebaseDatabase db;

    private String date;

    public PastOrdersFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PastOrdersFragment newInstance(String param1) {
        PastOrdersFragment fragment = new PastOrdersFragment();
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
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
    }

    public void setOrders(DataSnapshot snapshot, RecyclerView recyclerView) {
        userOrders = new ArrayList<UserOrder>();
        ArrayList<String> dates = new ArrayList<>();
        for (DataSnapshot orderBundle: snapshot.getChildren()) {
            UserOrder userOrder = new UserOrder();
            userOrders.add(userOrder);
            if (orderBundle.child("createdAt").getValue() != null) {
                date = orderBundle.child("createdAt").getValue().toString();
                // userOrder.setUserID(orderBundle.child("user").getValue().toString());
                for (DataSnapshot itemOrder:orderBundle.child("orders").getChildren()) {
                    CartProduct order = itemOrder.getValue(CartProduct.class);
                    if (order != null) {
                        userOrder.getOrders().add(order);
                        dates.add(date);
                    }
                }
            }

        }
        recyclerView.setAdapter(new UserOrderRecyclerAdapter(getContext(), userOrders, this, dates));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_past_orders, container, false);

        DatabaseReference query = db.getReference().child("users").
                child(getActivity().getIntent().getExtras().get("USERNAME").toString()).child("pastOrders");
        //child(getActivity().getIntent().getExtras().get("created_at").toString()).child("orders");

        RecyclerView recyclerView = v.findViewById(R.id.past_order_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setOrders(snapshot, recyclerView);
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
        toolbar.setTitle("My Orders");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new StoresFragment());
                fragmentTransaction.commit();

            }
        });
    }
}

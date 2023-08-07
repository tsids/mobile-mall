package com.example.b07project.UserOrders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
<<<<<<< HEAD
=======
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
>>>>>>> b8c134b (Created past orders for user)
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< HEAD
import com.example.b07project.Navbar.UserNavActivity;
import com.example.b07project.Product;
=======
import com.example.b07project.OwnerOrders.Order;
>>>>>>> b8c134b (Created past orders for user)
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

    public PastOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PastOrdersFragment.
     */
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
        }
        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
    }

    public void setOrders(DataSnapshot snapshot, RecyclerView recyclerView) {
        userOrders = new ArrayList<UserOrder>();
        for (DataSnapshot orderBundle: snapshot.getChildren()) {
            UserOrder userOrder = new UserOrder();
            userOrders.add(userOrder);
            userOrder.setUserID(orderBundle.child("user").getValue().toString());
            for (DataSnapshot itemOrder:orderBundle.child("pastOrders").getChildren()) {
                Order order = itemOrder.getValue(Order.class);
                if (order != null) {
                    userOrder.getOrders().add(order);
                }
            }
        }
        recyclerView.setAdapter(new UserOrderRecyclerAdapter(getContext(), userOrders, this));
    }


    private void setProducts(){
        //This way of assigning data is temporary, and will need to be connected to a database
        String[] names = getResources().getStringArray(R.array.prod_names);
        String[] descs = getResources().getStringArray(R.array.prod_descriptions);
        String[] prices = getResources().getStringArray(R.array.prod_prices);

        for (int i = 0; i < names.length; i++) {
            float price = Float.parseFloat(prices[i].substring(1));
            products.add(new Product(null,names[i],price,descs[i],0,0));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_past_orders, container, false);

        DatabaseReference query = db.getReference().child("users").
                child(getActivity().getIntent().getExtras().get("USERNAME").toString()).child("pastOrders");

        RecyclerView recyclerView = v.findViewById(R.id.order_recycler);
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
        toolbar.setNavigationIcon(null);
    }
}
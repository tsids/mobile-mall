package com.example.b07project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductPreview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductPreview extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String mParam3;

    public ProductPreview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param price Parameter 2.
     * @param description Parameter 3
     * @return A new instance of fragment ProductPreview.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductPreview newInstance(String title, String price, String description) {
        ProductPreview fragment = new ProductPreview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, price);
        args.putString(ARG_PARAM3, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_preview, container, false);
        return view;
    }

    public void setText(String title, String price, String description) {
        TextView prodTitle = (TextView) getView().findViewById(R.id.productTitle);
        prodTitle.setText(title);

        TextView prodPrice = (TextView) getView().findViewById(R.id.productPrice);
        prodPrice.setText(price);

        TextView prodDesc = (TextView) getView().findViewById(R.id.productDescription);
        prodDesc.setText(description);
    }
}
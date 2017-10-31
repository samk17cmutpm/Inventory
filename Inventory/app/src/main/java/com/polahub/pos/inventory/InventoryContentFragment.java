package com.polahub.pos.inventory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InventoryContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryContentFragment extends Fragment {

    private Unbinder unbinder;

    private InventoryAdapter mInventoryAdapter;

    private List<InventoryItem> mInventoryItems;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private SpacesItemDecoration mSpacesItemDecoration;

    private GridLayoutManager mGridLayoutManager;

    public InventoryContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InventoryContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InventoryContentFragment newInstance() {
        InventoryContentFragment fragment = new InventoryContentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory_content, container, false);
        unbinder = ButterKnife.bind(this, view);
        mInventoryItems = new ArrayList<>();
        for (int index = 0; index < 200; index++)
            mInventoryItems.add(new InventoryItem());

        mSpacesItemDecoration = new SpacesItemDecoration(10);
        mInventoryAdapter = new InventoryAdapter(mInventoryItems);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);

        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(mSpacesItemDecoration);
        mRecyclerView.setAdapter(mInventoryAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

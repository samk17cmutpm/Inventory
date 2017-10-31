package com.polahub.pos.inventory;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InventoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private InventoryContentPagerAdapter mInventoryContentPagerAdapter;

    public InventoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment InventoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InventoryFragment newInstance() {
        InventoryFragment fragment = new InventoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        unbinder = ButterKnife.bind(this, view);

        mInventoryContentPagerAdapter = new InventoryContentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mInventoryContentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public class InventoryContentPagerAdapter extends SmartFragmentStatePagerAdapter {

        private static final int INVENTORY_CONTENT_TOTAL = 5;

        public InventoryContentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return InventoryContentFragment.newInstance();
        }

        @Override
        public int getCount() {
            return INVENTORY_CONTENT_TOTAL;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case InventoryContent.MAIN:
                    return "MAIN";
                case InventoryContent.PICTURES:
                    return "PICTURES";
                case InventoryContent.NEW_PRODUCTS:
                    return "NEW_PRODUCTS";
                case InventoryContent.NEW_ORDERS:
                    return "NEW_ORDERS";
                case InventoryContent.INVENTORIES:
                    return "INVENTORIES";
                default:
                    return "INVENTORIES";
            }
        }
    }

    public class InventoryContent {
        public static final int MAIN = 0;
        public static final int PICTURES = 1;
        public static final int NEW_PRODUCTS = 2;
        public static final int NEW_ORDERS = 3;
        public static final int INVENTORIES = 4;
    }

}

package com.polahub.pos.inventory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InventoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;


    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private InventoryPagerAdapter mInventoryPagerAdapter;

    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(InventoryType.HOME, true);
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(InventoryType.DASHBOARD, true);
                    return true;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(InventoryType.NOTIFICATIONS, true);
                    return true;
                case R.id.navigation_settings:
                    mViewPager.setCurrentItem(InventoryType.SETTINGS, true);
                    return true;
            }
            return false;
        }
    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
            mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigationView.setNavigationItemSelectedListener(this);

        mInventoryPagerAdapter = new InventoryPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mInventoryPagerAdapter);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case InventoryType.HOME:
                            Log.e("HOME", "HOME");
                            mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
                            break;
                        case InventoryType.DASHBOARD:
                            Log.e("DASHBOARD", "DASHBOARD");
                            mBottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
                            break;
                        case InventoryType.NOTIFICATIONS:
                            mBottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
                            break;
                        case InventoryType.SETTINGS:
                            mBottomNavigationView.setSelectedItemId(R.id.navigation_settings);
                            break;
                        default:
                            mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public class InventoryPagerAdapter extends SmartFragmentStatePagerAdapter {

        private static final int TOTAL_PAGES = 4;

        public InventoryPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return InventoryFragment.newInstance();
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case InventoryType.HOME:
                    return String.valueOf(getString(R.string.title_home));
                case InventoryType.DASHBOARD:
                    return String.valueOf(getString(R.string.title_dashboard));
                case InventoryType.NOTIFICATIONS:
                    return String.valueOf(getString(R.string.title_notifications));
                case InventoryType.SETTINGS:
                    return String.valueOf(getString(R.string.title_settings));
                default:
                    return String.valueOf(getString(R.string.title_home));
            }
        }
    }

    public class InventoryType {
        public static final int HOME = 0;
        public static final int DASHBOARD = 1;
        public static final int NOTIFICATIONS = 2;
        public static final int SETTINGS = 3;
    }
}

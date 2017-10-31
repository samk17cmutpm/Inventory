package com.polahub.pos.inventory;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

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

    private interface WindowCompat {
        void setStatusBarColor(int color);
    }

    private int mPrimaryColor;
    private int mAccentColor;

    private List<Item> mItems;

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
            mBottomNavigationView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        mInventoryPagerAdapter = new InventoryPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mInventoryPagerAdapter);
            final WindowCompat windowCompat = windowCompat(this);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                private ValueAnimator mPrimaryAnimator;
                private ValueAnimator mAccentAnimator;
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    if (mPrimaryAnimator != null
                            && mPrimaryAnimator.isRunning()) {
                        mPrimaryAnimator.cancel();
                    }

                    if (mAccentAnimator != null
                            && mAccentAnimator.isRunning()) {
                        mAccentAnimator.cancel();
                    }

                    // right, if
                    final Item item = mItems.get(position);

                    final CCFAnimator primary = CCFAnimator.rgb(mPrimaryColor, item.primaryColor);
                    mPrimaryAnimator = primary.asValueAnimator(new CCFAnimator.OnNewColorListener() {
                        @Override
                        public void onNewColor(@ColorInt int color) {
                            windowCompat.setStatusBarColor(color);
                        }
                    });
                    mPrimaryAnimator.setDuration(250L);
                    mPrimaryAnimator.start();

                    // we need to evaluate colors (in case we are currently in scrolling transaction
                    final CCFAnimator accent = CCFAnimator.rgb(
                            accentColor(mAccentColor, mPrimaryColor),
                            accentColor(item.accentColor, item.primaryColor)
                    );
                    mAccentAnimator = accent.asValueAnimator(new CCFAnimator.OnNewColorListener() {
                        @Override
                        public void onNewColor(@ColorInt int color) {
                            mBottomNavigationView.setBackgroundColor(color);
                            mToolbar.setBackgroundColor(color);
                        }
                    });
                    mAccentAnimator.setDuration(250L);
                    mAccentAnimator.start();

                    mPrimaryColor = item.primaryColor;
                    mAccentColor = item.accentColor;

                    switch (position) {
                        case InventoryType.HOME:
                            mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
                            break;
                        case InventoryType.DASHBOARD:
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

            mItems = new ArrayList<>();

            mItems.add(new Item(
                    ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary),
                    ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary),
                    "First Teal"
            ));

            mItems.add(new Item(
                    ContextCompat.getColor(getApplicationContext(), R.color.md_red_500),
                    ContextCompat.getColor(getApplicationContext(), R.color.md_red_300),
                    "Second Red"
            ));

            mItems.add(new Item(
                    ContextCompat.getColor(getApplicationContext(), R.color.md_purple_500),
                    ContextCompat.getColor(getApplicationContext(), R.color.md_purple_300),
                    "Third Purple"
            ));

            mItems.add(new Item(
                    ContextCompat.getColor(getApplicationContext(), R.color.md_blue_500),
                    ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300),
                    "Forth Blue"
            ));

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

    private interface ViewProvider {
        View provide(ViewGroup parent);
    }

    private static class Item {

        final int primaryColor;
        final int accentColor;
        final String title;

        private Item(int primaryColor, int accentColor, String title) {
            this.primaryColor = primaryColor;
            this.accentColor = accentColor;
            this.title = title;
        }
    }

    private static WindowCompat windowCompat(Activity activity) {
        final WindowCompat compat;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            compat = new WindowCompat21(activity.getWindow());
        } else {
            compat = new WindowCompatImpl();
        }
        return compat;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class WindowCompat21 implements WindowCompat {

        private final Window mWindow;

        private WindowCompat21(Window window) {
            mWindow = window;
        }

        @Override
        public void setStatusBarColor(int color) {
            mWindow.setStatusBarColor(color);
        }
    }

    private static class WindowCompatImpl implements WindowCompat {

        @Override
        public void setStatusBarColor(int color) {
            // no op
        }
    }

    private static int accentColor(int accentColor, int primaryColor) {
        int out;
         CCFAnimator animator = CCFAnimator.rgb(accentColor, primaryColor);
        out = animator.getColor(1);
        return out;
    }
}

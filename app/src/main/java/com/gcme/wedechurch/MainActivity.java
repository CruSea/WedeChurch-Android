package com.gcme.wedechurch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.gcme.wedechurch.Fragments.ChurchDemominations;
import com.gcme.wedechurch.Fragments.Events;
import com.gcme.wedechurch.Fragments.FragmentDrawer;
import com.gcme.wedechurch.Fragments.MapFragment;
import com.gcme.wedechurch.Fragments.Profile;
import com.gcme.wedechurch.Intro.IntroActivity;
import com.gcme.wedechurch.common.ActivityBase;
import com.gcme.wedechurch.dialogs.CoverChooseDialog;
import com.gcme.wedechurch.dialogs.PhotoChooseDialog;
import com.gcme.wedechurch.dialogs.PopularSettingsDialog;
import com.gcme.wedechurch.dialogs.ProfileReportDialog;


public class MainActivity extends ActivityBase implements FragmentDrawer.FragmentDrawerListener, PhotoChooseDialog.AlertPositiveListener, CoverChooseDialog.AlertPositiveListener, ProfileReportDialog.AlertPositiveListener, PopularSettingsDialog.AlertPositiveListener {

    Toolbar mToolbar;

    private FragmentDrawer drawerFragment;
    private SearchView searchview;

    // used to store app title
    private CharSequence mTitle;

    LinearLayout mContainerAdmob;

    Fragment fragment;
    Boolean action = false;
    int page = 0;

    private Boolean restore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        if (savedInstanceState != null) {
//
//            //Restore the fragment's instance
//            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "currentFragment");
//
//            restore = savedInstanceState.getBoolean("restore");
//            mTitle = savedInstanceState.getString("mTitle");
//
//        } else {
//
//            fragment = new StreamFragment();
//
//            restore = false;
//            mTitle = getString(R.string.app_name);
//        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mTitle);

        drawerFragment = (FragmentDrawer) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(MainActivity.this, IntroActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mTitle);


        drawerFragment = (FragmentDrawer) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);



        if (!restore) {

            // Show default section "Stream"

            displayView(1);
        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//
//        super.onSaveInstanceState(outState);
//
//        outState.putBoolean("restore", true);
//        outState.putString("mTitle", getSupportActionBar().getTitle().toString());
//        getSupportFragmentManager().putFragment(outState, "currentFragment", fragment);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_LOGIN && resultCode == RESULT_OK && null != data) {

            String pageId = data.getStringExtra("pageId");

            switch (pageId) {

                case "favorites": {

                    displayView(5);

                    break;
                }

                case "notifications": {

                    displayView(6);

                    break;
                }

                case "profile": {

                    displayView(7);

                    break;
                }

                case "settings": {

//                    Intent i = new Intent(MainActivity.this, SettingsActivity.class);
//                    startActivity(i);

                    break;
                }

                default: {

                    break;
                }
            }
        }
    }

    @Override
    public void onChangePopularCategory(int position) {

//        PopularFragment p = (PopularFragment) fragment;
//        p.onChangeCategory(position);
    }

    @Override
    public void onPhotoFromGallery() {

//        ProfileFragment p = (ProfileFragment) fragment;
//        p.photoFromGallery();
    }

    @Override
    public void onPhotoFromCamera() {

//        ProfileFragment p = (ProfileFragment) fragment;
//        p.photoFromCamera();
    }

    @Override
    public void onCoverFromGallery() {

//        ProfileFragment p = (ProfileFragment) fragment;
//        p.coverFromGallery();
    }

    @Override
    public void onCoverFromCamera() {

//        ProfileFragment p = (ProfileFragment) fragment;
//        p.coverFromCamera();
    }

    @Override
    public void onProfileReport(int position) {

//        ProfileFragment p = (ProfileFragment) fragment;
//        p.onProfileReport(position);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {

        displayView(position);
    }

    private void displayView(int position) {

        action = false;

        switch (position) {

            case 0: {

                break;
            }

            case 1: {

                page = 1;

                fragment = new MapFragment();
                getSupportActionBar().setTitle(R.string.page_1);

                action = true;

                break;
            }

            case 2: {

                page = 2;

                fragment = new ChurchDemominations();
                getSupportActionBar().setTitle(R.string.page_2);

                action = true;

                break;
            }


            case 3: {

                page = 3;

                fragment = new Events();
                getSupportActionBar().setTitle(R.string.page_3);

                action = true;

                break;
            }

            case 4: {

//                page = 4;
//
//                fragment = new PopularFragment();
//                getSupportActionBar().setTitle(R.string.page_4);
//
//                action = true;
//
//                break;
            }

            case 5: {
                page = 5;

                fragment = new Profile();
                getSupportActionBar().setTitle(R.string.page_5);

                action = true;

                break;
            }

            case 6: {

//                if (App.getInstance().getId() != 0){
//
//                    page = 6;
//
//                    fragment = new NotificationsFragment();
//                    getSupportActionBar().setTitle(R.string.page_6);
//
//                    action = true;
//
//                } else {
//
//                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                    i.putExtra("pageId", "notifications");
//                    startActivityForResult(i, ACTION_LOGIN);
//                }

                break;
            }

            case 7: {

//                if (App.getInstance().getId() != 0){
//
//                    page = 7;
//
//                    fragment = new ProfileFragment();
//                    getSupportActionBar().setTitle(R.string.page_7);
//
//                    action = true;
//
//                } else {
//
//                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                    i.putExtra("pageId", "profile");
//                    startActivityForResult(i, ACTION_LOGIN);
//                }

                break;
            }

            default: {

//                if (App.getInstance().getId() != 0) {
//
//                    Intent i = new Intent(MainActivity.this, SettingsActivity.class);
//                    startActivity(i);
//
//                } else {
//
//                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                    i.putExtra("pageId", "settings");
//                    startActivityForResult(i, ACTION_LOGIN);
//                }

                break;
            }
        }

        if (action) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container_body, fragment)
                    .commit();
        }
    }





    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        menu.findItem(R.id.search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home: {

                return true;
            }
            case R.id.search:

                return true;
            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerFragment.isDrawerOpen()) {

            drawerFragment.closeDrawer();

        } else {

            super.onBackPressed();
        }
    }

    @Override
    public void setTitle(CharSequence title) {

        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


}
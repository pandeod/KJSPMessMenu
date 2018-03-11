package com.example.onkarpande.kjspmessmenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // final variables
    public static final String ANONYMOUS="Android";
    public static final String ANONYMOUS_EMAIL="android@studio.com";
    public static final String ANONYMOUS_PIC_URL="";
    public static final int RC_SIGN_IN=1;

    //other objects
    private String mUserName;
    private String mUserEmail;
    private  String mUserPicUrl;

    //firebase predefined objects

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserName=ANONYMOUS;
        mUserEmail=ANONYMOUS_EMAIL;
        mUserPicUrl=ANONYMOUS_PIC_URL;

        //initialize firebase objects

        mFirebaseAuth=FirebaseAuth.getInstance();

        //persists data on device
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        View headerView= navigationView.getHeaderView(0);
        final TextView userName=headerView.findViewById(R.id.userName);
        final TextView userEmail=headerView.findViewById(R.id.userEmail);
        final ImageView userPic=headerView.findViewById(R.id.userPic);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    mUserName=user.getDisplayName();
                    mUserEmail=user.getEmail();
                    userName.setText(mUserName);
                    userEmail.setText(mUserEmail);
                    userPic.setImageResource(R.drawable.user_icon);
                }
                else
                {
                    startActivityForResult(
                            AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                            .build(),RC_SIGN_IN);
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_today) {
            setTitle("Today's Menu");
            TodayFragment mTodayFragment = new TodayFragment();
            FragmentManager mFragmentManager =getSupportFragmentManager();
            mFragmentManager.beginTransaction().replace(R.id.menu_list,mTodayFragment).commit();
        } else if (id == R.id.nav_menu) {
            setTitle("Menu");
            MenuFragment mMenuFragment=new MenuFragment();
            FragmentManager mFragmentManager =getSupportFragmentManager();
            mFragmentManager.beginTransaction().replace(R.id.menu_list,mMenuFragment).commit();

        } else if (id == R.id.nav_settings) {
            setTitle("Settings");
          SettingFragment mSettingFragment = new SettingFragment();
            FragmentManager mFragmentManager =getSupportFragmentManager();
            mFragmentManager.beginTransaction().replace(R.id.menu_list,mSettingFragment).commit();

        } else if (id == R.id.nav_logout) {
           if(mFirebaseAuth.getCurrentUser()!=null)
            AuthUI.getInstance().signOut(this);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.Beans.Student;

public class InActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected Student myInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        myInfo = (Student)intent.getSerializableExtra("myInfo");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        NotDbMainFragment notdbMainFragment = new NotDbMainFragment();
        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().add(R.id.content_in, notdbMainFragment).addToBackStack(null).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void set_Schedule_Button(View v){
        /* TODO: 스케줄 설정 액티비티로 전환
        *  신규 .java 파일과 연동
        * */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Skips one activity to go back twice
            FragmentManager fm = getSupportFragmentManager();
            int backStackNum = fm.getBackStackEntryCount();
            if(backStackNum == 1) {
                finish();
            } else if(backStackNum > 2) {
                for(int i = backStackNum; i > 2; i--) {
                    fm.popBackStack();
                }
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.in, menu);
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


        if (id == R.id.nav_time_table) {

            TimeTableFragment timeTableFragment = new TimeTableFragment();
            FragmentManager manager= getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_in, timeTableFragment).addToBackStack(null).commit();

            Bundle bundle = new Bundle(1);
            bundle.putSerializable("myInfo", myInfo);
            timeTableFragment.setArguments(bundle);

            //DrawTimeTableFragment drawTimeTableFragment = new DrawTimeTableFragment();
            //FragmentManager fragmentManager = getSupportFragmentManager();
            //fragmentManager.beginTransaction().replace(R.id.content_in, drawTimeTableFragment).addToBackStack(null).commit();
        } else if (id == R.id.nav_edit_mem_info) {
            EditMemInfoFragment editMemInfoFragment = new EditMemInfoFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_in, editMemInfoFragment).addToBackStack(null).commit();

            Bundle bundle = new Bundle(1);
            bundle.putSerializable("myInfo", myInfo);
            editMemInfoFragment.setArguments(bundle);
        } else if (id == R.id.nav_matching) {
            MatchFragment matchFragment = new MatchFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_in, matchFragment).addToBackStack(null).commit();

            Bundle bundle = new Bundle(1);
            bundle.putSerializable("myInfo", myInfo);
            matchFragment.setArguments(bundle);
        } else if (id == R.id.nav_appointment) {

        } else if (id == R.id.nav_preference) {

        }else if (id == R.id.nav_set_profile_image) {
            SetProfileImageFragment setProfileImageFragment = new SetProfileImageFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_in, setProfileImageFragment).addToBackStack(null).commit();

            Bundle bundle = new Bundle(1);
            bundle.putSerializable("myInfo", myInfo);
            setProfileImageFragment.setArguments(bundle);
        } else if (id == R.id.nav_chat) {
            ChatFragment chatFragment = new ChatFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_in, chatFragment).addToBackStack(null).commit();

            Bundle bundle = new Bundle(1);
            bundle.putSerializable("myInfo", myInfo);
            chatFragment.setArguments(bundle);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void set_Profile(View view){
        Intent intent = new Intent(getApplicationContext(), SetProfileImage.class);
        startActivity(intent);
    }
}

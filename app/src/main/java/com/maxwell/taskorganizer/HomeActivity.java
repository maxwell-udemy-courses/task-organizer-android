package com.maxwell.taskorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.maxwell.taskorganizer.auth.LoginActivity;
import com.maxwell.taskorganizer.auth.models.UserTask;
import com.maxwell.taskorganizer.calendar.TaskCalendarFragment;
import com.maxwell.taskorganizer.creation.TaskCreateFragment;
import com.maxwell.taskorganizer.list.TaskListFragment;
import com.maxwell.taskorganizer.search.TaskSearchFragment;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        populateUserData(navigationView.getHeaderView(0));

        initFragmentList();
    }

    void populateUserData(View v){
        TextView tvUserFullName = v.findViewById(R.id.tvUserFullName);
        TextView tvUserEmail = v.findViewById(R.id.tvUserEmail);

        UserTask currentUser = pm.getCurrentUser();

        tvUserFullName.setText(currentUser.getFullName());
        tvUserEmail.setText(currentUser.getUserName());
    }

    void initFragmentList(){
        TaskListFragment fragment = new TaskListFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        boolean isFragment = true;

        int id = item.getItemId();

        if (id == R.id.nav_task_list) {
            fragment = new TaskListFragment();
        } else if (id == R.id.nav_task_create) {
            fragment = new TaskCreateFragment();
        } else if (id == R.id.nav_task_search) {
            fragment = new TaskSearchFragment();
        } else if (id == R.id.nav_calendar) {
            fragment = new TaskCalendarFragment();
        } else if (id == R.id.nav_logout) {
            isFragment = false;
            pm.setCurrentUser(null);
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if(isFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

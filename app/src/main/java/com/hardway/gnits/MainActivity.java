package com.hardway.gnits;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hardway.gnits.databaseoffline.Login_DONE;
import com.hardway.gnits.fragments.DL;
import com.hardway.gnits.fragments.DN;
import com.hardway.gnits.fragments.Forum;
import com.hardway.gnits.fragments.Gps;
import com.hardway.gnits.fragments.HomeFragment;
import com.hardway.gnits.fragments.Questions;
import com.hardway.gnits.fragments.SM;
import com.hardway.gnits.fragments.Share;
import com.hardway.gnits.fragments.Tech;
import com.hardway.gnits.login.LoginRegister;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Login_DONE login_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        login_done = new Login_DONE(MainActivity.this);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            login_done.setLoggedIn(false);
            Intent i = new Intent(MainActivity.this, LoginRegister.class);
            startActivity(i);
            finish();
            return true;
        }

        if(id==R.id.action_upload_forum)
        {
            Intent i = new Intent(MainActivity.this, HomeFragment.class);
            startActivity(i);
            finish();
            return true;


        }


        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SM(), "Studious Monk");
        adapter.addFragment(new DL(), "Digital Library");
        adapter.addFragment(new Share(),"Share");
        adapter.addFragment(new DN(),"Fest");
        adapter.addFragment(new Forum(),"Forum");
        adapter.addFragment(new Tech(),"Tech");
        adapter.addFragment(new Gps(),"Others");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

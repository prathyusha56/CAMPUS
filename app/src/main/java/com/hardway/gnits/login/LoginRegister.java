package com.hardway.gnits.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hardway.gnits.MainActivity;
import com.hardway.gnits.R;
import com.hardway.gnits.databaseoffline.Login_DONE;

import java.util.ArrayList;
import java.util.List;



public class LoginRegister extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Login_DONE login_done;
    boolean login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        login_done = new Login_DONE(LoginRegister.this);

        login = login_done.getUserLoggedIn();

        viewPager = (ViewPager) findViewById(R.id.viewpager3);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs3);
        tabLayout.setupWithViewPager(viewPager);


        if(login == true)
        {
            Intent i = new Intent(LoginRegister.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Login(), "Login");
        adapter.addFrag(new Register(), "Register");
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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

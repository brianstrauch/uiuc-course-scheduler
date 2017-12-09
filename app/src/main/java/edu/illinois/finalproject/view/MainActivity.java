package edu.illinois.finalproject.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.view.CourseListFragment;
import edu.illinois.finalproject.view.ScheduleFragment;

public class MainActivity extends AppCompatActivity {
    private Adapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new Adapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class Adapter extends FragmentPagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public String getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Courses";
                case 1:
                    return "Schedule";
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new CourseListFragment();
                case 1:
                    return new ScheduleFragment();
            }
            return null;
        }
    }
}


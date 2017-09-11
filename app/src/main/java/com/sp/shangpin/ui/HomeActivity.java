package com.sp.shangpin.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sp.shangpin.R;
import com.sp.shangpin.fragments.FragmentHome;
import com.sp.shangpin.fragments.FragmentLotto;
import com.sp.shangpin.fragments.FragmentMine;
import com.sp.shangpin.fragments.FragmentStore;

import java.lang.reflect.Field;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private BottomNavigationView navigation;
    private Fragment fragmentHome;
    private Fragment fragmentLotto;
    private Fragment fragmentStore;
    private Fragment fragmentMine;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (null == fragmentHome) {
                        fragmentHome = new FragmentHome();
                    }
                    loadFragment(fragmentHome);
                    return true;
                case R.id.navigation_lotto:
                    if (null == fragmentLotto) {
                        fragmentLotto = new FragmentLotto();
                    }
                    loadFragment(fragmentLotto);
                    return true;
                case R.id.navigation_store:
                    if (null == fragmentStore) {
                        fragmentStore = new FragmentStore();
                    }
                    loadFragment(fragmentStore);
                    return true;
                case R.id.navigation_mine:
                    if (null == fragmentMine) {
                        fragmentMine = new FragmentMine();
                    }
                    loadFragment(fragmentMine);
                    return true;
            }
            return false;
        }

    };
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            navigation.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void checkTab(int index) {
        if (index >= 0 && index <= 3) {
            navigation.getMenu().getItem(index).setChecked(true);
            switch (index) {
                case 0:
                    loadFragment(new FragmentHome());
                    break;
                case 1:
                    loadFragment(new FragmentLotto());
                    break;
                case 2:
                    loadFragment(new FragmentStore());
                    break;
                case 3:
                    loadFragment(new FragmentMine());
                    break;
            }
        }
    }

    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);
        loadFragment(new FragmentHome());
        setTitle("");
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }

}

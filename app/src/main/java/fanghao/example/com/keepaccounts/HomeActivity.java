package fanghao.example.com.keepaccounts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

public class HomeActivity extends BaseActivity
                          implements View.OnClickListener,
                                     NavigationView.OnNavigationItemSelectedListener,
                                     HomeFragment.OnFragmentInteractionListener,
                                     IncomeAddFragment.OnFragmentInteractionListener,
                                     AllAcountListFragment.OnFragmentInteractionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        StatusBarCompat.compat(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(R.color.status_bar_color);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//声明NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//设置图标为原本色彩
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

//        navigationView.getResources().getDimensionPixelSize(R.dimen.navigation_icon_size);
//获取头部内控件
        View headerView = navigationView.getHeaderView(0);
        RoundedImageView riAvatar = (RoundedImageView) headerView.findViewById(R.id.ri_nhh_avatar);
        riAvatar.setOnClickListener(this);

        HomeFragment fragment = new HomeFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container_home, fragment)
                .commit();
        setTitle("总额");
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
        getMenuInflater().inflate(R.menu.home, menu);
        return false;
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

//菜单列表的点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//收入记账
        if (id == R.id.nav_start) {
            setTitle(item.getTitle());
            IncomeAddFragment fragment = new IncomeAddFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .addToBackStack(null)
                    .commit();
//支出记账
        } else if (id == R.id.nav_expense) {
            setTitle(item.getTitle());
            ExpenseAddFragment fragment = new ExpenseAddFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .addToBackStack(null)
                    .commit();
//自定义记账
        } else if (id == R.id.nav_cluster) {
            setTitle(item.getTitle());
            AcountAddCluFragment fragment = new AcountAddCluFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .addToBackStack(null)
                    .commit();

//今日账单
        } else if (id == R.id.nav_today) {
            setTitle(item.getTitle());
            LineChartFragment fragment = new LineChartFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .addToBackStack(null)
                    .commit();
//今月账单
        } else if (id == R.id.nav_month) {
            setTitle(item.getTitle());
            LineChartMonthFragment fragment = new LineChartMonthFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .addToBackStack(null)
                    .commit();
//今年账单
        } else if (id == R.id.nav_year) {
            setTitle(item.getTitle());
            LineChartYearFragment fragment = new LineChartYearFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .addToBackStack(null)
                    .commit();

//所有账单
        } else if (id == R.id.nav_all_account) {
            setTitle(item.getTitle());
            AllAcountListFragment fragment = new AllAcountListFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .addToBackStack(null)
                    .commit();
//总额
        } else if (id == R.id.nav_sum) {
            setTitle(item.getTitle());
            HomeFragment fragment = new HomeFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .commit();
////关于
//        } else if (id == R.id.nav_sum) {
//            setTitle(item.getTitle());
//            AboutFragment fragment = new AboutFragment();
//            getFragmentManager().beginTransaction()
//                   .replace(R.id.container_home, fragment)
//                   .commit();
//账单视图
        }

        else if (id == R.id.nav_send) {
            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, ListViewMultiChartActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//再按一次我就走
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次我就走", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
/*
* 头部控件调用的写法*/
    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "尚未开启自定义用户模块", Toast.LENGTH_SHORT).show();



    }
}

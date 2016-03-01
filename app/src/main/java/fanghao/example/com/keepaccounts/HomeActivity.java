package fanghao.example.com.keepaccounts;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import fanghao.example.com.keepaccounts.deprecated.DbFragment;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnFragmentInteractionListener,
AcountAddFragment.OnFragmentInteractionListener,TodayAcountListFragment.OnFragmentInteractionListener{

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment fragment = new HomeFragment();
        
        getFragmentManager().beginTransaction()
                .replace(R.id.container_home,fragment)
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_start) {
            setTitle(item.getTitle());
            AcountAddFragment fragment = new AcountAddFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_today) {
            setTitle(item.getTitle());
            LineChartFragment fragment = new LineChartFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_allacount) {
            setTitle(item.getTitle());
            TodayAcountListFragment fragment = new TodayAcountListFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home,fragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_sum) {
            setTitle(item.getTitle());
            HomeFragment fragment = new HomeFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home,fragment)
                    .commit();
        } /*else if (id == R.id.nav_share) {
            setTitle(item.getTitle());
            DbFragment fragment = new DbFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home,fragment)
                    .commit();
        }*/ else if (id == R.id.nav_send) {
            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, ListViewMultiChartActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   /* public void insert(SQLiteDatabase db,String figure, String remarks, String category, String date,String type) {
        final String INSERT_TABLE_SQL="insert into acounts(figure,remarks,category,date,type) values (?,?,?,?,?)";
        db.execSQL(INSERT_TABLE_SQL, new String[]{figure, remarks, category, date, type});
    }*/

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

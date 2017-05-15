package fanghao.example.com.keepaccounts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by zeng on 2017/4/23 0023.
 * 加载页面
 */
public class LaunchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        },1000);

    }

    private void goHome() {
        Intent intent=new Intent(LaunchActivity.this,HomeActivity.class);
        LaunchActivity.this.startActivity(intent);
        LaunchActivity.this.finish();
    }
}

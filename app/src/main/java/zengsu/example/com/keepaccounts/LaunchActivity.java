package zengsu.example.com.keepaccounts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by zeng on 2017/4/23 0023.
 * 加载页面
 */
public class LaunchActivity extends AppCompatActivity {

    private SharedPreferences sp;
    public String email;
    public String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        //获取当前程序的SharedPreferences对象（文件名，文件存储的方式）
        sp = getSharedPreferences("file", Context.MODE_PRIVATE);
        email=sp.getString("email",null);
       password=sp.getString("password",null);
//

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        },1000);

    }

    private void goHome() {
        if (email == null || email.length()<=0){
            Intent intent=new Intent(LaunchActivity.this,LoginActivity.class);
            LaunchActivity.this.startActivity(intent);
            LaunchActivity.this.finish();

        }else {

            Intent intent = new Intent(LaunchActivity.this, HomeActivity.class);
            LaunchActivity.this.startActivity(intent);
            LaunchActivity.this.finish();

        }




    }




}

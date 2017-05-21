package zengsu.example.com.keepaccounts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import zengsu.example.com.keepaccounts.entity.User;


/**
 * Created by zeng on 2017/5/16 0016.
 * 登录首页
 */
@ContentView(R.layout.activity_about)
public class AboutActivity extends BaseActivity{
    DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("myUser")
            //.setDbDir(new File("/data/data/fanghao.example.com.keepaccounts"))
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // TODO: ...
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                }
            });
    @ViewInject(R.id.tv_about_name)
    private TextView mName;
    @ViewInject(R.id.tv_about_email)
    private TextView mEmail;
    @ViewInject(R.id.btn_quit)
    private Button mQuit;

    private SharedPreferences sp;
    public String email;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(R.color.status_bar_color);
        setTitle("关于用户");
        x.view().inject(this);
        //获取当前程序的SharedPreferences对象（文件名，文件存储的方式）
        sp = getSharedPreferences("file", Context.MODE_PRIVATE);
        email=sp.getString("email",null);
        DbManager db = x.getDb(daoConfig);
        try {
            User user =db.selector(User.class).where("email", "=",email).findFirst();

            mName.setText(user.getName().toString());
            mEmail.setText(user.getEmail().toString());

        } catch (DbException e) {
            e.printStackTrace();
        }





    }

    @Event(R.id.btn_quit)
    private void Quit(View view){
        setResult(RESULT_OK);
        finish();



    }


}

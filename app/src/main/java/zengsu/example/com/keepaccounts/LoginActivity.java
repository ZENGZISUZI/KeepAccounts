package zengsu.example.com.keepaccounts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity{
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
    @ViewInject(R.id.et_name)
    private EditText etName;
    @ViewInject(R.id.et_password)
    private EditText etPassword;
//    @ViewInject(R.id.tv_forget)
//    private TextView tvForget;
    @ViewInject(R.id.tv_resiger)
    private TextView tvResig;
    @ViewInject(R.id.btn_login)
    private Button btnLogin;
    private SharedPreferences sp;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);






        //获取当前程序的SharedPreferences对象（文件名，文件存储的方式）
        sp = getSharedPreferences("file", Context.MODE_PRIVATE);
    }
    @Event(R.id.btn_login)
    private void Login(View view){
        String email = etName.getText().toString().trim();
        String  password= etPassword.getText().toString().trim();
        DbManager db = x.getDb(daoConfig);
        //进行判断如果查询不到name，或者密码不匹配提示不存在用户名或者重新输入密码
        try {
            User userFindName = db.selector(User.class).where("email", "=", email).findFirst();
            if (userFindName==null){
                Toast.makeText(this, "邮箱不存在", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etPassword.setText("");
            }else {
                String userFindPassword = userFindName.getPassword();
                if (!userFindPassword.equals(password)){
                    Toast.makeText(this, "密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                   //                    etName.setText("");
                    etPassword.setText("");
                }else {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("email",email);
                    edit.putString("password",password);
                    edit.commit();
                    startActivity(new Intent(this,HomeActivity.class));
                    finish();
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }


    }

//    @Event(R.id.tv_forget)
//    private void forget(View view){
//        startActivity(new Intent(this,ForgetActivity.class));
//
//    }

    @Event(R.id.tv_resiger)
    private void resiger(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
//        startActivity(intent);
        startActivityForResult(intent,RESULT_FIRST_USER);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_FIRST_USER:
//判断注册是否成功  如果注册成功
                if(resultCode==RESULT_OK){
//则获取data中的账号和密码  动态设置到EditText中
                    String email=data.getStringExtra("email");
                    String pwd=data.getStringExtra("pwd");
                    etName.setText(email);
                    etPassword.setText(pwd);
                }
                break;
        }

    }





}

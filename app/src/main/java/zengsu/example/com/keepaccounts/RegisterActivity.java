package zengsu.example.com.keepaccounts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import zengsu.example.com.keepaccounts.entity.User;

/**
 * Created by zeng on 2017/5/17 0017.
 * 注册页面
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity{
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

    @ViewInject(R.id.btn_res_resigter)
    private Button btnRegister;
    @ViewInject(R.id.et_res_name)
    private EditText etName;
    @ViewInject(R.id.et_res_email)
    private EditText etEmail;
    @ViewInject(R.id.et_res_password)
    private EditText etPassword;
    @ViewInject(R.id.et_re_res_password)
    private EditText etRePassword;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }
    /** 
      * 描述：是否是邮箱. 
      * 
      * @param str 指定的字符串 
      * @return 是否是邮箱:是为true，否则false 
      */
    public static Boolean isEmail(String str) {
         Boolean isEmail = false;
         String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
         if (str.matches(expr)) {
             isEmail = true;
             }
         return isEmail;
    }
    @Event(R.id.btn_res_resigter)
    private void Register(View view){
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String rePassword = etRePassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if(isEmail(email)==true){
            if (!name.equals("")&&!password.equals("")&&!rePassword.equals("")&&!email.equals("")){

                if (password.equals(rePassword)){
                     User user = new User();
                     user.setName(name);
                     user.setEmail(email);
                     user.setPassword(password);
                     DbManager db = x.getDb(daoConfig);
                    try {
                        User userFindEmail = db.selector(User.class).where("email", "=", email).findFirst();
//                    User user1=db.selector(User.class).where("email","=",email).findFirst();
                        if (userFindEmail==null) {
                             db.save(user);
                             Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                             Intent data=new Intent();
                             data.putExtra("email", email);
                             data.putExtra("pwd", password);
                             setResult(RESULT_OK, data);
                             finish();
                        }else {
                              Toast.makeText(this, "邮箱已注册，重新输入", Toast.LENGTH_SHORT).show();
                              etName.setText("");
                              etEmail.setText("");
                              etPassword.setText("");
                              etRePassword.setText("");
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etRePassword.setText("");
                }
            }else {
                 Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                 etRePassword.setText("");
                 etPassword.setText("");
                 etName.setText("");
                 etEmail.setText("");
        }


        }else {
            Toast.makeText(this, "请检查邮箱格式", Toast.LENGTH_SHORT).show();
            etRePassword.setText("");
            etPassword.setText("");
            etEmail.setText("");

        }
    }


}

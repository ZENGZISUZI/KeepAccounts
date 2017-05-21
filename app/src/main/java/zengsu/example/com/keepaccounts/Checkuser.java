package zengsu.example.com.keepaccounts;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import zengsu.example.com.keepaccounts.entity.Account;
import zengsu.example.com.keepaccounts.entity.User;

import static android.R.attr.id;

/**
 * Created by zeng on 2017/5/19 0019.
 */

public class Checkuser {
    public int  userId;
    DbManager.DaoConfig daoConfig2 = new DbManager.DaoConfig()
            .setDbName("myAccount")
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                }
            });
    DbManager.DaoConfig daoConfig1 = new DbManager.DaoConfig()
            .setDbName("user")
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                }
            });
   public void  check() {
       DbManager db2 = x.getDb(daoConfig2);
       DbManager db1 = x.getDb(daoConfig1);
       try {
           User userId1 = db1.selector(User.class).where("userid","=",id).findFirst();
           userId=userId1.getUserid();

           Account userId2 = db2.selector(Account.class).where("userid","=",userId).findFirst();

       } catch (DbException e) {
           e.printStackTrace();
       }


    }

}

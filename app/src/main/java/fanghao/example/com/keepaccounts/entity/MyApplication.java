package fanghao.example.com.keepaccounts.entity;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by zeng on 2017/5/10.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}

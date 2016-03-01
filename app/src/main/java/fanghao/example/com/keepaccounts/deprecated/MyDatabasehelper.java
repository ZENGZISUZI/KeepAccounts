package fanghao.example.com.keepaccounts.deprecated;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fanghao on 2015/12/6.
 */
public class MyDatabasehelper extends SQLiteOpenHelper {
    final String CREATE_TABLE_SQL="create table acounts(_id integer primary key autoincrement," +
            "figure real,remarks VARCHAR,category VARCHAR,date VARCHAR,type VARCHAR)";
    public MyDatabasehelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table acounts");
        db.execSQL(CREATE_TABLE_SQL);
    }
}

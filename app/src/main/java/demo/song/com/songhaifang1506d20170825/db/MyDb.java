package demo.song.com.songhaifang1506d20170825.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * data:2017/8/25 0025.
 * Created by ：宋海防  song on
 */

public class MyDb extends SQLiteOpenHelper {
    public MyDb(Context context) {
        super(context, "yuekao.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table haha(String result)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

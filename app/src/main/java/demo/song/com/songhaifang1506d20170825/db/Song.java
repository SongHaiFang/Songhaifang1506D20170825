package demo.song.com.songhaifang1506d20170825.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * data:2017/8/25 0025.
 * Created by ：宋海防  song on
 */

public class Song {
    private SQLiteDatabase db;

    public Song(Context context) {
        MyDb myDb = new MyDb(context);
        db = myDb.getWritableDatabase();
    }

    public void add(String result){
        ContentValues values = new ContentValues();
        values.put("result",result);
        db.insert("haha",null,values);
    }
}

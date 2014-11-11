package ifmo.ru.lesson5.main_activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mariashka on 11/10/14.
 */
public class RSSDB extends SQLiteOpenHelper{

    static final String DB_NAME = "my_db";
    static public final int DB_VERSION = 1;

    static public final String FEED_TABLE = "feed";
    static public final String SUB_TABLE = "sub";

    static public final String FEED_ID = "_id";
    static public final String FEED_TITLE = "title";
    static public final String FEED_DESCR = "description";
    static public final String FEED_LINK = "link";

    static public final String SUB_ID = "_id";
    static public final String SUB_LINK = "link";

    static final String DB_FEED_CREATE = "create table " + FEED_TABLE + "("
      + FEED_ID + " integer primary key autoincrement, "
      + FEED_TITLE + " text, " + FEED_DESCR + " text, " + FEED_LINK + " text" + ");";

    static final String DB_SUB_CREATE = "create table " + SUB_TABLE + "("
      + SUB_ID + " integer primary key autoincrement, "
      + SUB_LINK + " text" + ");";


    RSSDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_FEED_CREATE);
        db.execSQL(DB_SUB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}

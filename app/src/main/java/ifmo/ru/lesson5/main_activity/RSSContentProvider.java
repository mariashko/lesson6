package ifmo.ru.lesson5.main_activity;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by mariashka on 11/10/14.
 */
public class RSSContentProvider extends ContentProvider {

    static public final String FEED_TABLE = "feed";
    static public final String SUB_TABLE = "sub";


    static public final String FEED_ID = "_id";
    static public final String FEED_TITLE = "title";
    static public final String FEED_DESCR = "description";
    static public final String FEED_LINK = "link";

    static public final String SUB_ID = "_id";
    static public final String SUB_LINK = "link";

    static public final String AUTHORITY = "ru.ifmo.lesson5.provider.RSSContentProvider";
    static public final String FEED_PATH = "feed";
    static public  final String SUB_PATH = "sub";

    public static final Uri FEED_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + FEED_PATH);

    public static final Uri SUB_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + SUB_PATH);

    public static final String FEED_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + FEED_PATH;

    public static final String FEED_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + FEED_PATH;

    public static final String SUB_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + SUB_PATH;

    public static final String SUB_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + SUB_PATH;


    static final int URI_FEED_ID = 1;
    static final int URI_SUB_ID = 2;
    static final int URI_FEED = 3;
    static final int URI_SUB = 4;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, FEED_PATH + "/#", URI_FEED_ID);
        uriMatcher.addURI(AUTHORITY, SUB_PATH + "/#", URI_SUB_ID);
        uriMatcher.addURI(AUTHORITY, FEED_PATH, URI_FEED);
        uriMatcher.addURI(AUTHORITY, SUB_PATH, URI_SUB);
    }

    RSSDB dbHelper;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new RSSDB(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String id;
        db = dbHelper.getWritableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case URI_FEED:
                cursor = db.query(FEED_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case URI_SUB:
                cursor = db.query(SUB_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case URI_FEED_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = FEED_ID + " = " + id;
                } else {
                    selection = selection + " AND " + FEED_ID + " = " + id;
                }
                cursor = db.query(FEED_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case URI_SUB_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = FEED_ID + " = " + id;
                } else {
                    selection = selection + " AND " + FEED_ID + " = " + id;
                }
                cursor = db.query(SUB_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), FEED_CONTENT_URI);
        cursor.setNotificationUri(getContext().getContentResolver(), SUB_CONTENT_URI);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_SUB:
                return SUB_CONTENT_TYPE;
            case URI_FEED:
                return SUB_CONTENT_ITEM_TYPE;
            case URI_FEED_ID:
                return FEED_CONTENT_ITEM_TYPE;
            case URI_SUB_ID:
                return FEED_CONTENT_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        Uri resultUri;
        long rowID;
        switch (uriMatcher.match(uri)) {
            case URI_FEED:
                rowID = db.insert(FEED_TABLE, null, values);
                resultUri = ContentUris.withAppendedId(FEED_CONTENT_URI, rowID);
                break;

            case URI_SUB:
                rowID = db.insert(SUB_TABLE, null, values);
                resultUri = ContentUris.withAppendedId(SUB_CONTENT_URI, rowID);
                break;

            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id;
        db = dbHelper.getWritableDatabase();
        int cnt = 0;

        switch (uriMatcher.match(uri)) {
            case URI_FEED:
                break;
            case URI_SUB:
                break;
            case URI_FEED_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = FEED_ID + " = " + id;
                } else {
                    selection = selection + " AND " + FEED_ID + " = " + id;
                }
                cnt = db.delete(FEED_TABLE, selection, selectionArgs);
                break;
            case URI_SUB_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = SUB_ID + " = " + id;
                } else {
                    selection = selection + " AND " + SUB_ID + " = " + id;
                }
                cnt = db.delete(SUB_TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int cnt = 0;
        String id;
        switch (uriMatcher.match(uri)) {
            case URI_FEED:
                break;
            case URI_SUB:
                break;
            case URI_FEED_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = FEED_ID + " = " + id;
                } else {
                    selection = selection + " AND " + FEED_ID + " = " + id;
                }
                cnt = db.update(FEED_TABLE, values, selection, selectionArgs);
                break;
            case URI_SUB_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = SUB_ID + " = " + id;
                } else {
                    selection = selection + " AND " + SUB_ID + " = " + id;
                }
                cnt = db.update(SUB_TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }
}

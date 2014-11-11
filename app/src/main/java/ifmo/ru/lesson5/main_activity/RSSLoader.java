package ifmo.ru.lesson5.main_activity;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariashka on 11/10/14.
 */
public class RSSLoader extends AsyncTaskLoader<List<RSSItem>> {
    Context context = null;
    final Uri FEED_URI = Uri.parse("content://ru.ifmo.lesson5.provider.RSSContentProvider/feed");
    final Uri SUB_URI = Uri.parse("content://ru.ifmo.lesson5.provider.RSSContentProvider/sub");
    public RSSLoader(Context c) {
        super(c);
        context = c;
    }

    @Override
    public List<RSSItem> loadInBackground() {
        return offlineHandle();
    }

    public List<RSSItem> offlineHandle() {
        List<RSSItem> list = new ArrayList<RSSItem>();
        Cursor cursor = context.getContentResolver().query(FEED_URI, null, null, null, null);
        cursor.moveToFirst();
        do {
            if (cursor.isAfterLast())
                break;
            String title = cursor.getString(1);
            String text = cursor.getString(2);
            String link = cursor.getString(3);
            list.add(new RSSItem(title, text, link));
        } while (cursor.moveToNext());
        cursor.close();
        return list;
    }

}

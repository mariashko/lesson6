package ifmo.ru.lesson5.main_activity;

import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by mariashka on 11/10/14.
 */
public class RSSService extends IntentService {

    final Uri FEED_URI = Uri.parse("content://ru.ifmo.lesson5.provider.RSSContentProvider/feed");
    final Uri SUB_URI = Uri.parse("content://ru.ifmo.lesson5.provider.RSSContentProvider/sub");

    public RSSService() {
        super("net");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List <RSSItem> list = new ArrayList<RSSItem>();
        Cursor subs = getContentResolver().query(RSSContentProvider.SUB_CONTENT_URI, null, null, null, null);
        if (subs != null) {
            subs.moveToFirst();
            try {
                do {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    RSSHandler handler = new RSSHandler();
                    if (subs.isAfterLast())
                        break;
                    String s = subs.getString(1);
                    URL url = new URL(s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream in = connection.getInputStream();
                    saxParser.parse(in, handler);

                    List<RSSItem> curr = handler.getItems();
                    for (int i = 0; i < curr.size(); i++) {
                        list.add(curr.get(i));
                    }
                    connection.disconnect();
                } while (subs.moveToNext());
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Cursor cursor = getContentResolver().query(FEED_URI, null, null, null, null);
        cursor.moveToFirst();
            int n = cursor.getCount();
            for (int i = 0; i < n; i++) {
                Uri uri = ContentUris.withAppendedId(FEED_URI, 0);
                getContentResolver().delete(uri, null, null);
            }
            for (RSSItem aCurr : list) {
                ContentValues cv = new ContentValues();
                cv.put(RSSContentProvider.FEED_TITLE, aCurr.getTitle());
                cv.put(RSSContentProvider.FEED_DESCR, aCurr.getText());
                cv.put(RSSContentProvider.FEED_LINK, aCurr.getUrl());
                getContentResolver().insert(FEED_URI, cv);
            }
        }
}

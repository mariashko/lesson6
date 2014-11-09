package ifmo.ru.lesson5.main_activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
 * Created by mariashka on 10/19/14.
 */
public class RSSDownloader extends AsyncTask <List<String>, String, List<RSSItem> > {
    ProgressDialog dialog;
    Context context;

    RSSDownloader(Context c) {
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(context);
        dialog.setMessage("Downloading content. Please, wait");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }
    @Override
    protected List<RSSItem> doInBackground(List<String>... params) {
        List <RSSItem> list = new ArrayList<RSSItem>();
        try {
            for (int i = 0; i < params[0].size(); i++) {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                RSSHandler handler = new RSSHandler();

                URL url = new URL(params[0].get(i));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                saxParser.parse(in, handler);

                List <RSSItem> curr = handler.getItems();

                for (RSSItem aCurr : curr) {
                    list.add(aCurr);
                }
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            Log.i("MalformedURLException ", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("IOException", e.getMessage());
            e.printStackTrace();
        } catch (SAXException e) {
            Log.i("SAXException", e.getMessage());
            e.printStackTrace();
        } catch (ParserConfigurationException e1) {
            Log.i("figurationException ", e1.getMessage());
            e1.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<RSSItem> s) {
        super.onPostExecute(s);
        dialog.dismiss();
    }
}

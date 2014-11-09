package ifmo.ru.lesson5.main_activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ifmo.ru.lesson5.R;


public class RSS extends ListActivity {

    RSSDownloader downloader;
    public List<String> subscriptions = new ArrayList<String>();
    public List<RSSItem> rssItems;
    public RSSAdapter adapter;
    public final String SUBSCRIPTIONS = "subscr";
    public Intent intentSubscr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        if (getIntent().hasExtra(SUBSCRIPTIONS)) {
            String[] s = getIntent().getExtras().getStringArray(SUBSCRIPTIONS);
            subscriptions = new ArrayList<String>();
            Collections.addAll(subscriptions, s);
        } else {
            subscriptions.add("http://feeds.bbci.co.uk/news/rss.xml");
            subscriptions.add("http://feeds.feedburner.com/zenhabits?format=xml");
        }
        downloader = new RSSDownloader(this);
        try {
            rssItems = downloader.execute(subscriptions).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new RSSAdapter(rssItems);
        setListAdapter(adapter);

        intentSubscr = new Intent(this, SubscriptionList.class);
        final Intent intent = new Intent(this, PreviewActivity.class);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = rssItems.get(position).getUrl();
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

    public void editSubscrClick(View view) {
        String[] s = new String[subscriptions.size()];
        for (int i = 0; i < subscriptions.size(); i++) {
            s[i] = subscriptions.get(i);
        }
        intentSubscr.putExtra(SUBSCRIPTIONS, s);
        startActivity(intentSubscr);
        adapter.notifyDataSetChanged();
    }
}

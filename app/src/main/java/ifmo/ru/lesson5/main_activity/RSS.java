package ifmo.ru.lesson5.main_activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;
import ifmo.ru.lesson5.R;


public class RSS extends ListActivity {

    RSSLoader downloader;
    public List<RSSItem> rssItems;
    public RSSAdapter adapter;
    public Intent intentSubscr;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        intent = new Intent(this, RSSService.class);
        stopService(intent);
        startService(intent);

        downloader = new RSSLoader(this);
        rssItems = downloader.loadInBackground();
        adapter = new RSSAdapter(rssItems);
        setListAdapter(adapter);

        intentSubscr = new Intent(this, SubscriptionList.class);
        final Intent intent2 = new Intent(this, PreviewActivity.class);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = rssItems.get(position).getUrl();
                intent2.putExtra("url", url);
                startActivity(intent2);
            }
        });
    }

    public void editSubscrClick(View view) {
        startActivity(intentSubscr);
        stopService(intent);
        startService(intent);
        rssItems = downloader.loadInBackground();
        adapter.notifyDataSetChanged();
    }
}

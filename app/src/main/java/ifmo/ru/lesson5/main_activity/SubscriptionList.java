package ifmo.ru.lesson5.main_activity;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ifmo.ru.lesson5.R;

public class SubscriptionList extends ListActivity {

    public List<String> subscr;
    public SubscriptionAdapter adapterSubscr;
    final Uri FEED_URI = Uri.parse("content://ru.ifmo.lesson5.provider.RSSContentProvider/feed");
    final Uri SUB_URI = Uri.parse("content://ru.ifmo.lesson5.provider.RSSContentProvider/sub");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_list);

        subscr = new ArrayList<String>();
        Cursor subs = getContentResolver().query(SUB_URI, null, null, null, null);
        subs.moveToFirst();
        do {
            if (subs.isAfterLast())
                break;
            String s = subs.getString(1);
            subscr.add(s);
        } while (subs.moveToNext());

        adapterSubscr = new SubscriptionAdapter(subscr);
        setListAdapter(adapterSubscr);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = ContentUris.withAppendedId(SUB_URI, position);
                getContentResolver().delete(uri, null, null);
                Log.d("delete", uri.toString());
                String mesg = "Further news of this feed wouldn't be downloaded";
                Toast toast = Toast.makeText(getApplicationContext(), mesg, Toast.LENGTH_SHORT);
                toast.show();

                adapterSubscr.data.remove(position);
                adapterSubscr.notifyDataSetChanged();
            }
        });
    }

    public void subscribeOnClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editText2);
        String s = editText.getText().toString();
        editText.setText("");

        if (!s.isEmpty()) {
            adapterSubscr.data.add(s);
            adapterSubscr.notifyDataSetChanged();
            ContentValues cv = new ContentValues();
            cv.put(RSSContentProvider.SUB_LINK, s);
            getContentResolver().insert(SUB_URI, cv);
        }
    }

    public void backToFeedOnClick(View view) {
        Intent intent = new Intent(this, RSS.class);
        startActivity(intent);
    }
}

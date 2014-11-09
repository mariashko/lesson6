package ifmo.ru.lesson5.main_activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifmo.ru.lesson5.R;

public class SubscriptionList extends ListActivity {

    public final String SUBSCRIPTIONS = "subscr";
    public List<String> subscr;
    public String[] listSubscr;
    public SubscriptionAdapter adapterSubscr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_list);

        listSubscr = getIntent().getExtras().getStringArray(SUBSCRIPTIONS);
        subscr = new ArrayList<String>();
        Collections.addAll(subscr, listSubscr);

        adapterSubscr = new SubscriptionAdapter(subscr);
        setListAdapter(adapterSubscr);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterSubscr.data.remove(position);
                adapterSubscr.notifyDataSetChanged();
            }
        });
    }

    public void subscribeOnClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editText2);
        String s = editText.getText().toString();
        editText.setText("");
        adapterSubscr.data.add(s);
        adapterSubscr.notifyDataSetChanged();
    }

    public void backToFeedOnClick(View view) {
        Intent intent = new Intent(this, RSS.class);

        String[] s = new String[subscr.size()];
        for (int i = 0; i < subscr.size(); i++)
            s[i] = subscr.get(i);

        intent.putExtra(SUBSCRIPTIONS, s);
        startActivity(intent);
    }
}

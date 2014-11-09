package ifmo.ru.lesson5.main_activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ifmo.ru.lesson5.R;

/**
 * Created by mariashka on 11/8/14.
 */
public class SubscriptionAdapter extends BaseAdapter{
    public List <String> data;

    SubscriptionAdapter() {
        data = new ArrayList<String>();
    }

    public SubscriptionAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View l = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscr_item, parent, false);

        String item = (String) getItem(position);

        TextView title = (TextView) l.findViewById(R.id.textView3);
        title.setText(item);

        TextView text = (TextView) l.findViewById(R.id.textView4);
        text.setText("Click to delete");

        return l;
    }
}

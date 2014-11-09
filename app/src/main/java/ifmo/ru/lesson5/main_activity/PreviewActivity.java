package ifmo.ru.lesson5.main_activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import ifmo.ru.lesson5.R;


public class PreviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        String url = getIntent().getExtras().getString("url");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        WebView page = (WebView) findViewById(R.id.webView);
        page.setVerticalScrollbarOverlay(true);
        page.loadUrl(url);
    }
}

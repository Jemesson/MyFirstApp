package br.com.srtorcedor.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by jemesson on 3/27/15.
 */
public class StoryActivity extends ActionBarActivity {
    private WebView webview;
    private Story story;
    private boolean isReadabilityOn = false;
    private SharedPreferences preferences;
    private static final String BASE_URL = "http://www.bbc.co.uk/news/uk-england-london-21049125#sa-ns_mchannel=rss&ns_source=PublicRSS20-sa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_story);

        if(getIntent().getExtras() != null) {
            preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            isReadabilityOn = preferences.getBoolean("readability", false);

            story = (Story) getIntent().getExtras().get("story");

            webview = (WebView) findViewById(R.id.web_view);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });

            reload();
        }
    }

    public void reload() {
        if(isReadabilityOn) {
            webview.loadUrl("http://www.instapaper.com/text?u=" + Uri.encode(BASE_URL));
        } else {
            webview.loadUrl(BASE_URL);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.story_menu, menu);
        menu.findItem(R.id.menu_toogle_readability).setChecked(isReadabilityOn);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_toogle_readability:
                isReadabilityOn = !isReadabilityOn;
                preferences.edit().putBoolean("readability", isReadabilityOn).apply();
                item.setChecked(isReadabilityOn);
                reload();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

package br.com.srtorcedor.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.callumtaylor.asynchttp.AsyncHttpClient;
import net.callumtaylor.asynchttp.response.GsonResponseHandler;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private ListView listView;
    private StoryAdapter adapter;
    private Story[] stories = {};
    private static final String URL = "https://raw.githubusercontent.com/3sidedcube/Android-BBCNews/master/feed.json";

    // comentário
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new StoryAdapter(stories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        long lastModified = System.currentTimeMillis();

        if(CacheManager.getInstance().fileExists(getFilesDir().getAbsolutePath() + "/stories")){
            lastModified = CacheManager.getInstance().fileModifiedDate(getFilesDir().getAbsolutePath() + "/stories");
            stories = (Story[]) CacheManager.getInstance().load(getFilesDir().getAbsolutePath() + "/stories");
            adapter.setItems(stories);
            adapter.notifyDataSetChanged();
        }

        boolean fileExist = CacheManager.getInstance().fileExists(getFilesDir().getAbsolutePath() + "/stories");

        if(!fileExist || (System.currentTimeMillis() - lastModified) > 60 * 1000){
            AsyncHttpClient client = new AsyncHttpClient(URL);
            client.get(new GsonResponseHandler<Story[]>(Story[].class) {
                @Override
                public void onSuccess() {
                    adapter.setItems(getContent());
                    CacheManager.getInstance().save(getFilesDir().getAbsolutePath() + "/stories", stories);
                }

                @Override
                public void onFinish(boolean failed) {
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Story story = (Story) listView.getItemAtPosition(position);

        Intent intent = new Intent(MainActivity.this, StoryActivity.class);
        intent.putExtra("story", story);

        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, String.format("Item %s was long clicked!", position), Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
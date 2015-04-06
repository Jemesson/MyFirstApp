package br.com.srtorcedor.myfirstapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.callumtaylor.asynchttp.AsyncHttpClient;
import net.callumtaylor.asynchttp.response.BitmapResponseHandler;

/**
 * Created by jemesson on 3/26/15.
 */
public class StoryAdapter extends BaseAdapter {

    private Story[] items;

    private static class StoryHolder {
        ImageView thumbnail;
        TextView title;
        TextView description;
    }

    public StoryAdapter(Story[] items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Story getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return items[position].hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Story story = getItem(position);
        final StoryHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_list_item, parent, false);

            holder = new StoryHolder();
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.description = (TextView) convertView.findViewById(R.id.desription);

            convertView.setTag(holder);
        } else {
            holder = (StoryHolder)convertView.getTag();
        }

        holder.thumbnail.setImageBitmap(null);

        if (holder.thumbnail.getTag() != null){
            ((AsyncHttpClient)holder.thumbnail.getTag()).cancel();
        }

        AsyncHttpClient loader = new AsyncHttpClient(story.getImageURL());
        loader.get(new BitmapResponseHandler() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onFinish(boolean failed) {
                if(!failed) {
                    holder.thumbnail.setTag(null);
                    holder.thumbnail.setImageBitmap(getContent());
                }
            }
        });

        holder.thumbnail.setTag(loader);
        holder.title.setText(story.getTitle());
        holder.description.setText(story.getBody());

        return convertView;
    }

    public Story[] getItems() {
        return items;
    }

    public void setItems(Story[] items) {
        this.items = items;
    }
}

package br.com.srtorcedor.myfirstapp;

import java.io.Serializable;

/**
 * Created by jemesson on 3/27/15.
 */
public class Story implements Serializable {
    private String title;
    private String body;
    private String imageURL;

    public Story(String title, String body, String imageURL) {
        this.title = title;
        this.body = body;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

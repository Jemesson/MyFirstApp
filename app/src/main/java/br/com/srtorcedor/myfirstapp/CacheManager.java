package br.com.srtorcedor.myfirstapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by jemesson on 3/31/15.
 */
public class CacheManager {
    private static CacheManager instance;

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public void save(String filename, Serializable data) {
        try {
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(filename), 8192);
            ObjectOutputStream outputStream = new ObjectOutputStream(output);

            outputStream.writeObject(data);

            outputStream.flush();
            outputStream.close();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Object load(String filename) {
        try {
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(filename), 8192);
            ObjectInputStream inputStream = new ObjectInputStream(input);

            Object data = inputStream.readObject();

            inputStream.close();
            input.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public long fileModifiedDate(String filename) {
        return new File(filename).lastModified();
    }

    public boolean fileExists(String filename) {
        return new File(filename).exists();
    }
}

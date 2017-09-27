package com.example.davidutterman.androidmovieapp;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/*
 * Helper class to get settings from asstes config file
 */
public class Config {

    private Context context;

    public Config(Context context) {
        this.context = context;
    }

    public String getProperty(String key) {
        InputStream inputStream;
        java.util.Properties properties = new java.util.Properties();
        try {
            final String CONFIG_FILE = context.getResources().getString(R.string.config_file);

            inputStream = context.getAssets().open(CONFIG_FILE);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
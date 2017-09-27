package com.example.davidutterman.androidmovieapp;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/*
 * Helper class to get settings from asstes config file
 */
public class Config {

    private static final String CONFIG = "config.properties";

    private AssetManager assetManager;

    public Config(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public String getProperty(String key) {
        InputStream inputStream;
        java.util.Properties properties = new java.util.Properties();
        try {
            inputStream = assetManager.open(CONFIG);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
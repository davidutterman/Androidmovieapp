package com.example.davidutterman.androidmovieapp;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

class Props {

    private AssetManager assetManager;

    Props(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    String getProperty(String key) {
        InputStream inputStream;
        java.util.Properties properties = new java.util.Properties();
        try {
            inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
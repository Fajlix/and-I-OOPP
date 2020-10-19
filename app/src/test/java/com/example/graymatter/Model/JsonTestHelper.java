package com.example.graymatter.Model;

import android.app.Application;

import java.io.IOException;
import java.io.InputStream;

public class JsonTestHelper extends Application {
    
    public String getJsonString () {
        try {
            InputStream inputStream = getAssets().open("testplayers.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            return new String(buffer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

package com.example.graymatter.Model.dataAccess.dataMapperImplementation;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalDataMapper {


    private Context context;

    public LocalDataMapper(Context context) {
        this.context = context;
    }

    public int getCurrentPlayerUserID(){ //protected but  testing...
        return Integer.parseInt(readCache());
    }

    public void setCurrentPlayerUserID(int i) {
        writeToCache("" + i);
    }



    private void writeToCache(String str){
        File tempFile = findCache();
        try {
            FileWriter writer = new FileWriter(tempFile);
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File findCache(){
        File cDir = context.getCacheDir();
        File tempFile;
        tempFile = new File(context.getCacheDir(), "/logIn.txt");
        if (tempFile.exists()) {
            return tempFile;
        }
        tempFile = new File(cDir.getPath() + "/logIn.txt") ;
        writeToCache("0");
        return tempFile;
    }

    private String readCache() {
        String strLine ="";
        StringBuilder text = new StringBuilder();
        try {
            FileReader fReader = new FileReader(findCache());
            BufferedReader bReader = new BufferedReader(fReader);

            while( (strLine=bReader.readLine()) != null  ){
                text.append(strLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }


}

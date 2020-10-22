package com.example.graymatter.Model.dataAccess.dataMapperImplementation;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LocalDataMapper {


    private Context context;

    public LocalDataMapper(Context context) {
        this.context = context;
    }

    public int getCurrentPlayerUserID(){ //protected but  testing...
        String str = readCache();
        return Integer.parseInt(str.substring(0, str.indexOf("\n")));
    }

    public void setCurrentPlayerUserID(int i) {
        writeToCache("" + i);
    }



    private void writeToCache(String str){
       /* try (FileOutputStream fos = context.openFileOutput("logIn.txt", Context.MODE_PRIVATE)) {
            fos.write(fileContents.toByteArray());
        }*/

        File tempFile = findCache();
        try {
            FileWriter writer = new FileWriter(tempFile);
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File findCache(){
        File tempFile;
        tempFile = new File(context.getCacheDir(), "logIn.txt");
        if (tempFile.exists()) {
            return tempFile;
        }
        try {
            tempFile = File.createTempFile("logIn.txt", null, context.getCacheDir());
            FileWriter writer = new FileWriter(tempFile);
            writer.write("0");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            bReader.close();
            fReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }


}

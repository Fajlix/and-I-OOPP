package com.example.graymatter.model.dataAccess.dataMapperImplementation;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

public class DataMapperServices {

    private Context context;

    public DataMapperServices(Context context) {
        this.context = context;
    }

    public int getCurrentPlayerUserID(){ //protected but  testing...
        String str = readCache();
        return Integer.parseInt(str.substring(0, str.indexOf("\n")));
    }

    public void setCurrentPlayerUserID(int i) throws IOException {
        writeToCache("" + i);
    }



    private void writeToCache(String str) throws IOException {
       /* try (FileOutputStream fos = context.openFileOutput("logIn.txt", Context.MODE_PRIVATE)) {
            fos.write(fileContents.toByteArray());
        }*/

        File tempFile = findFile();
        try {
            FileWriter writer = new FileWriter(tempFile);
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private File findFile () throws IOException {
        File file = new File(context.getFilesDir(), "logIn.txt");
        if (!file.exists()){
            OutputStream stream = new FileOutputStream(file);
            stream.write("0".getBytes());
            stream.close();
            return file;
        }
        return file;

    }

    private String readCache() {
        String strLine;
        StringBuilder text = new StringBuilder();
        try {
            FileReader fReader = new FileReader(findFile());
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

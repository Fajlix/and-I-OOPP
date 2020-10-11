package com.example.graymatter.Model.dataAccess.dataMapperImplementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalDataMapper {


    private static String path = "src/main/assets/currentPlayer.txt";

    public static int getCurrentPlayerUserID(){ //protected but  testing...
        try {
            return Integer.parseInt(new String(Files.readAllBytes(Paths.get(path))));  //OK for testing, not safe for launch
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

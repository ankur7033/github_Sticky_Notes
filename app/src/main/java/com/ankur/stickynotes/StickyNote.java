package com.ankur.stickynotes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class StickyNote {
    String getStick(Context context){
        File file = new File(context.getFilesDir().getPath()+"/ankur.txt");
        StringBuilder text = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine())!=null){
                text.append(line);
                text.append("\n");
            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return text.toString();
    }
    void setStick(String textToBeSaved,Context context){
        String text = textToBeSaved;
        FileOutputStream fos = null;
        try{
            fos = context.getApplicationContext().openFileOutput("ankur.txt",context.MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (fos!=null){
                try {
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

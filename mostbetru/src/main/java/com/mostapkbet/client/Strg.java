package com.mostapkbet.client;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Strg {
    public static List<String> showFiles(Context context) {
        List<String> fileList = new ArrayList<>();
        File directory = context.getFilesDir();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                fileList.add(file.getName());
            }
        }
        return fileList;
    }
}

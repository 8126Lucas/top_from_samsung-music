package com.lucas8126.top100insm;

import java.io.File;

public class GetFile {
    public static File searchM3UFile(File file_dir) {
        File[] files = file_dir.listFiles();
        if(files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    File recursion_result = searchM3UFile(file);
                    if(recursion_result != null) {
                        return recursion_result;
                    }
                }
                else if(file.getName().equals("MOST_LISTENED.m3u")) {
                    return file;
                }
            }
        }
        return null;
    }
}
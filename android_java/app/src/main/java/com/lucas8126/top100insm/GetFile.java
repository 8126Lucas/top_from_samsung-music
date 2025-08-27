package com.lucas8126.top100insm;

import java.io.File;

public class GetFile {
    public static File searchM3UFile(File file_dir) {
        File[] files = file_dir.listFiles();
        File newest_file = null;
        if(files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    File recursion_result = searchM3UFile(file);
                    if(recursion_result != null) {
                        return recursion_result;
                    }
                }
                else if((newest_file == null || file.lastModified() > newest_file.lastModified())
                        && file.getName().startsWith("MOST_LISTENED")
                        && file.getName().endsWith(".m3u")) {
                    newest_file = file;
                }
            }
        }
        return newest_file;
    }
}
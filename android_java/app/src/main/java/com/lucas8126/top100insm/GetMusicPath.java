package com.lucas8126.top100insm;

import android.content.Context;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class GetMusicPath {
    public static List<String> getMusicPath(Context context, File playlist) {
        List<String> music_paths = new ArrayList<>();
        try(Scanner scanner = new Scanner(playlist)) {
            if(scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if(!line.isEmpty()) {
                    String music_path;
                    if(line.startsWith("/")) {
                        music_path = line;
                    }
                    else {
                        music_path = new File(playlist.getParentFile(), line).getAbsolutePath();
                    }
                    music_paths.add(music_path);
                }
            }
        } catch(FileNotFoundException error) {
            Toast.makeText(context, "❌ M3U file not found: " + error.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
        return music_paths;
    }
}

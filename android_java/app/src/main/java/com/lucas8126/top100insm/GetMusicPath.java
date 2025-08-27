package com.lucas8126.top100insm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class GetMusicPath {
    public static List<String> getMusicPath(File playlist) {
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
            System.out.println("❌ M3U file not found: " + error.getMessage());
            return null;
        }
        return music_paths;
    }
}

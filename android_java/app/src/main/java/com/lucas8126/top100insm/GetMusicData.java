package com.lucas8126.top100insm;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GetMusicData {
    public static final File ROOT_DIR = Environment.getExternalStorageDirectory();
    public static ArrayList<Song> getMusicData(Context context) throws FileNotFoundException {
        ArrayList<Song> songs = new ArrayList<>();
        File playlist = GetFile.searchM3UFile(ROOT_DIR);
        if(playlist == null) {
            NotificationCentral.showNotification(context, "❌ MOST_LISTENED.m3u file not found.");
            return null;
        }
        else {
            NotificationCentral.showNotification(context, "✅ File found: " + playlist.getName());
        }
        List<String> song_paths = GetMusicPath.getMusicPath(playlist);
        int song_count = 0;
        assert song_paths != null;
        for(String paths : song_paths) {
            GetMetadata.extractMetadata(songs, paths);
            song_count++;
            if(song_count % 10 == 0) {
                NotificationCentral.showNotification(context, "🎵 " + song_count + " songs extracted until now.");
            }
        }
        NotificationCentral.showNotification(context, "✅ " + song_count + " songs found.");
        return songs;
    }
}

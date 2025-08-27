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
        List<String> song_paths = GetMusicPath.getMusicPath(context, playlist);
        assert song_paths != null;
        for(String paths : song_paths) {
            GetMetadata.extractMetadata(context, songs, paths);
        }
        return songs;
    }
}

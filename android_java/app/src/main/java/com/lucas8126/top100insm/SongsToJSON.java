package com.lucas8126.top100insm;

import android.content.Context;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SongsToJSON {
    public static File writeJSONFile(ArrayList<Song> songs, Context context) {
        Gson gson = new Gson();
        File json_file = new File(context.getFilesDir(), "MOST_LISTENED.json");
        try (FileWriter writer = new FileWriter(json_file)) {
            gson.toJson(songs, writer);
        } catch (IOException error) {
            System.out.println("❌ JSON writing failed: " + error.getMessage());
        }
        return json_file;
    }
}

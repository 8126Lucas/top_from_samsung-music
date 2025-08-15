package com.lucas8126.top100insm;

import android.content.Context;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetMetadata {
    public static void extractMetadata(Context context, ArrayList<Song> songs, String music_location) {
        File music_file = new File(music_location);
        if (!music_file.exists() || !music_file.isFile()) {
            Toast.makeText(context, "❌ Metadata extraction: File does not exist or is not a file: " + music_location, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!music_file.canRead()) {
            Toast.makeText(context, "❌ Metadata extraction: Cannot read file: " + music_location, Toast.LENGTH_SHORT).show();
            return;
        }
        try(InputStream is = new FileInputStream(music_file)) {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parse_context = new ParseContext();
            parser.parse(is, handler, metadata, parse_context);

            for (String name : metadata.names()) {
                System.out.println(name + ": " + metadata.get(name));
            }
            int song_id = songs.size() + 1;
            Song music = new Song(song_id, metadata.get("dc:title"), metadata.get("xmpDM:artist"), metadata.get("xmpDM:album"));
            if(music.getTitle() == null) {music.setTitle("Unknown");}
            if(music.getArtist() == null) {music.setArtist("Unknown");}
            if(music.getAlbum() == null) {music.setAlbum("Unknown");}
            songs.add(music);
        } catch (TikaException | IOException | SAXException error) {
            Toast.makeText(context, "❌ Metadata extraction failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

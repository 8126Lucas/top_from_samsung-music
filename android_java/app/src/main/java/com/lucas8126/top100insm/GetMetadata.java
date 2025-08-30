package com.lucas8126.top100insm;

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
    public static String youtubeURL(String title, String artist) {
        title = title.replace(" ", "+");
        artist = artist.replace(" ", "+");
        return "https://www.youtube.com/search?q=" + title + "+" + artist;
    }

    public static void extractMetadata(ArrayList<Song> songs, String music_location) {
        File music_file = new File(music_location);
        if (!music_file.exists() || !music_file.isFile()) {
            System.out.println("❌ Metadata extraction: File does not exist or is not a file: " + music_location);
            return;
        }
        if (!music_file.canRead()) {
            System.out.println("❌ Metadata extraction: Cannot read file: " + music_location);
            return;
        }
        try(InputStream is = new FileInputStream(music_file)) {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parse_context = new ParseContext();
            parser.parse(is, handler, metadata, parse_context);
            int song_id = songs.size() + 1;
            String youtube = youtubeURL(metadata.get("dc:title"), metadata.get("xmpDM:artist"));
            Song music = new Song(song_id, metadata.get("dc:title"),
                                    metadata.get("xmpDM:artist"), metadata.get("xmpDM:album"),
                                    metadata.get("xmpDM:duration"), youtube);
            if(music.getTitle() == null) {music.setTitle("Unknown");}
            if(music.getArtist() == null) {music.setArtist("Unknown");}
            if(music.getAlbum() == null) {music.setAlbum("Unknown");}
            if(music.getDuration() == null) {music.setDuration("Unknown");}
            if(music.getYoutube() == null) {music.setYoutube("Unknown");}
            songs.add(music);
        } catch (TikaException | IOException | SAXException error) {
            System.out.println("❌ Metadata extraction failed: " + error.getMessage());
        }
    }
}

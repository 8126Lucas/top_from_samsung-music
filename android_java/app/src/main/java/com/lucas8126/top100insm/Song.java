package com.lucas8126.top100insm;

public class Song {
    private int position;
    private String title;
    private String artist;
    private String album;
    private String duration;
    private String youtube;

    public Song(int position, String title, String artist, String album, String duration, String youtube) {
        this.position = position;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.youtube = youtube;
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {return duration;}

    public String getYoutube() {return youtube;}

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setDuration(String duration) {this.duration = duration;}

    public void setYoutube(String youtube) {this.youtube = youtube;}
}

package com.lucas8126.top100insm;

public class Song {
    private int position;
    private String title;
    private String artist;
    private String album;

    public Song(int position, String title, String artist, String album) {
        this.position = position;
        this.title = title;
        this.artist = artist;
        this.album = album;
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
}

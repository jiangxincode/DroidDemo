package edu.jiangxin.droiddemo.mediastore;

import android.graphics.drawable.BitmapDrawable;

public class MusicInfo {
private int id;
private String name;
private String artist;
private String album;
private String src;
private int duration;
private int size;
private int album_id;
private BitmapDrawable album_cover;






public MusicInfo(int id, String name, String artist, String album, String src,
		int duration, int size, int album_id, BitmapDrawable album_cover) {
	super();
	this.id = id;
	this.name = name;
	this.artist = artist;
	this.album = album;
	this.src = src;
	this.duration = duration;
	this.size = size;
	this.album_id = album_id;
	this.album_cover = album_cover;
}


public MusicInfo() {
	// TODO Auto-generated constructor stub
}


public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getArtist() {
	return artist;
}
public void setArtist(String artist) {
	this.artist = artist;
}
public String getAlbum() {
	return album;
}
public void setAlbum(String album) {
	this.album = album;
}
public String getSrc() {
	return src;
}
public void setSrc(String src) {
	this.src = src;
}
public int getDuration() {
	return duration;
}
public void setDuration(int duration) {
	this.duration = duration;
}
public int getSize() {
	return size;
}
public void setSize(int size) {
	this.size = size;
}


public BitmapDrawable getAlbum_cover() {
	return album_cover;
}


public void setAlbum_cover(BitmapDrawable album_cover) {
	this.album_cover = album_cover;
}

}


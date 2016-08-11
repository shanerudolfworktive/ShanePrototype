package com.prototype.shane.shaneprototype.controller.musicSearch;


public class MusicModel {
	private int id;
	private String musicTitle;
	private String musicArtist;
	private String musicAlbum;
	
	public MusicModel(){}
	
	public MusicModel(String musicTitle, String musicArtist, String musicAlbum) {
		this.musicTitle = musicTitle;
		this.musicArtist = musicArtist;
		this.musicAlbum = musicAlbum;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMusicTitle() {
		return musicTitle;
	}
	public void setMusicTitle(String musicTitle) {
		this.musicTitle = musicTitle;
	}
	public String getMusicArtist() {
		return musicArtist;
	}
	public void setMusicArtist(String musicArtist) {
		this.musicArtist = musicArtist;
	}
	public String getMusicAlbum() {
		return musicAlbum;
	}
	public void setMusicAlbum(String musicAlbum) {
		this.musicAlbum = musicAlbum;
	}
}

package edu.jiangxin.droiddemo.mediastore;

public class MovieInfo {
	private String title;
	private String url;
	private String cover_url;
	private int duration;
	
	
	
	
	public MovieInfo(String title, String url, String cover_url, int duration) {
		super();
		this.title = title;
		this.url = url;
		this.cover_url = cover_url;
		this.duration = duration;
	}
	
	
	public MovieInfo() {

	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCover_url() {
		return cover_url;
	}
	public void setCover_url(String cover_url) {
		this.cover_url = cover_url;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	

}

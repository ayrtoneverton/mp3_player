package com.player.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe para representar os arquivos de música
 */
@XmlRootElement
public class Music extends Persist implements Playable {

	private String name;
	private String url;

	/**
	 * CONSTRUTOR
	 */
	public Music(){}

	/**
	 * CONSTRUTOR
	 * @param id
	 * @param nome
	 * @param url
	 */
	public Music(int id, String name, String url){
		this.id = id;
		this.name = name;
		this.url = url;
	}

	/**
	 * CONSTRUTOR
	 * @param nome
	 * @param url
	 */
	public Music(String name, String url){
		this.name = name;
		this.url = url;
	}
	
	@Override
	public PlayList getPlayList() {
		return new PlayList(this);
	}

	/* GETs and SETs */

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}

	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url = url;
	}
}
package com.player.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Classe para representar os arquivos de música
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Music extends Persist implements Playable {
	//@ spec_public nullable
	private String name;
	//@ spec_public nullable
	private String url;

	/**
	 * CONSTRUTOR
	 */
	//@ pure
	public Music(){}

	/**
	 * CONSTRUTOR
	 * @param id
	 * @param nome
	 * @param url
	 */
	//@ requires !com.player.Util.isExistEmpty(id, name, url);
	public Music(int id, String name, String url){
		this.id = id;
		this.name = name;
		this.url = url;
	}

	//@ protected represents playList <- new PlayList((Music) this);
	@XmlTransient
	@Override
	public PlayList getPlayList() {
		return new PlayList(this);
	}

	/* GETs and SETs */

	//@ ensures \result == name;
	//@ pure nullable
	public String getName(){
		return name;
	}

	//@ requires !com.player.Util.isExistEmpty(name);
	//@ assignable this.name;
	//@ ensures this.name == name;
	public void setName(String name){
		this.name = name;
	}

	//@ ensures \result == url;
	//@ pure nullable
	public String getUrl(){
		return url;
	}

	//@ requires !com.player.Util.isExistEmpty(url);
	//@ assignable this.url;
	//@ ensures this.url == url;
	public void setUrl(String url){
		this.url = url;
	}
}
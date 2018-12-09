package com.player.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Classe para representar a playlist de música
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PlayList extends Persist implements Playable {
	//@ spec_public nullable
	private String name;
	//@ spec_public non_null
	private ArrayList<Music> musics;
	//@ spec_public nullable
	private User user;

	/**
	 * CONSTRUTOR
	 */
	//@ assignable this.musics;
	//@ ensures this.musics != null && this.musics.size() == 0;
	public PlayList(){
		musics = new ArrayList<Music>();
	}

	/**
	 * CONSTRUTOR
	 * @param music
	 */
	//@ requires music != null;
	//@ assignable this.musics;
	//@ ensures this.musics.size() == 1 && this.musics.get(0) == music;
	public PlayList(Music music){
		this();
		musics.add(music);
	}

	//@ protected represents playList <- this;
	@XmlTransient
	@Override
	public PlayList getPlayList() {
		return this;
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

	//@ ensures \result == musics;
	//@ pure
	public ArrayList<Music> getMusics(){
		return musics;
	}

	//@ requires musics != null;
	//@ assignable this.musics;
	//@ ensures this.musics == musics;
	public void setMusics(ArrayList<Music> musics){
		this.musics = musics;
	}

	//@ ensures \result == user;
	//@ pure nullable
	public User getUser(){
		return user;
	}

	//@ requires user != null;
	//@ assignable this.user;
	//@ ensures this.user == user;
	public void setUser(User user){
		this.user = user;
	}
}
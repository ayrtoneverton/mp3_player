package com.player.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe para representar a playlist de música
 */
@XmlRootElement
public class PlayList extends Persist implements Playable {

	private String name;
	private ArrayList<Music> musics;
	private User user;

	/**
	 * CONSTRUTOR
	 */
	public PlayList(){}

	/**
	 * CONSTRUTOR
	 * @param music
	 */
	public PlayList(Music music){
		musics = new ArrayList<Music>();
		musics.add(music);
	}

	/**
	 * CONSTRUTOR
	 * @param id
	 * @param name
	 * @param musics
	 * @param user
	 */
	public PlayList(int id, String name, ArrayList<Music> musics, User user){
		this.id = id;
		this.name = name;
		this.musics = musics;
		this.user = user;
	}
	
	@Override
	public PlayList getPlayList() {
		return this;
	}

	/* GETs and SETs */

	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public ArrayList<Music> getMusics(){
		return musics;
	}
	
	public void setMusics(ArrayList<Music> musics){
		this.musics = musics;
	}

	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
}
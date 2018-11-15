package com.player.model;

/**
 * Classe abstrata para entidades que serão persistidas nos arquivos
 */
public abstract class Persist {

	protected int id;

	/* GETs and SETs */

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}
}
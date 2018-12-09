package com.player.model;

/**
 * Classe abstrata para entidades que serão persistidas nos arquivos
 */
public abstract class Persist {
	//@ public invariant id >= 0;
	//@ spec_public
	protected int id;

	/* GETs and SETs */

	//@ ensures \result == id;
	//@ pure
	public int getId(){
		return id;
	}

	//@ requires this.id == 0 && id > 0;
	//@ assignable this.id;
	//@ ensures this.id == id;
	public void setId(int id){
		this.id = id;
	}
}
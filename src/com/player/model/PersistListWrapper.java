package com.player.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;

/**
 * Classe template para persistencia de todas as entidades nos XMLs
 */
public class PersistListWrapper<T extends Persist> {
	//@ spec_public non_null
	private List<T> persists;

	//@ assignable this.persists;
	//@ ensures this.persists != null && this.persists.size() == 0;
	public PersistListWrapper(){
		persists = new ArrayList<T>();
	}

	//@ assignable this.persists;
	//@ ensures this.persists == persists;
	public PersistListWrapper(List<T> persists){
		this.persists = persists;
	}

	//@ ensures \result == persists;
	//@ pure
	@XmlAnyElement(lax=true)
	public List<T> getPersists() {
		return persists;
	}

	//@ requires persists != null;
	//@ assignable this.persists;
	//@ ensures this.persists == persists;
	public void setPersists(List<T> persists) {
		this.persists = persists;
	}
}

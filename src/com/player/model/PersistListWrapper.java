package com.player.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;

/**
 * Classe template para persistencia de todas as entidades nos XMLs
 */
public class PersistListWrapper<T extends Persist> {

	private List<T> persists;
	
	public PersistListWrapper(){
		persists = new ArrayList<T>();
	}
	
	public PersistListWrapper(List<T> persists){
		this.persists = persists;
	}

	@XmlAnyElement(lax=true)
	public List<T> getPersists() {
		return persists;
	}

	public void setPersists(List<T> persists) {
		this.persists = persists;
	}
}

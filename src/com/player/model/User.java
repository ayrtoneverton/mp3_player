package com.player.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe para representar os usuários do Player
 */
@XmlRootElement
public class User extends Persist {
	//@ spec_public nullable
	private String name;
	//@ spec_public nullable
	private String login;
	//@ spec_public nullable
	private String pass;
	//@ spec_public non_null
	private TypeUser type;
	//@ spec_public
	private boolean rememberPass;
	
	/**
	 * CONSTRUTOR
	 */
	//@ assignable type;
	//@ ensures type == TypeUser.COMUM;
	public User() {
		this.type = TypeUser.COMUM;
	}
	
	/**
	 * CONSTRUTOR
	 * @param id
	 * @param name
	 * @param login
	 * @param pass
	 * @param type
	 */
	//@ requires !com.player.Util.isExistEmpty(id, name, login, pass);
	public User(int id, String name, String login, String pass, TypeUser type) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.pass = pass;
		this.type = type;
	}

	/* GETs and SETs */

	//@ ensures \result == name;
	//@ pure nullable
	public String getName() {
		return name;
	}

	//@ requires !com.player.Util.isExistEmpty(name);
	//@ assignable this.name;
	//@ ensures this.name == name;
	public void setName(String name) {
		this.name = name;
	}

	//@ ensures \result == login;
	//@ pure nullable
	public String getLogin() {
		return login;
	}

	//@ requires !com.player.Util.isExistEmpty(login);
	//@ assignable this.login;
	//@ ensures this.login == login;
	public void setLogin(String login) {
		this.login = login;
	}

	//@ ensures \result == pass;
	//@ pure nullable
	public String getPass() {
		return pass;
	}

	//@ requires !com.player.Util.isExistEmpty(pass);
	//@ assignable this.pass;
	//@ ensures this.pass == pass;
	public void setPass(String pass) {
		this.pass = pass;
	}

	//@ ensures \result == type;
	//@ pure
	public TypeUser getType() {
		return type;
	}

	//@ assignable this.type;
	//@ ensures this.type == type;
	public void setType(TypeUser type) {
		this.type = type;
	}

	//@ ensures \result == rememberPass;
	//@ pure
	public boolean isRememberPass() {
		return rememberPass;
	}

	//@ assignable this.rememberPass;
	//@ ensures this.rememberPass == rememberPass;
	public void setRememberPass(boolean rememberPass) {
		this.rememberPass = rememberPass;
	}

	//@ pure
	public boolean isRoot() {
		return type == TypeUser.ROOT;
	}

	//@ pure
	public boolean isVip() {
		return type == TypeUser.VIP || type == TypeUser.ROOT;
	}

	//@ pure
	public boolean isComum() {
		return type == TypeUser.COMUM;
	}
}
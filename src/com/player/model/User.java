package com.player.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe para representar os usuários do Player
 */
@XmlRootElement
public class User extends Persist {

	private String name;
	private String login;
	private String pass;
	private TypeUser type;
	private boolean rememberPass;
	
	/**
	 * CONSTRUTOR
	 */
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
	public User(int id, String name, String login, String pass, TypeUser type) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.pass = pass;
		this.type = type;
	}

	/* GETs and SETs */
	
	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String senha) {
		this.pass = senha;
	}

	public TypeUser getType() {
		return type;
	}

	public void setType(TypeUser type) {
		this.type = type;
	}

	public boolean isRememberPass() {
		return rememberPass;
	}

	public void setRememberPass(boolean lembrarSenha) {
		this.rememberPass = lembrarSenha;
	}

	public boolean isRoot() {
		return type == TypeUser.ROOT;
	}

	public boolean isVip() {
		return type == TypeUser.VIP || type == TypeUser.ROOT;
	}

	public boolean isComum() {
		return type == TypeUser.COMUM;
	}
}
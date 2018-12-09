package com.player.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import com.player.Util;
import com.player.model.AbstractController;
import com.player.model.User;

/**
 * Dialog to login.
 */
public class LoginDialogController extends AbstractController {
	//@ nullable
    @FXML
    private TextField loginField;
	//@ nullable
    @FXML
    private TextField senhaField;
	//@ nullable
    @FXML
    private CheckBox lembrarField;

	//@ spec_public nullable
    private User user;
	//@ spec_public nullable
    private ObservableList<User> users;

    /**
     * Ao clicar em Logar
     */
	//@ pure
    @FXML
    private void logar() {
    	if (loginField.getText() == null || loginField.getText().isEmpty()) {
        	Util.showError("Login não informado!");
            return;
        }
        if (senhaField.getText() == null || senhaField.getText().isEmpty()) {
        	Util.showError("Senha não informada!");
            return;
        }

        for(User u: users) {
        	if(u.getLogin().equals(loginField.getText()) && u.getPass().equals(Util.toMD5(senhaField.getText()))) {
        		u.setRememberPass(lembrarField.isSelected());
        		user = u;
        		stage.close();
        		return;
        	}
        }
        Util.showError("login e/ou senha incorreto(s)!");
    }

	/* GETs and SETs */

	//@ ensures \result == user;
	//@ pure nullable
	public User getUser() {
		return user;
	}

	//@ requires users != null;
	//@ assignable this.users;
	//@ ensures this.users == users;
    public void setUsers(ObservableList<User> users) {
		this.users = users;
	}

    @Override
    protected void initStage() {
    	super.initStage();
    	stage.setTitle("Realizar Login no MP3 Player");
    }
}
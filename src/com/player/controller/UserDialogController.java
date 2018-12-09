package com.player.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import com.player.Util;
import com.player.model.AbstractController;
import com.player.model.TypeUser;
import com.player.model.User;

/**
 * Dialog to edit details of a user.
 */
public class UserDialogController extends AbstractController {
	//@ nullable
    @FXML
    private TextField nameField;
	//@ nullable
    @FXML
    private TextField loginField;
	//@ nullable
    @FXML
    private TextField senhaField;
	//@ nullable
    @FXML
    private ComboBox<TypeUser> typeCombo;

	//@ spec_public nullable
    private User user;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    //@ pure
    @FXML
    private void initialize() {
    	typeCombo.setItems(FXCollections.observableArrayList(TypeUser.values()));
    }

    /**
     * Sets the user to be edited in the dialog.
     * @param user
     */
	//@ requires user != null;
	//@ assignable this.user;
	//@ ensures this.user == user;
    public void setUser(User user) {
        this.user = user;
        if(user.getId() > 0)
        	stage.setTitle("Edit User");

        nameField.setText(user.getName());
        loginField.setText(user.getLogin());
        typeCombo.getSelectionModel().select(user.getType());
    }

    /**
     * Called when the user clicks ok.
     */
	//@ assignable this.ok;
    @FXML
    private void onOk() {
        if (isInputsValid()) {
            user.setName(nameField.getText());
            user.setLogin(loginField.getText());
            if(user.getPass() == null || (senhaField.getText() != null && senhaField.getText().length() > 0))
            	user.setPass(Util.toMD5(senhaField.getText()));
            user.setType(typeCombo.getValue());

            ok = true;
            stage.close();
        }
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    //@ pure
    private boolean isInputsValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Nome não informado!\n"; 
        }
        if (loginField.getText() == null || loginField.getText().length() == 0) {
            errorMessage += "Login não informado!\n"; 
        }
        if (user.getPass() == null && (senhaField.getText() == null || senhaField.getText().length() == 0)) {
            errorMessage += "Senha não informada!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
        	Util.showError(errorMessage);
            return false;
        }
    }

    @Override
	protected void initStage() {
    	super.initStage();
		stage.setTitle("Create User");
	}
}
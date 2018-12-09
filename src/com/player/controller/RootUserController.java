package com.player.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.player.Util;
import com.player.model.AbstractController;
import com.player.model.User;

public class RootUserController extends AbstractController {
	//@ nullable
    @FXML
    private TableView<User> userTable;
	//@ nullable
    @FXML
    private TableColumn<User, String> nameColumn;
	//@ nullable
    @FXML
    private TableColumn<User, String> typeColumn;

	//@ spec_public nullable
    private ObservableList<User> users;

    /**
     * Responsavel por inicializar componentes e eventos da view
     */
	//@ pure
    @FXML
    private void initialize() {
    	// Initializar tabelas
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    }

    /**
     * Responsavel por inicializar estrutura e dados do RootUser
     * 
     * @param users
     */
	//@ requires users != null;
	//@ assignable this.users;
	//@ ensures this.users == users;
    public void setUsers(ObservableList<User> users) {
        this.users = users;

        userTable.setItems(users);
        if(userTable.getSelectionModel() != null)
        	userTable.getSelectionModel().selectFirst();
    }

	//@ pure
	@FXML
	private void deleteUser() {
		if(userTable.getSelectionModel() != null) {
			int i = userTable.getSelectionModel().getSelectedIndex();
			if(i >= 0)
				userTable.getItems().remove(i);
		}
	}

	//@ pure
	@FXML
	private void newUser() {
		User tempUser = new User();
		if (showUserDialog(tempUser)) {
			tempUser.setId(Util.getNextId(users));
			users.add(tempUser);
			userTable.getSelectionModel().selectLast();
		}
	}

	//@ pure
	@FXML
	private void editUser() {
		if(userTable.getSelectionModel() != null) {
			showUserDialog(userTable.getSelectionModel().getSelectedItem());
			userTable.refresh();
		}
	}

	//@ pure
	private boolean showUserDialog(User user){
		try {
			UserDialogController controller = getInstance(UserDialogController.class, stage);
			controller.setUser(user);

			// Show the dialog and wait until the user closes it
			controller.getStage().showAndWait();

			return controller.isOk();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected void initStage() {
		super.initStage();
		stage.setTitle("Gerenciar usuários");
	}
}
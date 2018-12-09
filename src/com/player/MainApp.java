package com.player;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.player.controller.LoginDialogController;
import com.player.controller.PlayerController;
import com.player.model.Music;
import com.player.model.PlayList;
import com.player.model.TypeUser;
import com.player.model.User;

/**
 * Classe inicial da aplicacao
 */
public class MainApp extends Application {
	//@ spec_public nullable
	private Stage primaryStage;

	/**
	 * Lista de todos os usuarios
	 */
	//@ spec_public non_null
	private final ObservableList<User> users = FXCollections.observableArrayList();

	/**
	 * Lista de todos os musicas
	 */
	//@ spec_public non_null
	private final ObservableList<Music> musics = FXCollections.observableArrayList();

	/**
	 * Lista de todos os musicas
	 */
	//@ spec_public non_null
	private final ObservableList<PlayList> playLists = FXCollections.observableArrayList();

	/**
	 * Usuario logado
	 */
	//@ spec_public nullable
	private User user;

	//@ assignable primaryStage, user;
	//@ ensures this.primaryStage == primaryStage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		// Carregando os usuarios
		users.addAll( DAO.findAll(User.class) );
		
		// Add usuario padrão
		if(users.size() == 0)
			users.add(new User(1, "Admin", "admin", Util.toMD5("admin"), TypeUser.ROOT));

		// Verifica e realiza login
		user = DAO.findActiveUser(users);
		if(user == null)
			user = showLoginDialog();

		if(user != null) {
			// Carregando o restante os dados
			musics.addAll( DAO.findAll(Music.class) );
			for(PlayList p: DAO.findAll(PlayList.class))
				if(p.getUser().getId() == user.getId())
					playLists.add(p);
			
			showPlayer();
		}
	}

	//@ pure
	@Override
	public void stop() throws Exception {
		if(user != null) {
			// salvando todos os dados
			DAO.saveAll(users);
			DAO.saveAll(musics);
			for(PlayList p: DAO.findAll(PlayList.class))
				if(p.getUser().getId() != user.getId())
					playLists.add(p);
			DAO.saveAll(playLists);
			DAO.saveActiveUser(user.isRememberPass() ? user : null);
		}
		super.stop();
	}

	/**
	 * Cria o dialog para realizacao do login
	 * 
	 * @return User
	 * @throws IOException 
	 */
	//@ pure nullable
	private User showLoginDialog() throws IOException {
		LoginDialogController controller = LoginDialogController.getInstance(LoginDialogController.class, primaryStage);
		controller.setUsers(users);
		controller.getStage().showAndWait();
		return controller.getUser();
	}

	/**
	 * Iniciar tela principal do player
	 * 
	 * @throws IOException 
	 */
	//@ requires user != null;
	//@ pure
	private void showPlayer() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/Player.fxml"));
		AnchorPane player = (AnchorPane) loader.load();
		primaryStage.setScene(new Scene(player));

		PlayerController controller = loader.getController();
		controller.setStage(primaryStage);
		controller.initStage();
		controller.initPlayer(this);

		primaryStage.show();
	}

	/**
	 * Sair do App e fechar
	 */
	//@ pure
	public void exit(){
		user.setRememberPass(false);
		primaryStage.close();
	}

	/* GETs and SETs and MAIN */

	//@ ensures \result == primaryStage;
	//@ pure
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	//@ ensures \result == users;
	//@ pure
	public ObservableList<User> getUsers() {
		return users;
	}

	//@ ensures \result == user;
	//@ pure
	public User getUser() {
		return user;
	}

	//@ ensures \result == musics;
	//@ pure
	public ObservableList<Music> getMusics() {
		return musics;
	}

	//@ ensures \result == playLists;
	//@ pure
	public ObservableList<PlayList> getPlayLists() {
		return playLists;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
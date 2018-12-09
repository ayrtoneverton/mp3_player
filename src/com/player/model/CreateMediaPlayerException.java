package com.player.model;

import com.player.Util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Classe de exceção na criacao de um MediaPlayer
 */
public class CreateMediaPlayerException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Indica se a aplicacao deve continuar tentando para as proximas musicas
	 */
	//@ spec_public
	private boolean testNext;

	/**
	 * CONSTRUTOR
	 */
	//@ assignable this.testNext;
	public CreateMediaPlayerException(String url) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(Util.getImage("icon.png"));
		alert.setTitle("Erro");
		alert.setHeaderText(null);
		alert.setContentText("Não foi possível abrir o arquivo: \n" + url + "\n\nDeseja tentar abrir o arquivo da sequência?");
		
		ButtonType next = new ButtonType("Tentar", ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(next, new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE));
        
        testNext = next == alert.showAndWait().get();
	}

	//@ ensures \result == testNext;
	//@ pure
	public boolean isTestNext() {
		return testNext;
	}
}

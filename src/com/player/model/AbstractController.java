package com.player.model;

import java.io.IOException;

import com.player.MainApp;
import com.player.Util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class AbstractController {
	//@ spec_public nullable
	protected Stage stage;
	//@ spec_public
	protected boolean ok;

	/**
	 * Create instance of view controller C, owner Stage is stageBack. 
	 * 
	 * @param C
	 * @param stageBack
	 * @return
	 * @throws IOException
	 */
	//@ pure
	public static <T extends AbstractController> T getInstance(Class<? extends AbstractController> C, Stage stageBack) throws IOException {
		// Load the fxml file and create a new stage.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/"+ C.getSimpleName().replace("Controller", "") +".fxml"));
		AnchorPane page = (AnchorPane) loader.load();

		// Create Stage.
		Stage stage = new Stage();
		stage.setScene(new Scene(page));
		stage.initOwner(stageBack);

		// Set the stage into the controller.
		T controller = loader.getController();
		controller.setStage(stage);
		controller.initStage();
		return controller;
	}

	/**
	 * Init stage(view).
	 */
	//@ pure
	protected void initStage() {
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setResizable(false);
		stage.getIcons().add(Util.getImage("user.png"));
	}

    /**
     * Called when the user clicks cancel.
     */
	//@ pure
    @FXML
    private void onCancel() {
        stage.close();
    }

    /* GETs and SETs */

	//@ ensures \result == stage;
	//@ pure nullable
	public Stage getStage() {
		return stage;
	}

	//@ requires stage != null;
	//@ assignable this.stage;
	//@ ensures this.stage == stage;
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	//@ ensures \result == this.ok;
	//@ pure
	public boolean isOk() {
		return ok;
	}
}

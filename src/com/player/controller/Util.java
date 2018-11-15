package com.player.controller;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import com.player.model.Persist;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
  
public class Util {

	public static String toMD5(Object o){
		try {
			 return URLEncoder.encode(new String(MessageDigest.getInstance("MD5").digest(String.valueOf(o).getBytes())), "UTF-8");
		} catch (Exception e) {
			 return null;
		}
	}

	public static void showInfo(String text){
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(getImage("icon.png"));
		alert.setTitle("Informação");
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.show();
	}

	public static void showError(String text){
		Alert alert = new Alert(AlertType.ERROR);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(getImage("icon.png"));
		alert.setTitle("Erro");
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.show();
	}

	public static String getTextDialog(String title, String input){
		TextInputDialog dialog = new TextInputDialog();
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(getImage("icon.png"));
		dialog.setTitle(title);
		dialog.setHeaderText(null);
		dialog.setContentText(input);
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && result.get().length() > 0)
			return result.get();

		return null;
	}

	public static Image getImage(String imgName){
		try {
			return new Image(Util.class.getResourceAsStream("/resources/images/"+imgName));
		}catch (Exception e) {
			return new Image("file:resources/images/"+imgName);
		}
	}

	public static int getNextId(List<? extends Persist> list) {
	    return list == null || list.size() == 0 ? 1 : list.get(list.size()-1).getId()+1;
	}

	public static String formatDuration(Duration duration) {
	    return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date((long) duration.toMillis() - TimeZone.getDefault().getRawOffset()));
	}
}
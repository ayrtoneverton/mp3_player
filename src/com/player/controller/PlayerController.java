package com.player.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;

import com.player.MainApp;
import com.player.model.AbstractController;
import com.player.model.CreateMediaPlayerException;
import com.player.model.Music;
import com.player.model.PlayList;
import com.player.model.Playable;

/**
 * Classe de controle visual principal, reponsavel por gerenciar e reproduzir musicas e plylists.
 */
public class PlayerController extends AbstractController {
    @FXML
    private TableView<Music> musicTable;
    @FXML
    private TableColumn<Music, String> musicColumn;
    @FXML
    private TableView<PlayList> playListTable;
    @FXML
    private TableColumn<PlayList, String> playListColumn;
    @FXML
    private TableView<Music> playTable;
    @FXML
    private TableColumn<Music, String> playColumn;
    @FXML
    private Slider progress;

    /**
     * Reference to the main application
     */
    private MainApp mainApp;

    /**
     * Player reprodutor de MP3
     */
    private MediaPlayer player;

    /**
     * Responsavel por inicializar componentes e eventos da view
     */
    @FXML
    private void initialize() {
    	// Initializar tabelas
    	musicColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    	playListColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    	playColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

    	// criando eventos doubleclick
    	musicTable.setRowFactory(getRowFactoryDoubleClick());
    	//playListTable.setRowFactory(getRowFactoryDoubleClick());
    	
        // Evento ao selecionar musica em tabela de executando
        playTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> { if(oldValue != null && newValue != null) selectPlay(); });
    	
        // Evento ao selecionar playList
        playListTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> { if(newValue != null) play(newValue); });

        // criando evento para barra de progresso
        progress.setMaxWidth(Double.MAX_VALUE);
        progress.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if((progress.isValueChanging() || progress.isPressed()) && player.getTotalDuration() != null) {
                    player.seek(player.getTotalDuration().multiply(progress.getValue() / 100.0));
                    updateSlider();
               }
            }
        });
    }

    /**
     * Responsavel por inicializar estrutura e dados do player
     * 
     * @param mainApp
     */
    public void initPlayer(MainApp mainApp) {
        this.mainApp = mainApp;

    	if(mainApp.getUser().isRoot()) {
        	Button gr = (Button) mainApp.getPrimaryStage().getScene().lookup("#gerirUsers");
        	gr.setVisible(true);
        	gr.setTooltip(new Tooltip("Gerenciar usúarios"));
    	}

    	if(mainApp.getUser().isVip()) {
        	playListTable.setItems(mainApp.getPlayLists());

        	playListTable.setDisable(false);
        	mainApp.getPrimaryStage().getScene().lookup("#vip").setVisible(true);
    	}else {
    		mainApp.getPrimaryStage().getScene().lookup("#createPlayList").setDisable(true);
        	playListTable.setTooltip(new Tooltip("Somente VIPs podem ter PlayLists"));
    	}

    	musicTable.setItems(mainApp.getMusics());
    	musicTable.getSelectionModel().selectFirst();
    	playTable.setItems(FXCollections.observableArrayList());
    	playTable.getItems().addAll(mainApp.getMusics());
    	playTable.getSelectionModel().selectFirst();

        ((Label) mainApp.getPrimaryStage().getScene().lookup("#nomeLabel")).setText("Olá, " + mainApp.getUser().getName());
        ((Button) mainApp.getPrimaryStage().getScene().lookup("#logout")).setTooltip(new Tooltip("Sair e fechar"));
    }

    /**
     * Responsavel por atalizar a lista de reproducao atual
     */
    private void play(Playable playable) {
    	PlayList playList = playable.getPlayList();
    	if(playList.getId() == 0)
    		playListTable.getSelectionModel().clearSelection();
    	playTable.getItems().clear();
    	playTable.getSelectionModel().clearSelection();
    	playTable.getItems().addAll(playList.getMusics());
    	playTable.getSelectionModel().selectFirst();
    	selectPlay();
    }

    /**
     * Responsavel por reproduzir a partir do ponto selecionado da lista
     */
    private void selectPlay() {
    	stop();
    	playPause();
    }

    /**
     * Responsavel por iniciar e pausar a reproducao da musica
     */
    @FXML
    private void playPause() {
    	if(playTable.getItems().isEmpty())
    		return;

    	if(player == null) {
    		try {
    			createMediaPlayer();
        	}catch (CreateMediaPlayerException e) {
        		Node n = mainApp.getPrimaryStage().getScene().lookup("#pause");
    			if(e.isTestNext())
    				next();
    			else if(n != null)
        			n.setId("play");
    			return;
    		}
    	}

    	if(player.getStatus() == MediaPlayer.Status.PLAYING) {
    		mainApp.getPrimaryStage().getScene().lookup("#pause").setId("play");
    		player.pause();
    	}else {
    		Node n = mainApp.getPrimaryStage().getScene().lookup("#play");
    		if(n != null)
    			n.setId("pause");
    		player.play();
    	}
    }

    /**
     * Responsavel por parar a reproducao da musica
     */
    @FXML
    private void stop() {
    	if(player != null) {
    		player.stop();
    		player = null;
    	}
    }

    /**
     * Responsavel por pular para proxima musica
     */
    @FXML
    private void previous() {
    	playTable.getSelectionModel().selectPrevious();
    }

    /**
     * Responsavel por voltar para musica anterior
     */
    @FXML
    private void next() {
    	playTable.getSelectionModel().selectNext();
    }

    /**
     * Criar novo player
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void createMediaPlayer() throws CreateMediaPlayerException {
    	try {
    		player = new MediaPlayer(new Media(new File(playTable.getSelectionModel().getSelectedItem().getUrl()).toURI().toString()));
    	}catch (Exception e) {
			throw new CreateMediaPlayerException(playTable.getSelectionModel().getSelectedItem().getUrl());
		}
    	player.currentTimeProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				updateSlider();
			}
		});
    }

    /**
     * Atuaizar barra de progresso
     */
    private void updateSlider() {
        if (player != null && player.getTotalDuration() != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                	progress.setDisable(player.getTotalDuration().isUnknown());
                    if(!progress.isDisabled() && player.getTotalDuration().greaterThan(Duration.ZERO) && !progress.isValueChanging() && !progress.isPressed()) {
                    	progress.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100.0);
                    }
                    ((Label) mainApp.getPrimaryStage().getScene().lookup("#time")).setText(Util.formatDuration(player.getCurrentTime()));
                    ((Label) mainApp.getPrimaryStage().getScene().lookup("#duration")).setText(Util.formatDuration(player.getTotalDuration()));
                    
                    if(player.getCurrentTime().toMillis()+20 > player.getTotalDuration().toMillis())
                    	next();
                }
            });
        }
    }

    @FXML
    private void createPlayList() {
    	if(!musicTable.getItems().isEmpty()) {
    		String nome = Util.getTextDialog("Criando PlayList", "Nome:");
    		if(nome == null)
    			return;
    		PlayList p = musicTable.getSelectionModel().getSelectedItem().getPlayList();
    		p.setId(Util.getNextId(playListTable.getItems()));
    		p.setName(nome);
    		p.setUser(mainApp.getUser());
    		playListTable.getItems().add(p);
    	}
    }

    @FXML
    private void removePlayList() {
    	PlayList playList = playListTable.getSelectionModel().getSelectedItem();
    	if(playList != null) {
    		playListTable.getSelectionModel().selectNext();;
    		playListTable.getItems().remove(playList);
    	}
    }

    @FXML
    private void addInPlayList() {
    	PlayList playList = playListTable.getSelectionModel().getSelectedItem();
    	Music music = musicTable.getSelectionModel().getSelectedItem();
    	if(playList != null && music != null && !playList.getMusics().contains(music)) {
    		playList.getMusics().add(music);
    		playTable.getItems().add(music);
    	}else if(music != null && !playTable.getItems().contains(music)) {
    		playTable.getItems().add(music);
    	}
    }

    @FXML
    private void removeInPlayList() {
    	Music music = playTable.getSelectionModel().getSelectedItem();
    	if(music != null) {
    		playTable.getSelectionModel().selectNext();
    		playTable.getItems().remove(music);
        	PlayList playList = playListTable.getSelectionModel().getSelectedItem();
    		if(playList != null)
        		playList.getMusics().remove(music);
    	}
    }

    /**
     * Carregar dos arquivos de um pasta
     */
    @FXML
    private void addDir() {
		// Show select directory dialog
		File file = new DirectoryChooser().showDialog(mainApp.getPrimaryStage());
		if (file != null) {
			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.length() > 3 && name.substring(name.length()-4, name.length()).equals(".mp3");
				}
			};
			int id = Util.getNextId(mainApp.getMusics());
			for(File f: file.listFiles(filter)) {
				mainApp.getMusics().add(new Music(id, f.getName().replaceFirst(".mp3", ""), f.getAbsolutePath()));
				++id;
			}
		}
	}

    /**
     * Carregar arquivos especificos
     */
    @FXML
    private void addFile() {
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3"));
		// Show select files dialog
		List<File> files = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());
		if (files != null) {
			int id = Util.getNextId(mainApp.getMusics());
			for(File f: files) {
				mainApp.getMusics().add(new Music(id, f.getName().replaceFirst(".mp3", ""), f.getAbsolutePath()));
				++id;
			}
		}
	}

    @FXML
    private void removeMusic() {
    	if(!musicTable.getItems().isEmpty()) {
    		Music music = musicTable.getSelectionModel().getSelectedItem();
    		musicTable.getSelectionModel().selectNext();
    		musicTable.getItems().remove(music);
    	}
    }

    /**
     * Finalizar App
     */
    @FXML
    private void exit() {
    	mainApp.exit();
    }

    @FXML
    private void showRootUser() throws IOException {
		RootUserController controller = getInstance(RootUserController.class, stage);
		controller.setUsers(mainApp.getUsers());
		controller.getStage().show();
	}

    /**
     * Metodo template para criacao da fucoes de duplo click em tabelas
     */
    private <T extends Playable> Callback<TableView<T>,TableRow<T>> getRowFactoryDoubleClick() {
		return tv -> {
    	    TableRow<T> row = new TableRow<>();
    	    row.setOnMouseClicked(event -> {
    	        if (event.getClickCount() == 2 && !row.isEmpty())
    	        	play(row.getItem());
    	    });
    	    return row;
    	};
	}
	
	@Override
	public void initStage() {
		stage.setTitle("MP3 Player");
		stage.getIcons().add(Util.getImage("icon.png"));
		stage.setResizable(false);
	}
}

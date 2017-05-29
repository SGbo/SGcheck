package de.grueter.sgcheck.client.gui;

	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane rootPane = FXMLLoader.load(getClass().getResource("MainView.fxml"));
			
			Scene scene = new Scene(rootPane, 800, 600);
			primaryStage.setTitle("EMU Check - Verwaltung von Messreihen");
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(eh -> {
				Platform.exit();
				System.exit(0);
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
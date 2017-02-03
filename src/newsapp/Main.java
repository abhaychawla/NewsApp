package newsapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Platform.runLater(()->{
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("splash.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root, 441, 313);
            //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("")));
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    primaryStage.close();
                    Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                    primaryStage.setTitle("News Flash");
                    Scene scene = new Scene(root, 1024, 640);
                    scene.getStylesheets().add(getClass().getResource("MainMenuStyleSheet.css").toExternalForm());
                    //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("")));
                    primaryStage.setScene(scene);
                    primaryStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(sleeper).start();


    }


    public static void main(String[] args) {
        launch(args);
    }
}

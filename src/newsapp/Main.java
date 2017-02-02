package newsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("News APP");
        Scene scene = new Scene(root, 1024, 480);
        scene.getStylesheets().add(getClass().getResource("MainMenuStyleSheet.css").toExternalForm());
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

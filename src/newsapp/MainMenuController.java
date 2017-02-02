package newsapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable{

    @FXML
    MenuBar menubar;
    //@FXML
    //AnchorPane topAnchorPane;
    @FXML
    BorderPane mainMenuBorderPane;
//    @FXML
//    Button button1;
//    @FXML
//    Button button2;
//    @FXML
//    Button button3;
    @FXML
    VBox rightVBox;
//    @FXML
//    HBox topButtons;
    @FXML
    ImageView shareToFacebook;
    @FXML
    ListView listNews;
    @FXML
    ImageView newsImage;
    @FXML
    TextArea newsText;

    private final Image IMAGE_APPLE  = new Image("http://findicons.com/files/icons/832/social_and_web/64/apple.png");
    private final Image IMAGE_TWITTER = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");

    private Image[] listOfImages = {IMAGE_APPLE, IMAGE_TWITTER};

    ObservableList<String> list = FXCollections.observableArrayList ();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newsText.prefWidthProperty().bind(rightVBox.widthProperty().divide(0.5));
        newsText.prefHeightProperty().bind(rightVBox.heightProperty().divide(0.5));
        //topButtons.prefWidthProperty().bind(topAnchorPane.widthProperty());
        //int topButtonCount = topButtons.getChildren().size();
//        button1.prefWidthProperty().bind(topButtons.widthProperty().divide(4));
//        button2.prefWidthProperty().bind(topButtons.widthProperty().divide(4));
//        button3.prefWidthProperty().bind(topButtons.widthProperty().divide(4));

        list.add("APPLE");
        list.add("TWITTER");
        listNews.setItems(list);

        listNews.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (name.equals("APPLE"))
                        imageView.setImage(listOfImages[0]);
                    else if (name.equals("TWITTER"))
                        imageView.setImage(listOfImages[1]);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });

        listNews.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    int pos = listNews.getSelectionModel().getSelectedIndex();
                    loadNews(pos);
                }
            }
        });

        listNews.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    int pos = listNews.getSelectionModel().getSelectedIndex();
                    loadNews(pos);
                }
            }
        });

        final KeyCombination escape = new KeyCodeCombination(KeyCode.ESCAPE);
        mainMenuBorderPane.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (escape.match(event)) {
                    listNews.getSelectionModel().clearSelection();
                    newsImage.setImage(null);
                    newsText.setText(null);
                }
            }
        });

        newsText.setEditable(false);
        newsText.setFocusTraversable(false);
        newsImage.setFocusTraversable(false);

        //BackgroundImage backgroundImage = new BackgroundImage(new Image("https://facebookbrand.com/wp-content/themes/fb-branding/prj-fb-branding/assets/images/fb-art.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        //Background background = new Background(backgroundImage);
        //button3.setBackground(background);
        shareToFacebook.setImage(new Image("https://facebookbrand.com/wp-content/themes/fb-branding/prj-fb-branding/assets/images/fb-art.png"));
    }

    void loadNews(int pos)
    {
        if(pos == 0) {
            newsImage.setImage(listOfImages[0]);
            newsText.setText("NEWS");
        }
        else if(pos == 1) {
            newsImage.setImage(listOfImages[1]);
            newsText.setText("NEWS");
        }
    }

}

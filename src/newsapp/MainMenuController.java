package newsapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.NewsWithImages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    private static final String USER_AGENT = "Mozilla/5.0";

    private final Image IMAGE_APPLE  = new Image("http://findicons.com/files/icons/832/social_and_web/64/apple.png");
    private final Image IMAGE_TWITTER = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");

    private Image[] listOfImages = {IMAGE_APPLE, IMAGE_TWITTER};

    ObservableList<String> list = FXCollections.observableArrayList ();
    ObservableList<NewsWithImages>newsWithImages=FXCollections.observableArrayList();

     void getNews() throws IOException, JSONException {

         String url = "https://newsapi.org/v1/articles?source=techcrunch&apiKey=b85f613f1fda40539e5547c64d03f345";

         URL obj = new URL(url);
         HttpURLConnection con = (HttpURLConnection) obj.openConnection();
         con.setRequestMethod("GET");

         //add request header
         con.setRequestProperty("User-Agent", USER_AGENT);
         JSONObject newsJson;

         int responseCode = con.getResponseCode();
         System.out.println("\nSending 'GET' request to URL : " + url);
         System.out.println("Response Code : " + responseCode);
         BufferedReader in = new BufferedReader(
                 new InputStreamReader(con.getInputStream()));
         String inputLine;
         StringBuffer response = new StringBuffer();

         while ((inputLine = in.readLine()) != null) {
             response.append(inputLine);
         }
         in.close();

         //print result
         System.out.println(response.toString());
         newsJson=new JSONObject(response.toString());
         System.out.println("YO"+newsJson);
         JSONArray articles =newsJson.getJSONArray("articles");

         Object titles;
         Object urls;

         for (int i = 0; i < articles.length(); i++) {
             titles=articles.getJSONObject(i).get("title");
             urls=articles.getJSONObject(i).get("urlToImage");
             newsWithImages.add(new NewsWithImages((String) titles, (String) urls));


             list.add((newsWithImages.get(i).getTitle()));
             System.out.println("\n" +  titles);
         }


     }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newsText.prefWidthProperty().bind(rightVBox.widthProperty().divide(0.5));
        newsText.prefHeightProperty().bind(rightVBox.heightProperty().divide(0.5));
        //topButtons.prefWidthProperty().bind(topAnchorPane.widthProperty());
        //int topButtonCount = topButtons.getChildren().size();
//        button1.prefWidthProperty().bind(topButtons.widthProperty().divide(4));
//        button2.prefWidthProperty().bind(topButtons.widthProperty().divide(4));
//        button3.prefWidthProperty().bind(topButtons.widthProperty().divide(4));
        try {
            getNews();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listNews.setItems(list);


       listNews.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);

                for (NewsWithImages img : newsWithImages) {
                    if (name==img.getTitle()) {
                        setText(img.getTitle());
                        imageView.setImage(new Image(img.getImageurl(), 50, 50, false, false));
                        setGraphic(imageView);
                    }
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

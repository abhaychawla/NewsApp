package newsapp;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PostUpdate;
import facebook4j.auth.AccessToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import java.net.MalformedURLException;
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
    ImageView shareToInstagram;
    @FXML
    ImageView sharetoTwitter;
    @FXML
    ListView listNews;
    @FXML
    ImageView newsImage;
    @FXML
    Label newsText;
    private static final String USER_AGENT = "Mozilla/5.0";

    private final Image IMAGE_APPLE  = new Image("http://findicons.com/files/icons/832/social_and_web/64/apple.png");
    private final Image IMAGE_TWITTER = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");

    private Image[] listOfImages = {IMAGE_APPLE, IMAGE_TWITTER};

    ObservableList<String> list = FXCollections.observableArrayList ();
    ObservableList<NewsWithImages>newsWithImages=FXCollections.observableArrayList();

     void getNews() throws IOException, JSONException {

         String url = "https://newsapi.org/v1/articles?source=techcrunch&apiKey=b85f613f1fda40539e5547c64d03f345";
         String url2=" https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=b85f613f1fda40539e5547c64d03f345";

         URL obj = new URL(url);
         URL obj2 = new URL(url2);
         HttpURLConnection con = (HttpURLConnection) obj.openConnection();
         HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();
         con.setRequestMethod("GET");
         con2.setRequestMethod("GET");

         //add request header
         con.setRequestProperty("User-Agent", USER_AGENT);
         con2.setRequestProperty("User-Agent", USER_AGENT);

         JSONObject newsJson;
         JSONObject newsJson2;

         int responseCode = con.getResponseCode();
         int responseCode2 = con2.getResponseCode();
         System.out.println("\nSending 'GET' request to URL : " + url);
         System.out.println("\nSending 'GET' request to URL : " + url2);
         System.out.println("Response Code : " + responseCode);
         System.out.println("Response Code : " + responseCode2);
         BufferedReader in = new BufferedReader(
                 new InputStreamReader(con.getInputStream()));
         String inputLine;
         StringBuffer response = new StringBuffer();

         while ((inputLine = in.readLine()) != null) {
             response.append(inputLine);
         }
         in.close();

         BufferedReader in2 = new BufferedReader(
                 new InputStreamReader(con2.getInputStream()));
         String inputLine2;
         StringBuffer response2 = new StringBuffer();

         while ((inputLine2 = in2.readLine()) != null) {
             response2.append(inputLine2);
         }
         in2.close();

         //print result
         System.out.println(response.toString());
         newsJson=new JSONObject(response.toString());
         newsJson2=new JSONObject(response2.toString());
         System.out.println("YO"+newsJson);
         JSONArray articles =newsJson.getJSONArray("articles");
         JSONArray articles2 =newsJson2.getJSONArray("articles");

         Object titles;
         Object urls;
         Object discription;
         int news = 0;

         for (int i = 0; i < articles.length(); i++) {
             titles=articles.getJSONObject(i).get("title");
             urls=articles.getJSONObject(i).get("urlToImage");
             discription= articles.getJSONObject(i).get("description");
             newsWithImages.add(new NewsWithImages((String) titles, (String) urls, (String) discription));


             list.add((newsWithImages.get(i).getTitle()));
             System.out.println("\n" +  titles);
             news=i;
         }

         Object titles2;
         Object urls2;
         Object discription2;
         for (int i = news+1; i < news+articles2.length(); i++) {
             try {
                 titles2 = articles2.getJSONObject(i).get("title");
                 urls2 = articles2.getJSONObject(i).get("urlToImage");
                 discription2 = articles2.getJSONObject(i).get("description");
                 newsWithImages.add(new NewsWithImages((String) titles2, (String) urls2, (String) discription2));


                 list.add((newsWithImages.get(i).getTitle()));
                 System.out.println("\n" + titles2);
             }
             catch(Exception e)
             {

             }
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
                setWrapText(true);
                for (NewsWithImages img : newsWithImages) {
                    if (name==img.getTitle()) {
                        setText(img.getTitle());
                        imageView.setImage(new Image(img.getImageurl(), 50, 50, false, false));
                        setGraphic(imageView);
                    }
                }
            }

            });

       shareToFacebook.setOnMousePressed(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
                int pos = listNews.getSelectionModel().getSelectedIndex();
               try {
                   facebookPOST(pos);
               } catch (Exception e)
               {
               }
           }
       });

        listNews.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
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



        newsImage.setFocusTraversable(false);

        //BackgroundImage backgroundImage = new BackgroundImage(new Image("https://facebookbrand.com/wp-content/themes/fb-branding/prj-fb-branding/assets/images/fb-art.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        //Background background = new Background(backgroundImage);
        //button3.setBackground(background);
        shareToFacebook.setImage(new Image("https://facebookbrand.com/wp-content/themes/fb-branding/prj-fb-branding/assets/images/fb-art.png"));
        shareToInstagram.setImage(new Image("http://3835642c2693476aa717-d4b78efce91b9730bcca725cf9bb0b37.r51.cf1.rackcdn.com/Instagram_App_Large_May2016_200.png"));
        sharetoTwitter.setImage(new Image("https://cdn1.iconfinder.com/data/icons/logotypes/32/twitter-128.png"));

        Facebook facebook = new FacebookFactory().getInstance();

    }

    void loadNews(int pos)
    {
       /* if(pos == 0) {
            newsImage.setImage(listOfImages[0]);
            newsText.setText("NEWS");
        }
        else if(pos == 1) {
            newsImage.setImage(listOfImages[1]);
            newsText.setText("NEWS");
        }*/
       System.out.println("i am here");
        newsImage.setImage(new Image(newsWithImages.get(pos).getImageurl()));
        newsText.setText(newsWithImages.get(pos).getDiscription());




    }

    void facebookPOST(int pos) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Posted!");
        //ImageView iv=null;
        //iv.setImage(new Image("http://www.clipartbest.com/cliparts/7ia/o7p/7iao7p5KT.gif"));
        //alert.setGraphic(iv);
        System.out.print("alert");


         try {
             Facebook facebook = new FacebookFactory().getInstance();
             facebook.setOAuthAppId("1909590085937544", "33ee14f807feb63d582e9314aa2ca6ce");
             //facebook.setOAuthPermissions(commaSeparetedPermissions);
             facebook.setOAuthAccessToken(new AccessToken("EAACEdEose0cBAEJuTqwZBm1JZCQ8GPZBjjQxrfNE3YtFxTDtSodiTtUnzf4VAsHXtOWRqSDqkwhoTpZBLGQJ424pfzLJdzgXXzpfJ61SJzPXcEJVFYazXxLV0jOa2qIuDRIs2ZC1QZBzLohJIlYzZCC7XVIChHOyagghNliqjVhXRw9qlyh6TozzfpE2foDalUZD", null));
             //facebook.postStatusMessage("Hello World from Facebook4J.");

             PostUpdate post = new PostUpdate(new URL("https://www.google.co.in/?gfe_rd=cr&ei=sP6SWLnPLu7s8Aew5oy4Dw"))
                     .picture(new URL(newsWithImages.get(pos).getImageurl()))
                     .name(newsWithImages.get(pos).getTitle())
                     .caption("")
                     .description(newsWithImages.get(pos).getDiscription());
             facebook.postFeed(post);

             alert.showAndWait();


         }
         catch(Exception e)
         {

         }
    }

}

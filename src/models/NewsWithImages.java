package models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Jov on 02/02/17.
 */
public class NewsWithImages {
    private SimpleStringProperty title;
    private SimpleStringProperty imageurl;
    private SimpleStringProperty discription;
public NewsWithImages(String title,String imageurl,String discription){
    this.title=new SimpleStringProperty(title);
    this.imageurl=new SimpleStringProperty(imageurl);
    this.discription=new SimpleStringProperty(discription);


}
    public String getTitle() {
        return title.get();
    }


    public String getImageurl() {
        return imageurl.get();
    }


    public void setTitle(String newValue) {
        title.set(newValue);

    }


    public void setImageurl(String newValue) {
        imageurl.set(newValue);

    }
    public String getDiscription() {
        return discription.get();
    }





}

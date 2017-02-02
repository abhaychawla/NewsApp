package models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Jov on 02/02/17.
 */
public class NewsWithImages {
    private SimpleStringProperty title;
    private SimpleStringProperty imageurl;
public NewsWithImages(String title,String imageurl){
    this.title=new SimpleStringProperty(title);
    this.imageurl=new SimpleStringProperty(imageurl);


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


}

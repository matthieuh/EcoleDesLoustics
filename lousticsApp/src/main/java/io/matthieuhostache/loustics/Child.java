package io.matthieuhostache.loustics;

/**
 * Created by matthieu on 13/03/14.
 */
public class Child {

    private static int increment = 0;
    private int id;
    private String picPath;

    public Child(){
        id = ++increment;
    }

    public Child(String picPath){
        id = ++increment;
        this.picPath = picPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}

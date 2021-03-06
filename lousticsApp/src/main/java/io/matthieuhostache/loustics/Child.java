package io.matthieuhostache.loustics;

/**
 * Created by matthieu on 13/03/14.
 */
public class Child {


    private int id;
    private String picPath;
    private String name;
    private int points;

    public Child(){
        this.points=0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Child(String name, String picPath){
        this.name = name;
        this.picPath = picPath;
        this.points = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}

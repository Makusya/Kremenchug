package com.example.sash.kremenchug;

public class Upload {
    private String Topic;
    private String Image;
    private String Text;
    private String Coordinate;


    public Upload(){

    }

    public Upload(String Topic, String Image, String Coordinate, String Text ) {
        this.Coordinate = Coordinate;
        this.Image = Image;
        this.Text = Text;
        this.Topic = Topic;
    }

    public String getTopic() {
        return Topic;
    }

    public String getImage() {
        return Image;
    }

    public String getText() {
        return Text;
    }

    public String getCoordinate() {
        return Coordinate;
    }
}

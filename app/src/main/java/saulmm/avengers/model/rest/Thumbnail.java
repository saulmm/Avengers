package saulmm.avengers.model.rest;

public class Thumbnail {

    private String path;
    private String extension;

    public String getImageUrl () {

        return String.format("%s.%s", path, extension);
    }
}
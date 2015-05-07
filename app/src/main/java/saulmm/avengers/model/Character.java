package saulmm.avengers.model;

import saulmm.avengers.model.rest.Thumbnail;

public class Character {

    private int id;
    private String name;
    private String description;
    private int imageResource;
    private Thumbnail thumbnail;
    private String resourceURI;

    public Character(String name, int thumb_resource, int id) {

        this.name = name;
        this.imageResource = thumb_resource;
        this.id = id;
    }

    public Character(String name, int imageResource) {

        this.name = name;
        this.imageResource = imageResource;
    }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public String getDescription() {

        return description;
    }

    public String getImageUrl() {

        return thumbnail.getImageUrl();
    }

    public int getImageResource() {

        return imageResource;
    }
}

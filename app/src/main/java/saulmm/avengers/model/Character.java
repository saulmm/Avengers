package saulmm.avengers.model;

public class Character {

    private String title;
    private int imageResource;

    public Character(String title) {

        this.title = title;
    }

    public Character(String title, int imageResource) {

        this.title = title;
        this.imageResource = imageResource;
    }

    public String getTitle () {

        return title;
    }

    public String getThumbnail () {

        return "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg";
    }

    public int getImageResource() {

        return imageResource;
    }
}

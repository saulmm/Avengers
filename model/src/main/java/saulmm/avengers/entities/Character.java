package saulmm.avengers.entities;


public class Character {
    private int id;
    private String name;
    private String bio;
    private String imageUrl;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return name;
    }
}

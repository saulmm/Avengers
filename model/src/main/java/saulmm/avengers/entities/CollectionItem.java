package saulmm.avengers.entities;


public class CollectionItem {
    public final static String KEY_COMICS 	= "comics";
    public final static String KEY_SERIES 	= "series";
    public final static String KEY_STORIES 	= "stories";
    public final static String KEY_EVENTS 	= "events";

    public enum Type {
        COMIC(KEY_COMICS),
        SERIE(KEY_SERIES),
        STORY(KEY_STORIES),
        EVENT(KEY_EVENTS);

        private String key;

        Type(String keyComics) {
            key = keyComics;
        }

        public String key() {
            return key;
        }
    }

    private int id;
    private String title;
    private String description;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

package saulmm.avengers.model;

import java.util.List;

import saulmm.avengers.model.rest.Thumbnail;

public class Comic {

    private String id;
    private String title;
    private String desccription;
    private List<Thumbnail> images;
    private int pageCount;

    public String getId() {

        return id;
    }

    public int getPageCount() {

        return pageCount;
    }

    public String getFirstImageUrl () {

        if (!images.isEmpty() && images.get(0) != null)
            return images.get(0).getImageUrl();

        return null;
    }

    public String getTitle() {

        return title;
    }

    public String getDesccription() {

        return desccription;
    }
}

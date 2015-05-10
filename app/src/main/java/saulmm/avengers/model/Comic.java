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
}

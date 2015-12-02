/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.entities;

@SuppressWarnings("unused")
public class MarvelCharacter {
    private int id;
    private int imageResource;
    private String name;
    private String description;
    private Thumbnail thumbnail;
    private String resourceURI;
    private ComicsCollection comics;
    private ComicsCollection series;
    private ComicsCollection stories;
    private ComicsCollection events;


    public MarvelCharacter(String name, int thumb_resource, int id) {
        this.name = name;
        this.imageResource = thumb_resource;
        this.id = id;
    }

    public MarvelCharacter(String name, int imageResource) {
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
        return (thumbnail != null) ? thumbnail.getImageUrl() : null;
    }

    public int getImageResource() {
        return imageResource;
    }

    public ComicsCollection getSeries() {
        return series;
    }

    public ComicsCollection getStories() {
        return stories;
    }

    public ComicsCollection getEvents() {
        return events;
    }

    public ComicsCollection getComics() {
        return comics;
    }
}

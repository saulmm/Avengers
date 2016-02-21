/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.rest.entities;

@SuppressWarnings("unused")
public class RestCharacter {
    private int id;
    private int imageResource;
    private String name;
    private String description;
    private RestThumbnail thumbnail;
    private String resourceURI;
    private RestComicsCollection comics;
    private RestComicsCollection series;
    private RestComicsCollection stories;
    private RestComicsCollection events;


    public RestCharacter(String name, int thumb_resource, int id) {
        this.name = name;
        this.imageResource = thumb_resource;
        this.id = id;
    }

    public RestCharacter(String name, int imageResource) {
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

    public RestComicsCollection getSeries() {
        return series;
    }

    public RestComicsCollection getStories() {
        return stories;
    }

    public RestComicsCollection getEvents() {
        return events;
    }

    public RestComicsCollection getComics() {
        return comics;
    }
}

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.entities;

public class Character {

    public final static String [] MARVEL_API_CHARACTER_FIELDS = {
        "id", "name", "description", "modified", "resourceURI", "urls", "comics",
        "stories", "events", "series"
    };

    private int id;
    private String name;
    private String description;
    private int imageResource;
    private Thumbnail thumbnail;
    private String resourceURI;
    private ComicsCollection comics;


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
        return (thumbnail != null) ? thumbnail.getImageUrl() : null;
    }

    public int getImageResource() {

        return imageResource;
    }
}

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.entities;

import java.util.List;

public class Comic {

    private String id;
    private String title;
    private String desccription;
    private List<Thumbnail> images;
    private List<TextObject> textObjects;
    private List<ComicDate> dates;
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

    public String getFirstTextObject () {

        if (!textObjects.isEmpty())
            return textObjects.get(0).getText();

        return null;
    }

    public List<ComicDate> getDates() {

        return dates;
    }
}

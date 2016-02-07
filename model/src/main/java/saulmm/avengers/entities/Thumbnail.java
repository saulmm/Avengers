/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.entities;

public class Thumbnail {

    private String path;
    private String extension;

    public String getImageUrl () {

        return String.format("%s.%s", path, extension);
    }
}
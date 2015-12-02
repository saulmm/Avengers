/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.repository.wrappers;

public class MarvelApiWrapper {

    private String code;
    private String status;

    private MarvelDataWrapper data;

    public String getCode() {

        return code;
    }

    public String getStatus() {

        return status;
    }

    public MarvelDataWrapper getData() {

        return data;
    }
}





/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.rest;

import java.util.List;

import saulmm.avengers.model.Character;

public class MarvelDataWrapper {

    private int count;
    private List<Character> results;

    public int getCount() {

        return count;
    }

    public List<Character> getResults() {

        return results;
    }
}
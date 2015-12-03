/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.repository.wrappers;

import java.util.List;
import saulmm.avengers.entities.MarvelCharacter;

public class MarvelDataWrapper {

    private int count;
    private List<MarvelCharacter> results;

    public int getCount() {

        return count;
    }

    public List<MarvelCharacter> getResults() {

        return results;
    }
}
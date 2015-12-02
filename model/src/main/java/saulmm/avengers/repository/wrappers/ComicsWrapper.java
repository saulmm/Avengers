/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.repository.wrappers;

import java.util.ArrayList;
import saulmm.avengers.entities.Comic;

public class ComicsWrapper {

    private ArrayList<Comic> mComics;

    public ComicsWrapper(ArrayList<Comic> comicsList) {

        mComics = comicsList;
    }

    public ArrayList<Comic> getmComics() {

        return mComics;
    }
}

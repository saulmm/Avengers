/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.views;

import saulmm.avengers.model.Comic;

public interface AvengersDetailView extends View {

    void startLoading ();

    void stopLoadingAvengersInformation();

    void startLoadingComics();

    void showAvengerBio (String text);

    void showAvengerImage (String url);

    void showAvengerName (String name);

    void addComic (Comic comic);

    void stopLoadingComicsIfNeeded();

    void clearComicsView();

    void showError(String s);
}

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.views;

import android.graphics.Bitmap;
import saulmm.avengers.model.entities.Comic;

public interface AvengersDetailView extends View {

    void initActivityColors(Bitmap sourceBitmap);

    void hideRevealViewByAlpha();

    void startLoading ();

    void stopLoadingAvengersInformation();

    void showAvengerBio (String text);

    void showAvengerImage (String url);

    void showAvengerName (String name);

    void addComic (Comic comic);

    void stopLoadingComicsIfNeeded();

    void clearComicsView();

    void showError(String s);

    void hideComics();
}

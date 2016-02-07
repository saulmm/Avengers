/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.views;

import android.graphics.Bitmap;
import saulmm.avengers.entities.MarvelCharacter;

public interface CharacterDetailView extends View {
    void disableScroll();

    void hideRevealViewByAlpha();

    void bindCharacter(MarvelCharacter character);

    void enableScroll();

    void goToCharacterComicsView(int characterId);

    void goToCharacterSeriesView(int characterId);

    void goToCharacterEventsView(int characterId);

    void goToCharacterStoriesView(int characterId);

    void showError(String s);

}

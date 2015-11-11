/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.views;

import android.graphics.Bitmap;
import saulmm.avengers.model.entities.Character;

public interface CharacterDetailView extends View {
    void hideRevealViewByAlpha();

    void showError(String s);

    void bindCharacter(Character character);

    void initActivityColors(Bitmap resource);
}

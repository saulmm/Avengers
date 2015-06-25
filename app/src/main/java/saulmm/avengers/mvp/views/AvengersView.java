/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.views;

import java.util.List;

import saulmm.avengers.model.entities.Character;

public interface AvengersView extends View {

    void showAvengersList (List<Character> avengers);

    void showLoadingIndicator ();

    void hideLoadingIndicator ();

    void showGenericError();
}

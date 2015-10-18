/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.views;

import android.app.ActivityOptions;
import java.util.List;
import saulmm.avengers.model.entities.Character;

public interface AvengersView extends View {

    void bindCharacterList(List<Character> avengers);

    void showCharacterList();

    void hideAvengersList();

    void showLoadingMoreCharactersIndicator();

    void hideLoadingMoreCharactersIndicator();

    void hideLoadingIndicator ();

    void showLoadingView();

    void hideLoadingView();

    void showLightError();

    void showErrorView(String errorMessage);

    void hideErrorView();

    void showEmptyIndicator();

    void hideEmptyIndicator();

    void updateCharacterList(int charactersLimit);

    ActivityOptions getActivityOptions(int position, android.view.View clickedView);
}

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector.modules;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import saulmm.avengers.R;
import saulmm.avengers.injector.Activity;
import saulmm.avengers.model.Character;

@Module
public class AvengersModule {

    @Provides @Activity
    List<Character> provideAvengers() {

        List<Character> avengers = new ArrayList<>(6);
        avengers.add(new Character("Iron Man", R.drawable.thumb_iron_man, 1009368));
        avengers.add(new Character("Thor", R.drawable.thumb_thor, 1009664));
        avengers.add(new Character("Captain America", R.drawable.thumb_cap,1009220));
        avengers.add(new Character("Black Widow", R.drawable.thumb_nat, 1009189));
        avengers.add(new Character("Hawkeye", R.drawable.thumb_hawkeye, 1009338));
        avengers.add(new Character("Hulk", R.drawable.thumb_hulk, 1009351));
        return avengers;
    }
}

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model;

import java.util.List;

import rx.Observable;

public interface Repository {

    Observable<Character> getCharacter (final int characterId);

    Observable<List<Comic>> getCharacterComics (final int characterId);
}

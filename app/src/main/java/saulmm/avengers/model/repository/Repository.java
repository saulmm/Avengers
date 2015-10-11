/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.repository;

import java.util.List;

import rx.Observable;
import saulmm.avengers.model.entities.*;
import saulmm.avengers.model.entities.Character;

public interface Repository {

    Observable<Character> getCharacter (final int characterId);

    Observable<List<Character>> getCharacters (int offset);

    Observable<List<Comic>> getCharacterComics (final int characterId);
}

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.repository;

import java.util.List;
import rx.Observable;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.entities.MarvelCharacter;

public interface CharacterRepository {
    Observable<MarvelCharacter> getCharacter (final int characterId);

    Observable<List<MarvelCharacter>> getCharacters (int offset);

    Observable<List<CollectionItem>> getCharacterCollection(int characterId, String type);
}

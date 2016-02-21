/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers;

import java.util.List;
import rx.Observable;
import saulmm.avengers.entities.Character;
import saulmm.avengers.rest.entities.RestCollectionItem;
import saulmm.avengers.rest.entities.RestCharacter;

public interface CharacterDatasource {
    Observable<Character> getCharacter (final int characterId);

    Observable<List<Character>> getCharacters (int offset);

    Observable<List<RestCollectionItem>> getCharacterCollection(int characterId, String type);
}

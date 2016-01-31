/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.rest;

import java.util.List;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.entities.MarvelCharacter;

public interface MarvelApi {
    @GET("/v1/public/characters")
    Observable<List<MarvelCharacter>> getCharacters (@Query("offset") int offset);

    @GET("/v1/public/characters")
    Observable<List<MarvelCharacter>> getCharacterById(@Query("id") int id);

    @GET("/v1/public/characters/{characterId}/{collectionType}")
    Observable<List<CollectionItem>> getCharacterCollection(
        @Path("characterId") int id,
        @Path("collectionType") String collectionType);
}

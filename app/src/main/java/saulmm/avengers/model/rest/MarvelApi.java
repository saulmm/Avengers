/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.rest;

import java.util.List;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface MarvelApi {

    String END_POINT       = "http://gateway.marvel.com/";
    String PARAM_API_KEY   = "apikey";
    String PARAM_HASH      = "hash";
    String PARAM_TIMESTAMP = "ts";

    @GET("/v1/public/characters")
    Observable<List<saulmm.avengers.model.entities.Character>> getCharacters (@Query("offset") int offset);

    //@GET("/v1/public/characters")
    //Observable<Character> getCharacterById(@Query("id") int id);
	//
    //@GET("/v1/public/characters/{characterId}/comics")
    //Observable<List<Comic>> getCharacterComics(@Path("characterId") int id,
    //                        @Query("format") String format,
    //                        @Query("formatType") String fromatType);
}

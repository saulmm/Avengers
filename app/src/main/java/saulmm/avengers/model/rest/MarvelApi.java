package saulmm.avengers.model.rest;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import saulmm.avengers.model.MarvelApiWrapper;

public interface MarvelApi {

    public final String END_POINT       = "http://gateway.marvel.com/";
    public final String PARAM_API_KEY   = "apikey";
    public final String PARAM_HASH      = "hash";
    public final String PARAM_TIMESTAMP = "ts";

    @GET("/v1/public/characters")
    void getCharacter(@Query("id") String id,
                      Callback<MarvelApiWrapper> callback);

    @GET("/v1/public/characters/{characterId}/comics")
    void getCharacterComics(@Path("characterId") String id,
                            @Query("format") String format,
                            @Query("formatType") String fromatType,
                            Callback<MarvelApiWrapper> callback);
}

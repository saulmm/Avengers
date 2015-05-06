package saulmm.avengers.model.rest;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import saulmm.avengers.model.Character;

public interface MarvelApi {

    public final String END_POINT       = "http://gateway.marvel.com/";
    public final String PARAM_API_KEY   = "apikey";
    public final String PARAM_HASH      = "hash";
    public final String PARAM_TIMESTAMP = "ts";

    @GET("/v1/public/characters")
    void getCharacter(@Query("id") String id, Callback<Character> callback);
}

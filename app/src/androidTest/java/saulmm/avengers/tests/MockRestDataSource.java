package saulmm.avengers.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import rx.Observable;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.CharacterRepository;
import saulmm.avengers.rest.utils.deserializers.MarvelResultsDeserializer;

/**
 * Created by saulmm on 04/01/16.
 */
public class MockRestDataSource implements CharacterRepository {

    private final static Gson customGsonInstance;

    static {
        customGsonInstance = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<List<MarvelCharacter>>() {
            }.getType(),
                    new MarvelResultsDeserializer<MarvelCharacter>())

            .create();
    }
    
    @Override
    public Observable<MarvelCharacter> getCharacter(int characterId) {
        List<MarvelCharacter> marvelCharactersList = customGsonInstance.fromJson(
                TestData.SINGLE_CHARACTER_JSON,
                new TypeToken<List<MarvelCharacter>>() {}.getType());

        return Observable.just(marvelCharactersList.get(0));
    }

    @Override
    public Observable<List<MarvelCharacter>> getCharacters(int offset) {
        List<MarvelCharacter> marvelCharactersList = customGsonInstance.fromJson(
                TestData.TWENTY_CHARACTERS_JSON,
                new TypeToken<List<MarvelCharacter>>() {}.getType());

        return Observable.just(marvelCharactersList);
    }

    @Override
    public Observable<List<CollectionItem>> getCharacterCollection(int characterId, String type) {
        return null;
    }
}

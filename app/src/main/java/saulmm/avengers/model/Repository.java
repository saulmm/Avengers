package saulmm.avengers.model;

import java.util.List;

import rx.Observable;

public interface Repository {

    Observable<Character> getCharacter (final int characterId);

    Observable<List<Comic>> getCharacterComics (final int characterId);
}

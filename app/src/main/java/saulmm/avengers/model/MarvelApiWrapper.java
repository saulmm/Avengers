package saulmm.avengers.model;

import java.util.List;

public class MarvelApiWrapper {

    private String code;
    private String status;

    private MarvelCharacterData data;

}

class MarvelCharacterData {

    int count;
    List<Character> results;
}

class Thumbnail {

    private String path;
    private String extension;

    public String getImageUrl () {

        return path + extension;
    }
}

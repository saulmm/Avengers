package saulmm.avengers.model.rest;

import java.util.List;

import saulmm.avengers.model.Character;

public class MarvelDataWrapper {

    private int count;
    private List<Character> results;

    public int getCount() {

        return count;
    }

    public List<Character> getResults() {

        return results;
    }
}
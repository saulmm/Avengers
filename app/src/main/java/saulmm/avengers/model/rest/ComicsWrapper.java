package saulmm.avengers.model.rest;

import java.util.ArrayList;

import saulmm.avengers.model.Comic;

public class ComicsWrapper {

    private ArrayList<Comic> mComics;

    public ComicsWrapper(ArrayList<Comic> comicsList) {

        mComics = comicsList;
    }

    public ArrayList<Comic> getmComics() {

        return mComics;
    }
}

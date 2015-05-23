package saulmm.avengers.mvp.views;

import saulmm.avengers.model.Comic;

public interface AvengersDetailView extends View {

    void startLoading ();

    void stopLoading ();

    void startLoadingComics();

    void showAvengerBio (String text);

    void showAvengerImage (String url);

    void showAvengerName (String name);

    void addComic (Comic comic);

    void hideComicProgressIfNeeded();

    void clearComicsView();
}

package saulmm.avengers.mvp.views;

import saulmm.avengers.model.Comic;

public interface AvengersDetailView extends View {

    void startLoading ();

    void stopLoadingAvengersInformation();

    void startLoadingComics();

    void showAvengerBio (String text);

    void showAvengerImage (String url);

    void showAvengerName (String name);

    void addComic (Comic comic);

    void stopLoadingComicsIfNeeded();

    void clearComicsView();

    void showError(String s);
}

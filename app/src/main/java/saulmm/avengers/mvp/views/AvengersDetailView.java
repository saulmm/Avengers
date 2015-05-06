package saulmm.avengers.mvp.views;

public interface AvengersDetailView extends View {

    void startLoading ();

    void stopLoading ();

    void showAvengerBio (String text);

    void showAvengerImage (String url);

    void showAvengerName (String name);
}

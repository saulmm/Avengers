package saulmm.avengers.mvp.views;

import java.util.List;

import saulmm.avengers.model.Character;

public interface AvengersView extends View {

    void showAvengersList (List<Character> avengers);
}

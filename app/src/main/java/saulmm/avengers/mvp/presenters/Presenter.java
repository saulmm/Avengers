package saulmm.avengers.mvp.presenters;

import android.content.Intent;

import saulmm.avengers.mvp.views.View;

public interface Presenter {

    void attachView (View v);

    void attachIncomingIntent (Intent intent);
}

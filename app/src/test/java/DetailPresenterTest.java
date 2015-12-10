import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import rx.Observable;
import saulmm.avengers.CharacterDetailsUsecase;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.mvp.presenters.CharacterDetailPresenter;
import saulmm.avengers.mvp.views.CharacterDetailView;

import static org.mockito.Mockito.*;

public class DetailPresenterTest {
    @Mock CharacterDetailView mockDetailView;
    @Mock CharacterDetailsUsecase mockDetailsUsecase;

    @Before public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void testThatPresenterAsksForCharacterDetails() throws Exception {
        CharacterDetailPresenter characterListPresenter = givenACharacterDetailPresenter();

        when(mockDetailsUsecase.execute()).thenReturn(getFakeObservableCharacter());
        characterListPresenter.askForCharacterDetails();

        verify(mockDetailsUsecase, times(1)).execute();
    }

    private CharacterDetailPresenter givenACharacterDetailPresenter(){
        return new CharacterDetailPresenter(mockDetailsUsecase);
    }

    private Observable<MarvelCharacter> getFakeObservableCharacter() {
        return Observable.just(new MarvelCharacter("", -1));
    }
}

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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
    final int FAKE_CHARACTER_ID = 0;

    @Before public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void testThatPresenterAsksForCharacterDetails() throws Exception {
        CharacterDetailPresenter characterListPresenter = givenACharacterDetailPresenter();

        when(mockDetailsUsecase.execute()).thenReturn(getFakeObservableCharacter());
        characterListPresenter.askForCharacterDetails();

        verify(mockDetailsUsecase, times(1)).execute();
    }

    @Test public void testThatPresentersOpensComicsView() throws Exception {
        CharacterDetailPresenter characterDetailPresenter = givenACharacterDetailPresenter();

        characterDetailPresenter.onComicsIndicatorPressed();

        verify(mockDetailView, times(1)).goToCharacterComicsView(FAKE_CHARACTER_ID);
    }

    @Test public void testThatPresentersOpensSeriesView() throws Exception {
        CharacterDetailPresenter characterDetailPresenter = givenACharacterDetailPresenter();

        characterDetailPresenter.onSeriesIndicatorPressed();

        verify(mockDetailView, times(1)).goToCharacterSeriesView(FAKE_CHARACTER_ID);
    }

    @Test public void testThatPresentersOpensStoriesView() throws Exception {
        CharacterDetailPresenter characterDetailPresenter = givenACharacterDetailPresenter();

        characterDetailPresenter.onStoriesIndicatorPressed();

        verify(mockDetailView, times(1)).goToCharacterStoriesView(FAKE_CHARACTER_ID);
    }

    @Test public void testThatPresentersOpensEventsView() throws Exception {
        CharacterDetailPresenter characterDetailPresenter = givenACharacterDetailPresenter();

        characterDetailPresenter.onEventIndicatorPressed();

        verify(mockDetailView, times(1)).goToCharacterEventsView(FAKE_CHARACTER_ID);
    }

    private CharacterDetailPresenter givenACharacterDetailPresenter(){
        CharacterDetailPresenter characterDetailPresenter = new CharacterDetailPresenter(mockDetailsUsecase);
        characterDetailPresenter.attachView(mockDetailView);
        return characterDetailPresenter;
    }

    private Observable<MarvelCharacter> getFakeObservableCharacter() {
        return Observable.just(new MarvelCharacter("", -1));
    }
}

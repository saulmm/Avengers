import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import saulmm.avengers.GetCharactersUsecase;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.mvp.presenters.CharacterListPresenter;
import saulmm.avengers.mvp.views.CharacterListView;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListPresenterTest {

	@Mock CharacterListView mockCharacterListView;
	@Mock GetCharactersUsecase mockGetCharacterUsecase;

	@Before public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test public void testThatCharactersArePassedToTheView() throws Exception {
		CharacterListPresenter listPresenter = givenAListPresenter();
		ArrayList<MarvelCharacter> fakeCharacterList = givenAFakeCharacterList();

		listPresenter.onCharactersReceived(fakeCharacterList);

		verify(mockCharacterListView, times(1))
			.bindCharacterList(fakeCharacterList);
	}

	@Test public void testThatPresenterRequestCharacters() throws Exception {
		CharacterListPresenter listPresenter = givenAListPresenter();

		when(mockGetCharacterUsecase.execute()).thenReturn(getFakeObservableCharacterList());
		listPresenter.askForCharacters();

		verify(mockGetCharacterUsecase, times(1)).execute();
	}

	@Test public void testThatPresenterShowsErrorWhenLoadingCharacters() throws Exception {
		CharacterListPresenter listPresenter = givenAListPresenter();

		when(mockGetCharacterUsecase.execute()).thenReturn(Observable.error(new Exception()));
		listPresenter.askForCharacters();

		verify(mockCharacterListView, times(1)).showUknownErrorMessage();
	}

	@Test public void testThatPresenterShowsALightErrorLoadingMoreCharacters() throws Exception {
		CharacterListPresenter listPresenter = givenAListPresenter();

		when(mockGetCharacterUsecase.execute()).thenReturn(Observable.error(new Exception()));
		listPresenter.askForNewCharacters();

		verify(mockCharacterListView, times(1)).showLightError();
	}

	@Test public void testThatPresenterRequestMoreCharacters() throws Exception {
		CharacterListPresenter listPresenter = givenAListPresenter();

		when(mockGetCharacterUsecase.execute()).thenReturn(getFakeObservableCharacterList());
		listPresenter.askForNewCharacters();

		verify(mockGetCharacterUsecase, only()).execute();
	}

	private ArrayList<MarvelCharacter> givenAFakeCharacterList() {
		ArrayList<MarvelCharacter> marvelCharacters = new ArrayList<>();
		marvelCharacters.add(new MarvelCharacter("", -1));
		marvelCharacters.add(new MarvelCharacter("", -1));
		return marvelCharacters;
	}

	private CharacterListPresenter givenAListPresenter() {
		CharacterListPresenter listPresenter = new CharacterListPresenter(mockGetCharacterUsecase);
		listPresenter.attachView(mockCharacterListView);
		return listPresenter;
	}

	private Observable<List<MarvelCharacter>> getFakeObservableCharacterList() {
		List<MarvelCharacter> test = new ArrayList<MarvelCharacter>();
		return Observable.just(test);
	}
}

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import saulmm.avengers.GetCharactersUsecase;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.mvp.presenters.CharacterListPresenter;
import saulmm.avengers.mvp.views.CharacterListView;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

	//@Test public void testThatPresenterRequestCharacters() throws Exception {
	//	CharacterListPresenter listPresenter = givenAListPresenter();
	//
	//	listPresenter.askForCharacters();
	//
	//	verify(mockGetCharacterUsecase, times(1)).execute();
	//}

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
}

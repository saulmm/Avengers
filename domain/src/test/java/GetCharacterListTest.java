import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import saulmm.avengers.GetCharactersUsecase;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.CharacterRepository;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.only;

public class GetCharacterListTest {
	@Mock CharacterRepository mockRepository;

	@Before public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test public void testThatAConcreteUsecaseImplementsAnUsecase() {
		GetCharactersUsecase charactersUsecase = givenACharactersUsecase();

		assertThat(charactersUsecase, instanceOf(GetCharactersUsecase.class));
	}

	@Test public void testThatCharactersUsecaseIsCalledOnce() throws Exception {
		GetCharactersUsecase charactersUsecase = givenACharactersUsecase();

		charactersUsecase.execute();

		Mockito.verify(mockRepository, only()).getCharacters(0);
	}

	private GetCharactersUsecase givenACharactersUsecase() {
		return new GetCharactersUsecase(mockRepository);
	}

	private Observable<List<MarvelCharacter>> getFakeObservableCharacterList() {
		List<MarvelCharacter> test = new ArrayList<MarvelCharacter>();
		return Observable.just(test);


	}
}

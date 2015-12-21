import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import saulmm.avengers.GetCharactersUsecase;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.CharacterRepository;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.when;
import static saulmm.avengers.GetCharactersUsecase.DEFAULT_CHARACTERS_LIMIT;

public class GetCharactersUsecaseTest {
	@Mock CharacterRepository mockRepository;
	@Mock Scheduler mockUiScheduler;
	@Mock Scheduler mockExecutorScheduler;

	@Before public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test public void testThatAConcreteUsecaseImplementsAnUsecase() {
		GetCharactersUsecase charactersUsecase = givenACharactersUsecase();

		assertThat(charactersUsecase, instanceOf(GetCharactersUsecase.class));
	}

	@Test public void testThatCharactersUsecaseIncrementsOffset() throws Exception {
		GetCharactersUsecase charactersUsecase = givenACharactersUsecase();
		when(mockRepository.getCharacters(anyInt())).thenReturn(getFakeObservableCharacterList());

		charactersUsecase.execute();
		charactersUsecase.execute();
		charactersUsecase.execute();

		assertThat(charactersUsecase.getCurrentOffset(), is(DEFAULT_CHARACTERS_LIMIT * 3));
	}

	@Test public void testThatCharactersUsecaseWithOffsetIsCalledOnce() throws Exception {
		GetCharactersUsecase charactersUsecase = givenACharactersUsecase();
		when(mockRepository.getCharacters(anyInt())).thenReturn(getFakeObservableCharacterList());

		charactersUsecase.execute();

		Mockito.verify(mockRepository, only()).getCharacters(anyInt());
	}

	private GetCharactersUsecase givenACharactersUsecase() {
		return new GetCharactersUsecase(mockRepository, mockUiScheduler, mockExecutorScheduler);
	}

	private Observable<List<MarvelCharacter>> getFakeObservableCharacterList() {
		List<MarvelCharacter> test = new ArrayList<MarvelCharacter>();
		return Observable.just(test);
	}
}

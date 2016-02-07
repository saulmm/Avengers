import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import rx.Observable;
import rx.Scheduler;
import saulmm.avengers.CharacterDetailsUsecase;
import saulmm.avengers.Usecase;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.CharacterRepository;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.internal.verification.VerificationModeFactory.only;

public class GetCharacterDetailsTest {
	private final static int FAKE_CHARACTER_ID = 69;
	@Mock CharacterRepository mRepository;
	@Mock Scheduler mockScheduler;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test public void testThatDetailUsecaseIsCalledOnce() throws Exception {
		CharacterDetailsUsecase detailsUsecase = givenACharacterUsecase();

		Mockito.when(mRepository.getCharacter(FAKE_CHARACTER_ID)).thenReturn(getFakeCharacterObservable());
		detailsUsecase.execute();

		Mockito.verify(mRepository, only()).getCharacter(FAKE_CHARACTER_ID);
	}

	@Test public void testThatAConcreteUsecaseImplementsAnUsecase() throws Exception {
		CharacterDetailsUsecase detailsUsecase = givenACharacterUsecase();
		
		assertThat(detailsUsecase, instanceOf(Usecase.class));
	}

	private CharacterDetailsUsecase givenACharacterUsecase() {
		return new CharacterDetailsUsecase(FAKE_CHARACTER_ID, mRepository);
	}

	private Observable<MarvelCharacter> getFakeCharacterObservable() {
		return Observable.just(new MarvelCharacter("", -1));
	}
}

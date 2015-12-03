import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import saulmm.avengers.CharacterDetailsUsecase;
import saulmm.avengers.Usecase;
import saulmm.avengers.repository.CharacterRepository;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.internal.verification.VerificationModeFactory.only;

public class GetCharacterDetailsTest {
	private final static int FAKE_CHARACTER_ID = 69;

	CharacterDetailsUsecase mGetCharacterDetailUsecase;
	@Mock CharacterRepository mRepository;


	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);
		mGetCharacterDetailUsecase = new CharacterDetailsUsecase(
			FAKE_CHARACTER_ID, mRepository);
	}

	@Test public void testThatDetailUsecaseIsCalledOnce() throws Exception {
		mGetCharacterDetailUsecase.execute();

		Mockito.verify(mRepository, only()).getCharacter(FAKE_CHARACTER_ID);
	}

	@Test public void testThatAConcreteUsecaseImplementsAnUsecase() throws Exception {
		assertThat(mGetCharacterDetailUsecase, instanceOf(Usecase.class));
	}
}

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import saulmm.avengers.GetCharacterInformationUsecase;
import saulmm.avengers.Usecase;
import saulmm.avengers.repository.Repository;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.internal.verification.VerificationModeFactory.only;

public class GetCharacterDetailsTest {
	private final static int FAKE_CHARACTER_ID = 69;

	GetCharacterInformationUsecase mGetCharacterDetailUsecase;
	@Mock Repository mRepository;


	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);
		mGetCharacterDetailUsecase = new GetCharacterInformationUsecase(
			FAKE_CHARACTER_ID, mRepository);
	}

	@Test public void testThatDetailUsecaseIsCalledOnce() {
		mGetCharacterDetailUsecase.execute();

		Mockito.verify(mRepository, only()).getCharacter(FAKE_CHARACTER_ID);
	}

	@Test public void testThatAConcreteUsecaseImplementsAnUsecase() {
		assertThat(mGetCharacterDetailUsecase, instanceOf(Usecase.class));
	}
}

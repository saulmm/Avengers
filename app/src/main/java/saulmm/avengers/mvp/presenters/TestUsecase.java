package saulmm.avengers.mvp.presenters;

import saulmm.avengers.model.rest.RestRepository;

public class TestUsecase {

	private RestRepository restRepository = new RestRepository();

	public void execute() {
		restRepository.test();
	}
}

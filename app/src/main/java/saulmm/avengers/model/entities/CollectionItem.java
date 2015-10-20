package saulmm.avengers.model.entities;

public class CollectionItem {
	public enum CollectionType {
		COMIC, SERIE, EVENT, HISTORY
	}


	private String title;

	private String shortDescription;

	private String imageUrl;

	public String getTitle() {
		return title;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}

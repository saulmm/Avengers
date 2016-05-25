package saulmm.avengers.rest.entities;

public class RestCollectionItem {
	protected int id;
	protected String title;
	protected String description;
	protected String resourceURI;
	protected RestThumbnail thumbnail;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getResourceURI() {
		return resourceURI;
	}

	public RestThumbnail getThumbnail() {
		return thumbnail;
	}

	public String getDescription() {
		return description;
	}
}

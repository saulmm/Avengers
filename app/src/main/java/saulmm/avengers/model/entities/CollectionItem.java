package saulmm.avengers.model.entities;

public class CollectionItem {
	public final static String COMIC 		= "comics";
	public final static String SERIES	  	= "series";
	public final static String STORY 		= "stories";
	public final static String EVENT 		= "events";

	protected int id;
	protected String title;
	protected String description;
	protected String resourceURI;
	protected Thumbnail thumbnail;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getResourceURI() {
		return resourceURI;
	}

	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	public String getDescription() {
		return description;
	}
}

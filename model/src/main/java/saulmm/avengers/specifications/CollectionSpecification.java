package saulmm.avengers.specifications;

import saulmm.avengers.entities.CollectionItem;

public class CollectionSpecification implements Specification {
    private CollectionItem.Type mType;
    private int mCharacterId;

    public CollectionSpecification(CollectionItem.Type mType, int mCharacterId) {
        this.mType = mType;
        this.mCharacterId = mCharacterId;
    }

    public CollectionItem.Type getType() {
        return mType;
    }

    public int getCharacterId() {
        return mCharacterId;
    }
}

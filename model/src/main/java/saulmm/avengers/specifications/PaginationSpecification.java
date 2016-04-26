package saulmm.avengers.specifications;


public class PaginationSpecification implements Specification {
    private int mOffset;
    private int mLimit;

    public PaginationSpecification(int mOffset, int mLimit) {
        this.mOffset = mOffset;
        this.mLimit = mLimit;
    }

    public void setOffset(int mOffset) {
        this.mOffset = mOffset;
    }

    public void setLimit(int mLimit) {
        this.mLimit = mLimit;
    }

    public void increaseOffset() {
        mOffset += mLimit;
    }

    public void decreaseOffset() {
        mOffset -= mLimit;
    }

    public int getOffset() {
        return mOffset;
    }

    public int getLimit() {
        return mLimit;
    }
}

package saulmm.avengers.specifications;


import saulmm.avengers.specifications.Specification;

public class IdSpecification implements Specification {
    private int id;

    public IdSpecification(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

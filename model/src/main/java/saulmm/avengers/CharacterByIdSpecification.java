package saulmm.avengers;


public class CharacterByIdSpecification implements Specification {
    private int id;

    public CharacterByIdSpecification(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

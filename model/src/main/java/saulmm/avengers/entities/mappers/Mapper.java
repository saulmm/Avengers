package saulmm.avengers.entities.mappers;

public interface Mapper<M, T> {
    M map(T parseObject);
}

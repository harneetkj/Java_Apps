package ca.jrvs.apps.twitter.example.dao;

public interface CrdDao<T, ID> {
    T save(T entity);
    T findById(T Entity);
    T deleteById(ID id);


}


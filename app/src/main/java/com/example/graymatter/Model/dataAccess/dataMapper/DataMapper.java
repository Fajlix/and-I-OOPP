package com.example.graymatter.Model.dataAccess.dataMapper;

import java.util.List;
import java.util.Optional;

public interface DataMapper<T> {
    //fix accessors
    Optional<T> find(int friendID);
    void insert(T obj);
    void update(T obj) throws DataMapperException;
    void delete(T obj) throws DataMapperException;

    //unsure whether this belongs in interface or not
    List<T> get();
}

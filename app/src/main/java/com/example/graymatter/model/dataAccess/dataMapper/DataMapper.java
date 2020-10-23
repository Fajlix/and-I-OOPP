package com.example.graymatter.model.dataAccess.dataMapper;

import java.util.List;
import java.util.Optional;

/**
 * @author Aline
 * Interface defined by DataMapper design pattern, one of Martin Fowler´s P of EAA patterns. Intended to limit the handling of database contacts to one implementation layer only.
 * Maps domain model to database.
 * All application database contact should be handled in a DataMapper implementation.
 * @see <a href="https://www.martinfowler.com/eaaCatalog/dataMapper.html">Martin Fowler´s definition of a DataMapper</a>
 * @param <T> class to map in database.
 */
public interface DataMapper<T> {

    /**
     * Finds Optional of T in database based on argument ID, or if no database T matching ID, empty Optional.
     * @param ID matching the ID of an existing database entry.
     * @return Optional of matching T, or empty Optional
     */
    Optional<T> find(int ID);

    /**
     * Inserts a T representation into the database. Will not insert obj if a T with matching ID is already in database.
     * @param obj to insert.
     * @throws DataMapperException if player userID matches the userID of a player already in database.
     */
    void insert(T obj) throws DataMapperException;

    /**
     * Updates fields of a T already represented in database. Identifies the T from the ID of argument obj.
     * @param obj with an ID matching ID of T representation already in database.
     */
    void update(T obj) throws DataMapperException;

    /**
     * Removes a T representation from database completely. Action can not be reversed.
     * @param obj to delete. Matching userID selects entry for removal. Other attributes can differ, database entry will still be deleted.
     * @throws DataMapperException if no Player with matching userID can be found.
     */
    void delete(T obj) throws DataMapperException;

    //TODO possibly return Iterator

    /**
     *
     * @return a list of all T:s represented in database.
     */
    List<T> get();
}

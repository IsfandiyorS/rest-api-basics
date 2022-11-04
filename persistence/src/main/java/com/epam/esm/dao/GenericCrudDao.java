package com.epam.esm.dao;

import com.epam.esm.exceptions.ActionFallDaoException;
import com.epam.esm.exceptions.ObjectNotFoundDaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// fixme change all of them to another
/**
 * Interface {@code GenericCrudDao} describes CRUD operations for working with database tables.
 *
 * @param <T> the type parameter
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public interface GenericCrudDao<T> {

    /**
     * Method for saving an entity to a table.
     *
     * @param entity entity object to save
     */
    Long save(T entity);

    /**
     * Method for updating an entity in a table if updating required.
     *
     * @param item entity object to update
     */
    default Long update(Map<String, String> item) throws ActionFallDaoException {return 0L;}
    /**
     * Method for removing an entity from a table by ID.
     *
     * @param id ID of entity to remove
     */
    Long deleteById(Long id) throws ActionFallDaoException;

    /**
     * Method for getting an entity object from a table by ID.
     *
     * @param id ID of entity to get
     * @return Entity object from table
     */
    Optional<T> findById(Long id);

    /**
     * Method for getting all entities from a table.
     *
     * @return List of all entities in the table
     */
    List<T> getAll();

    /**
     * Method for getting a list of tags from a table with a specific name.
     *
     * @param name name of objects to get
     * @return Optional object of entit from table
     */
    Optional<T> findByName(String name);

    /**
     * Method for filtering elements.
     *
     * @param filterMap contains fields will be sanded to filter or sort
     * @return List of objects by filtering parameters
     */
    List<T> doFilter(Map<String, String> filterMap) throws ObjectNotFoundDaoException;
}

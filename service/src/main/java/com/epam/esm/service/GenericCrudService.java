package com.epam.esm.service;

import com.epam.esm.criteria.Criteria;
import com.epam.esm.entity.BaseAbstractDomain;
import com.epam.esm.exceptions.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Interface {@code GenericCrudService} describes CRUD operations for working with objects.
 *
 * @param <T> the type parameter
 * @param <ID> the ID of object
 * @param <C> Objects filter or sort class inherited from Criteria
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public interface GenericCrudService<T extends BaseAbstractDomain, ID extends Serializable, C extends Criteria> {

    /**
     * Method for getting object by its ID
     *
     * @param id ID of object which will send from url
     * @return <T> type of object returns from database
     */
    T getById(ID id) throws ObjectNotFoundException;

    /**
     * Method for getting all object
     *
     * @return List of required object
     */
    List<T> getAll();

    /**
     * Method for creating object by its specific fields
     *
     * @param createEntity request parameters from url
     * @return Long as an ID of object
     */
    Long create(T createEntity) throws AlreadyExistException, ValidationException, ActionFallDaoException;


    /**
     * Method for updating object or entity by its parameters as well as this method defined with
     * default access modifier because this method will be overridden if required
     *
     * @param updateEntity request parameters from url
     */
    default void update(T updateEntity) throws ValidationException, ObjectNotFoundException, AlreadyExistException, ActionFallDaoException {}

    /**
     * Method for deleting object or entity by its ID.
     *
     * @param id request parameters from url
     * @return Long as a response
     */
    Long delete(ID id) throws ObjectNotFoundException, ActionFallDaoException;

    /**
     * Method for getting a list of objects by specific parameters.
     *
     * @param criteria request parameters from url
     * @return List of required object
     */
    List<T> doFilter(C criteria) throws ObjectNotFoundDaoException;

    /**
     * Method for validating optional object if it exists or not in database.
     *
     * @param entity object returned from database
     */
    void validate(Optional<T> entity) throws ObjectNotFoundException;

}
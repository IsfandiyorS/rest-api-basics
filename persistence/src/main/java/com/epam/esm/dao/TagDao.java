package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ObjectNotFoundDaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface {@code TagDao} describes abstract behavior for working with tags table in database.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public interface TagDao extends GenericCrudDao<Tag> {

    /**
     * Method for getting a list of tags from a table with a specific name.
     *
     * @param name name of tag to get
     * @return Optional object of tag from table
     */
    Optional<Tag> findByName(String name);

    /**
     * Method for deleting a list of tags by gift certificate ID from database.
     *
     * @param giftCertificateId gift certificate id which required to send tags
     * @return List of tags by gift certificate id
     */
    List<Tag> getAttachedTagsWithGiftCertificateId(Long giftCertificateId);

    /**
     * Method for deleting a list of tags by gift certificate ID from database.
     *
     * @param giftCertificateId gift certificate id to check availability in related tag
     * @param tagId tag id to check availability in related tag
     */
    boolean checkForAvailabilityOfTagIdInRelatedTable(Long tagId, Long giftCertificateId);
}

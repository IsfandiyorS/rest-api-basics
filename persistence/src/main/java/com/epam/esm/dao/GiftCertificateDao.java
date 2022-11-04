package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ActionFallDaoException;
import com.epam.esm.exceptions.ObjectNotFoundDaoException;

import java.util.List;
import java.util.Map;

/**
 * Interface {@code GiftCertificateDao} describes abstract behavior for
 * working with gift_certificate table in database.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public interface GiftCertificateDao extends GenericCrudDao<GiftCertificate> {

    /**
     * Method for adding a list of tags by gift certificate ID to database.
     *
     * @param giftCertificateId ID of gift certificate to update
     * @param tagId ID of tag to attach to gift certificate
     */
    Long attachTagToGiftCertificate(Long tagId, Long giftCertificateId) throws ActionFallDaoException;

    /**
     * Method for getting a list of tags by gift certificate ID from database.
     *
     * @param giftCertificateId ID of gift certificate
     * @return List of tags from gift certificate
     */
    List<Tag> getAttachedTagsWithGiftCertificateId(Long giftCertificateId);

    /**
     * Method for deleting a list of tags by gift certificate ID from database.
     *
     * @param certificateId ID of gift certificate to update
     * @param tags          List of tags to delete
     */
    Long deleteTagsAssociation(Long certificateId, List<Tag> tags) throws ObjectNotFoundDaoException, ActionFallDaoException;

}

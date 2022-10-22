package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.slf4j.ILoggerFactory;

import java.util.Optional;

public interface TagDao extends GenericCrudDao<Tag> {
    Optional<Tag> findByName(final String name);

    void attachTagToGiftCertificate(final Long tagId, Long giftCertificateId);
}

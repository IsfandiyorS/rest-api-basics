package com.epam.esm.dao;

import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends GenericCrudDao<Tag> {
    Optional<Tag> findByName(final String name);

    List<Tag> getAttachedTagsWithGiftCertificateId(final Long giftCertificateId);

    void attachTagToGiftCertificate(final Long tagId, final Long giftCertificateId);

    boolean checkForAvailabilityOfTagIdInRelatedTable(Long tagId, Long giftCertificateId);
}

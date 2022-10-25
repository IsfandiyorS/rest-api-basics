package com.epam.esm.dao;

import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;

import java.util.List;
import java.util.Optional;

public interface TagDao extends GenericCrudDao<TagDto, TagCreateDto> {
    Optional<TagDto> findByName(final String name);

    List<TagDto> getAttachedTagsWithGiftCertificateId(final Long giftCertificateId);

    void attachTagToGiftCertificate(final Long tagId, final Long giftCertificateId);

    boolean checkForAvailabilityOfTagIdInRelatedTable(Long tagId, Long giftCertificateId);
}

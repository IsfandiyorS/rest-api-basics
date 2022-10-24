package com.epam.esm.dao;

import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao extends GenericCrudDao<GiftCertificateDto, GiftCertificateCreateDto> {
    void attachTagToGiftCertificate(Long tagId, Long giftCertificateId);

    List<TagDto> getAttachedTagsWithGiftCertificateId(Long giftCertificateId);

    void deleteTagsAssociation(Long id, List<Long> tags);

}

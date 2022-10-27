package com.epam.esm.dao;

import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends GenericCrudDao<GiftCertificate> {
    void attachTagToGiftCertificate(Long tagId, Long giftCertificateId);

    List<Tag> getAttachedTagsWithGiftCertificateId(Long giftCertificateId);

    void deleteTagsAssociation(Long id, List<Long> tags);

    List<GiftCertificate> getGiftCertificateByFilteringParameters(Map<String, String> criteria);
}

package com.epam.esm.service;

import com.epam.esm.dto.impl.*;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService extends GenericCrudService<GiftCertificate, GiftCertificateDto, GiftCertificateCreateDto, GiftCertificateUpdateDto, Long> {
    List<TagDto> getAttachedTagsWithGiftCertificateId(Long id);

    Boolean attachTagsToGiftCertificate(Long giftCertificateId, List<TagCreateDto> tags);

    Boolean deleteAssociatedTags(Long id, List<TagCreateDto> tags);

}

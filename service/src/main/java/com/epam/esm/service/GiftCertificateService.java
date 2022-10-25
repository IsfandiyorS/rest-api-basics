package com.epam.esm.service;

import com.epam.esm.criteria.impl.GiftCertificateCriteria;
import com.epam.esm.dto.impl.*;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GiftCertificateService extends GenericCrudService<GiftCertificate, GiftCertificateDto, GiftCertificateCreateDto, GiftCertificateUpdateDto, Long> {
    ResponseEntity<List<TagDto>> getAttachedTagsWithGiftCertificateId(Long id);

    ResponseEntity<Boolean> attachTagsToGiftCertificate(Long giftCertificateId, List<TagCreateDto> tags);

    ResponseEntity<Boolean> deleteAssociatedTags(Long id, List<TagCreateDto> tags);

    List<GiftCertificateDto> doFilter(GiftCertificateCriteria criteria);
}

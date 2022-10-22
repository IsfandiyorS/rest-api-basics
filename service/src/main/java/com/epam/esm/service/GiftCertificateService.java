package com.epam.esm.service;

import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.GiftCertificateUpdateDto;
import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateService extends GenericCrudService<GiftCertificate, GiftCertificateDto, GiftCertificateCreateDto, GiftCertificateUpdateDto, Long> {

}

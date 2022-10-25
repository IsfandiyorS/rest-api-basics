package com.epam.esm.converter.impl;

import com.epam.esm.converter.GenericConverter;
import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateConverter implements GenericConverter<GiftCertificate, GiftCertificateCreateDto, GiftCertificateDto> {

    private final TagConverter tagConverter;

    public GiftCertificateConverter(TagConverter tagConverter) {
        this.tagConverter = tagConverter;
    }

    @Override
    public GiftCertificate convertCreatedDtoToEntity(GiftCertificateCreateDto createdDto) {



        return null;
    }

    @Override
    public GiftCertificateDto convertEntityToDto(GiftCertificate entity) {
        return null;
    }
}

package com.epam.esm.mapper;

import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.GiftCertificateUpdateDto;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateConverter implements GenericConverter<GiftCertificate, GiftCertificateDto, GiftCertificateCreateDto, GiftCertificateUpdateDto>{

    @Override
    public GiftCertificateDto convertObjectToDto(GiftCertificate obj) {
        GiftCertificateDto dto=new GiftCertificateDto();
        dto.setId(obj.getId());
        dto.setPrice(obj.getPrice());
        dto.setName(obj.getName());
        dto.setDescription(obj.getDescription());
        dto.setDuration(obj.getDuration());
        return dto;
    }

    @Override
    public GiftCertificate convertDtoToObject(GiftCertificateCreateDto createDto) {
        GiftCertificate giftCertificate=new GiftCertificate();

        giftCertificate.setPrice(createDto.getPrice());
        giftCertificate.setName(createDto.getName());
        giftCertificate.setDescription(createDto.getDescription());
        giftCertificate.setDuration(createDto.getDuration());

        return giftCertificate;
    }

    @Override
    public GiftCertificate convertUpdateDtoToObject(GiftCertificateUpdateDto updateDto) {
        return null;
    }
}

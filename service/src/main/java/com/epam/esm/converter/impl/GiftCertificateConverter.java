package com.epam.esm.converter.impl;

import com.epam.esm.converter.GenericConverter;
import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateConverter implements GenericConverter<GiftCertificate, GiftCertificateCreateDto, GiftCertificateDto> {

    private final TagConverter tagConverter;

    @Autowired
    public GiftCertificateConverter(TagConverter tagConverter) {
        this.tagConverter = tagConverter;
    }


    @Override
    public GiftCertificate convertCreatedDtoToEntity(GiftCertificateCreateDto createdDto) {
        GiftCertificate giftCertificate=new GiftCertificate();
        giftCertificate.setName(createdDto.getName());
        giftCertificate.setDescription(createdDto.getDescription());
        giftCertificate.setPrice(createdDto.getPrice());
        giftCertificate.setDuration(createdDto.getDuration());
        if (createdDto.getTagCreateDtoList()!=null){
            List<TagCreateDto> tagCreateDtoList = createdDto.getTagCreateDtoList();
            List<Tag> tagList=new ArrayList<>();
            tagCreateDtoList.forEach(dto ->  tagList.add(tagConverter.convertCreatedDtoToEntity(dto)));
            giftCertificate.setTagList(tagList);
        }
        return giftCertificate;
    }

    @Override
    public GiftCertificateDto convertEntityToDto(GiftCertificate entity) {
        GiftCertificateDto dto=new GiftCertificateDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreateDate(entity.getCreateDate());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        if (entity.getTagList()!=null){
            List<Tag> tagList = entity.getTagList();
            List<TagDto> tagDtoList = new ArrayList<>();
            tagList.forEach(entityDto -> tagDtoList.add(tagConverter.convertEntityToDto(entityDto)));
            dto.setTagDtoList(tagDtoList);
        }
        return dto;
    }
}

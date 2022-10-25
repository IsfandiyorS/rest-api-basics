package com.epam.esm.converter.impl;

import com.epam.esm.converter.GenericConverter;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements GenericConverter<Tag, TagCreateDto, TagDto> {
    @Override
    public Tag convertCreatedDtoToEntity(TagCreateDto createdDto) {
        return new Tag(createdDto.getName());
    }

    @Override
    public TagDto convertEntityToDto(Tag entity) {
        return new TagDto(entity.getId(), entity.getName());
    }
}

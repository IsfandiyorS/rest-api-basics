package com.epam.esm.mapper;

import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.dto.impl.TagUpdateDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements GenericConverter<Tag, TagDto, TagCreateDto, TagUpdateDto> {

    @Override
    public TagDto convertObjectToDto(Tag obj) {
        return new TagDto(obj.getId(), obj.getName());
    }

    @Override
    public Tag convertDtoToObject(TagCreateDto createDto) {
        return new Tag(createDto.getName());
    }

    @Override
    public Tag convertUpdateDtoToObject(TagUpdateDto updateDto) {
        return new Tag(updateDto.getName());
    }
}

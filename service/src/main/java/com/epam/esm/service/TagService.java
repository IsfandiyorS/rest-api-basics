package com.epam.esm.service;

import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.dto.impl.TagUpdateDto;
import com.epam.esm.entity.Tag;

public interface TagService extends GenericCrudService<Tag, TagDto, TagCreateDto, TagUpdateDto, Long> {
}

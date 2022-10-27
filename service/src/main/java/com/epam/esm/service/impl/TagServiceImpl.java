package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.TagConverter;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.dto.impl.TagUpdateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.service.AbstractCrudService;
import com.epam.esm.service.TagService;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class TagServiceImpl extends AbstractCrudService<Tag, TagDto, TagCreateDto, TagUpdateDto, TagDaoImpl> implements TagService {

    private final TagDao tagDao;
    private final TagValidator validator;
    private static final TagConverter tagConverter = new TagConverter();

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagValidator validator) {
        this.tagDao = tagDao;
        this.validator = validator;
    }

    @Override
    public TagDto get(Long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        validate(optionalTag, id, "Tag");
        TagDto tagDto = new TagDto();
        return tagConverter.convertEntityToDto(optionalTag.get());
    }

    @Override
    public List<TagDto> getAll() {
        List<Tag> tagList = tagDao.getAll();
        List<TagDto> tagDtoList = new ArrayList<>();
        tagList.forEach(entity -> tagDtoList.add(tagConverter.convertEntityToDto(entity)));
        return tagDtoList;
    }

    @Override
    public GenericDto create(TagCreateDto dto) {

        validator.isCreateDtoValid(dto);

        Optional<Tag> optionalTag = tagDao.findByName(dto.getName());

        if (optionalTag.isPresent()) {
            throw new AlreadyExistException(format(ErrorCodes.OBJECT_ALREADY_EXIST.message, "Tag", "name"));
        }

        Tag tag = tagConverter.convertCreatedDtoToEntity(dto);

        return new GenericDto(tagDao.save(tag));
    }

    @Override
    public Boolean delete(Long id) {

        Optional<Tag> optionalTag = tagDao.findById(id);
        validate(optionalTag, id, "Tag");
        tagDao.removeById(id);
        return true;
    }

    @Override
    public void validate(Optional<Tag> entity, Long id, String entityName) {
        if (entity.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, entityName, id));
        }
    }
}

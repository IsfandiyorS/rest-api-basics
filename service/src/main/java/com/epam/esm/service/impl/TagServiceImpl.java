package com.epam.esm.service.impl;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class TagServiceImpl extends AbstractCrudService<Tag, TagDto, TagCreateDto, TagUpdateDto, TagDaoImpl> implements TagService {

    private final TagDaoImpl tagDao;
    private final TagValidator validator;

    @Autowired
    public TagServiceImpl(TagDaoImpl tagDao, TagValidator validator) {
        this.tagDao = tagDao;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<TagDto> get(Long id) {
        Optional<TagDto> optionalTag = tagDao.findById(id);
        if (optionalTag.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Tag", id));
        }

        return ResponseEntity.ok(optionalTag.get());
    }

    @Override
    public ResponseEntity<List<TagDto>> getAll() {
        return ResponseEntity.ok(tagDao.getAll());
    }

    @Override
    public ResponseEntity<GenericDto> create(TagCreateDto dto) {

        validator.isCreateDtoValid(dto);

        Optional<TagDto> optionalTag = tagDao.findByName(dto.getName());

        if (optionalTag.isPresent()) {
            throw new AlreadyExistException(format(ErrorCodes.OBJECT_ALREADY_EXIST.message, "Tag", "name"));
        }

        return ResponseEntity.ok(new GenericDto(tagDao.save(dto)));

    }

    @Override
    public ResponseEntity<Boolean> delete(Long id) {

        Optional<TagDto> optionalTag = tagDao.findById(id);

        if (optionalTag.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Tag", id));
        }

        tagDao.removeById(id);
        return ResponseEntity.ok(true);
    }
}

package com.epam.esm.service.impl;

import com.epam.esm.criteria.TagCriteria;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.*;
import com.epam.esm.service.TagService;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.constant.FilterParameters.*;
import static java.lang.String.format;


/**
 * Class {@code TagServiceImpl} is implementation of interface {@link TagService}
 * and intended to work with {@link Tag} objects.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag getById(Long id) throws ObjectNotFoundException {
        Optional<Tag> optionalTag = tagDao.findById(id);
        validate(optionalTag);
        return optionalTag.get();
    }

    @Override
    public List<Tag> getAll() {
        return tagDao.getAll();
    }

    @Override
    public Long create(Tag createEntity) throws AlreadyExistException, ValidationException {

        TagValidator.isCreateEntityValid(createEntity);

        Optional<Tag> optionalTag = tagDao.findByName(createEntity.getName());

        if (optionalTag.isPresent()) {
            throw new AlreadyExistException(format(ErrorCodes.OBJECT_ALREADY_EXIST.message));
        }

        return tagDao.save(createEntity);
    }

    @Override
    public Long delete(Long id) throws ObjectNotFoundException, ActionFallDaoException {
        getById(id);
        return tagDao.deleteById(id);
    }

    @Override
    public List<Tag> doFilter(TagCriteria criteria) throws ObjectNotFoundDaoException {
        Map<String, String> tagFilterMap = new HashMap<>();
        tagFilterMap.put(TAG_NAME, criteria.getTagName());
        tagFilterMap.put(SORT_BY_TAG_NAME, criteria.getSortByTagName());
        tagFilterMap.put(PART_OF_TAG_NAME, criteria.getPartOfTagName());
        return tagDao.doFilter(tagFilterMap);
    }

    @Override
    public void validate(Optional<Tag> entity) throws ObjectNotFoundException {
        if (entity.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message));
        }
    }

}

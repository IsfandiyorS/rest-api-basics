package com.epam.esm.service.impl;

import com.epam.esm.criteria.GiftCertificateCriteria;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.*;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.GiftCertificateValidator;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.epam.esm.constant.ActionMassages.FIELD;
import static com.epam.esm.constant.ActionMassages.WRITTEN;
import static com.epam.esm.constant.FilterParameters.*;
import static java.lang.String.format;

/**
 * Class {@code GiftCertificateServiceImpl} is implementation of interface {@link GiftCertificateService}
 * and intended to work with {@link GiftCertificate} objects.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificationDao;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificationDao, TagDaoImpl tagDao) {
        this.giftCertificationDao = giftCertificationDao;
        this.tagDao = tagDao;
    }

    @Override
    public GiftCertificate getById(Long id) throws ObjectNotFoundException {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificationDao.findById(id);
        validate(optionalGiftCertificate);
        return optionalGiftCertificate.get();
    }

    @Override
    public List<GiftCertificate> getAll() {
        return giftCertificationDao.getAll();
    }

    @Override
    public Long create(GiftCertificate createEntity) throws AlreadyExistException, ValidationException, ActionFallDaoException {
        GiftCertificateValidator.isCreateEntityValid(createEntity);

        Optional<GiftCertificate> certificateByName = giftCertificationDao.findByName(createEntity.getName());

        if (certificateByName.isPresent()) {
            throw new AlreadyExistException(format(ErrorCodes.OBJECT_ALREADY_EXIST.message));
        }

        // fixme change this gift certificate id to the last line of method
        Long giftCertificationId = giftCertificationDao.save(createEntity);

        if (!(createEntity.getTagList() == null || createEntity.getTagList().isEmpty())) {
            List<Tag> createdTagList = createEntity.getTagList();
            List<Long> tagIdList = new ArrayList<>();
            for (Tag tag : createdTagList) {
                Optional<Tag> optionalTag = tagDao.findByName(tag.getName());

                if (optionalTag.isEmpty()) {
                    tagIdList.add(tagDao.save(tag));
                } else {
                    tagIdList.add(optionalTag.get().getId());
                }
            }
            for (Long tagId : tagIdList) {
                giftCertificationDao.attachTagToGiftCertificate(tagId, giftCertificationId);
            }
        }

        return giftCertificationId;
    }

    @Override
    public void update(GiftCertificate updateEntity) throws ValidationException, ObjectNotFoundException, AlreadyExistException, ActionFallDaoException {

        if (updateEntity.getId() == null) {
            throw new ValidationException(format(ErrorCodes.OBJECT_ID_REQUIRED.message));
        }

        getById(updateEntity.getId());

        Map<String, String> updateFieldsMap = new HashMap<>();
        if (!GiftCertificateValidator.isUpdateEntityValid(updateEntity, updateFieldsMap)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_SHOULD_BE.message, FIELD, WRITTEN));
        }
        giftCertificationDao.update(updateFieldsMap);
        if (updateEntity.getTagList() != null) {
            List<Tag> allCreatedTags = tagDao.getAttachedTagsWithGiftCertificateId(updateEntity.getId());
            checkTagsForAvailabilityAndSave(updateEntity.getTagList(), allCreatedTags, updateEntity.getId());
        }
    }

    @Override
    public Long delete(Long id) throws ObjectNotFoundException, ActionFallDaoException {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificationDao.findById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message));
        }
        return giftCertificationDao.deleteById(id);
    }

    @Override
    public List<GiftCertificate> doFilter(GiftCertificateCriteria criteria) throws ObjectNotFoundDaoException {
        Map<String, String> map = new HashMap<>();
        map.put(NAME, criteria.getName());
        map.put(TAG_NAME, criteria.getTagName());
        map.put(PART_OF_NAME, criteria.getPartOfName());
        map.put(PART_OF_DESCRIPTION, criteria.getPartOfDescription());
        map.put(PART_OF_TAG_NAME, criteria.getPartOfTagName());
        map.put(SORT_BY_NAME, criteria.getSortByName());
        map.put(SORT_BY_CREATE_DATE, criteria.getSortByCreateDate());
        map.put(SORT_BY_TAG_NAME, criteria.getSortByTagName());

        return giftCertificationDao.doFilter(map);
    }

    @Override
    public List<Tag> getAttachedTagsWithGiftCertificateId(Long id) throws ObjectNotFoundException {
        getById(id);
        return giftCertificationDao.getAttachedTagsWithGiftCertificateId(id);
    }

    // fixme this should resolve and check if these tags deleted
    @Override
    public void attachTagsToGiftCertificate(Long giftCertificateId, List<Tag> tagList) throws AlreadyExistException, ValidationException, ObjectNotFoundException, ActionFallDaoException {
        getById(giftCertificateId);
        TagValidator.validateListOfTags(tagList);
        List<Tag> alreadyExistedTags = tagDao.getAttachedTagsWithGiftCertificateId(giftCertificateId);
        checkTagsForAvailabilityAndSave(tagList, alreadyExistedTags, giftCertificateId);
    }

    @Override
    public Long deleteAssociatedTags(Long id, List<Tag> tags) throws ObjectNotFoundException, ValidationException, ActionFallDaoException {
        Optional<GiftCertificate> optionalGiftCertificateDto = giftCertificationDao.findById(id);
        if (optionalGiftCertificateDto.isEmpty()) {
            throw new ObjectNotFoundException(ErrorCodes.OBJECT_NOT_FOUND_ID.message);
        }
        TagValidator.validateListOfTags(tags);
        Long deleteEntity;
        try {
            deleteEntity = giftCertificationDao.deleteTagsAssociation(id, tags);
        } catch (ObjectNotFoundDaoException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }
        return deleteEntity;
    }

    @Override
    public void validate(Optional<GiftCertificate> entity) throws ObjectNotFoundException {
        if (entity.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message));
        }
    }

    // todo check it to working or not

    private void checkTagsForAvailabilityAndSave(List<Tag> requestTags, List<Tag> allCreatedNewTags, Long giftCertificateId) throws AlreadyExistException, ActionFallDaoException, ObjectNotFoundException {
        if (requestTags == null) {
            return;
        }
        if (allCreatedNewTags == null || allCreatedNewTags.isEmpty()) {
            requestTags.forEach(tagDao::save);
        } else {
            for (Tag request : requestTags) {
                boolean isExist = false;
                for (Tag created : allCreatedNewTags) {
                    if (Objects.equals(request.getName(), created.getName())) {
                        isExist = true;
                        break;
                    }
                }
                if (isExist) {
                    throw new AlreadyExistException(format(ErrorCodes.OBJECT_ALREADY_EXIST.message));
                }
                Optional<Tag> optionalTag = tagDao.findByName(request.getName());
                Long tagId;
//                if (optionalTag.isEmpty()){
//                    tagId = tagDao.save(new Tag(request.getName()));
//                } else {
//                    tagId = optionalTag.get().getId();
//                }
                if (optionalTag.isEmpty()){
                    throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND.message));
                }else {
                    tagId = optionalTag.get().getId();
                }
                giftCertificationDao.attachTagToGiftCertificate(tagId, giftCertificateId);
            }
        }
    }

}

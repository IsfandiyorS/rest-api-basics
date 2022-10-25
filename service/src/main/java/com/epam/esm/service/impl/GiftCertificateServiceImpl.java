package com.epam.esm.service.impl;

import com.epam.esm.criteria.impl.GiftCertificateCriteria;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.dto.impl.*;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.IdRequiredException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.exceptions.ValidationException;
import com.epam.esm.service.AbstractCrudService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static com.epam.esm.criteria.FilterParameters.*;
import static java.lang.String.format;

@Service
public class GiftCertificateServiceImpl extends AbstractCrudService<GiftCertificate, GiftCertificateDto, GiftCertificateCreateDto, GiftCertificateUpdateDto, GiftCertificateDao> implements GiftCertificateService {

    private final GiftCertificateDao giftCertificationDao;
    private final GiftCertificateValidator validator;
    private final TagDaoImpl tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificationDao, GiftCertificateValidator validator, TagDaoImpl tagDao) {
        this.giftCertificationDao = giftCertificationDao;
        this.validator = validator;
        this.tagDao = tagDao;
    }

    @Override
    public ResponseEntity<GiftCertificateDto> get(Long id) {
        Optional<GiftCertificateDto> optional = giftCertificationDao.findById(id);
        if (optional.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Gift", id));
        }
        return ResponseEntity.ok(giftCertificationDao.findById(id).get());
    }

    @Override
    public ResponseEntity<List<GiftCertificateDto>> getAll() {
        // fixme sort by id
        return ResponseEntity.ok(giftCertificationDao.getAll());
    }

    @Override
    public ResponseEntity<GenericDto> create(GiftCertificateCreateDto dto) {
        validator.isCreateDtoValid(dto);

        // fixme I think all of these logics should be in dao.
        // fixme clarify there whether or not unique fields except gift certificate id
        Long giftCertificationId = giftCertificationDao.save(dto);

        if (!(dto.getTagCreateDtoList() == null || dto.getTagCreateDtoList().isEmpty())) {
            List<TagCreateDto> tagCreateDtoList = dto.getTagCreateDtoList();
            List<Long> tagIdList = new ArrayList<>();
            for (TagCreateDto tag : tagCreateDtoList) {
                Optional<TagDto> optionalTag = tagDao.findByName(tag.getName());

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

        return ResponseEntity.ok(new GenericDto(giftCertificationId));
    }

    @Override
    public ResponseEntity<Boolean> update(GiftCertificateUpdateDto dto) {

        // fixme create method for catching exceptions
        if (dto.getId() == null) {
            throw new IdRequiredException(format(ErrorCodes.OBJECT_ID_REQUIRED.message, "Gift Certificate"));
        }

        Optional<GiftCertificateDto> optionalGiftCertificate = giftCertificationDao.findById(dto.getId());

        if (optionalGiftCertificate.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Tag", dto.getId()));
        }

        Map<String, String> updateFieldsMap = new HashMap<>();
        if (!validator.isUpdateDtoValid(dto, updateFieldsMap)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_SHOULD_BE.message, "At least one gift certificate field", "written"));
        }
        giftCertificationDao.update(updateFieldsMap);
        if (dto.getTagList() != null) {
            List<TagDto> allCreatedTags = tagDao.getAttachedTagsWithGiftCertificateId(dto.getId());
            checkTagsForAvailabilityAndSave(dto.getTagList(), allCreatedTags, dto.getId());
        }

        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> delete(Long id) {
        Optional<GiftCertificateDto> optionalGiftCertificate = giftCertificationDao.findById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Tag", id));
        }
        giftCertificationDao.removeById(id);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<List<TagDto>> getAttachedTagsWithGiftCertificateId(Long id) {
        Optional<GiftCertificateDto> optionalGiftCertificateDto = giftCertificationDao.findById(id);
        if (optionalGiftCertificateDto.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Gift Certificate", id));
        }
        return ResponseEntity.ok(giftCertificationDao.getAttachedTagsWithGiftCertificateId(id));
    }

    @Override
    public ResponseEntity<Boolean> attachTagsToGiftCertificate(Long giftCertificateId, List<TagCreateDto> tags) {
        //fixme id checking and create for it new method()
        Optional<GiftCertificateDto> optionalGiftCertificateDto = giftCertificationDao.findById(giftCertificateId);
        if (optionalGiftCertificateDto.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Gift Certificate", giftCertificateId));
        }
        validator.validateListOfTags(tags);
        List<TagDto> tagDto = tagDao.getAll();
        List<TagDto> tagDtoList = new ArrayList<>();
        tags.forEach(dto -> tagDtoList.add(new TagDto(dto.getName())));
        checkTagsForAvailabilityAndSave(tagDtoList, tagDto, giftCertificateId);
//        giftCertificationDao.attachTagsToGiftCertificate(giftCertificateId, tags);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> deleteAssociatedTags(Long id, List<TagCreateDto> tags) {
        Optional<GiftCertificateDto> optionalGiftCertificateDto = giftCertificationDao.findById(id);
        if (optionalGiftCertificateDto.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Gift Certificate", id));
        }
        validator.validateListOfTags(tags);
        giftCertificationDao.deleteTagsAssociation(id, getTagsId(tags, id));
        return ResponseEntity.ok(true);
    }

    @Override
    public List<GiftCertificateDto> doFilter(GiftCertificateCriteria criteria) {
//        Map<String, String> map = new HashMap<>();
//        map.put(NAME, criteria.getSearchByName());
//        map.put(TAG_NAME, criteria.getSearchByTagName());
//        map.put(PART_OF_NAME, criteria.getSearchByPartOfName());
//        map.put(PART_OF_DESCRIPTION, criteria.getSearchByPartOfDescription());
//        map.put(PART_OF_TAG_NAME, criteria.getSearchByPartOfTagName());
//        map.put(SORT_BY_NAME, criteria.getSortByName());
//        map.put(SORT_BY_CREATE_DATE, criteria.getSortByCreatedDate());
//        map.put(SORT_BY_TAG_NAME, criteria.getSortByTagName());
        return giftCertificationDao.getGiftCertificateByFilteringParameters(null);
    }

    private void checkTagsForAvailabilityAndSave(List<TagDto> requestTags, List<TagDto> allCreatedNewTags, Long giftCertificateId) {
        if (requestTags == null) {
            return;
        }

        for (TagDto request : requestTags) {
            boolean isExist = false;
            for (TagDto created : allCreatedNewTags) {
                if (Objects.equals(request.getName(), created.getName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                throw new AlreadyExistException(format(ErrorCodes.OBJECT_ALREADY_EXIST.message, "Tag", "name"));
            }
            Long tagId = tagDao.save(new TagCreateDto(request.getName()));
            tagDao.attachTagToGiftCertificate(tagId, giftCertificateId);
        }
    }

    private List<Long> getTagsId(List<TagCreateDto> tags, Long giftCertificateId) {
        List<Long> tagIdList = new ArrayList<>();
        tags.forEach(tag -> {
            String tagName = tag.getName();
            Optional<TagDto> optionalTagDto = tagDao.findByName(tagName);
            if (optionalTagDto.isEmpty()) {
                throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_BY_FIELD.message, "Tag name", tagName + " name"));
            } else {
                boolean exist = tagDao.checkForAvailabilityOfTagIdInRelatedTable(optionalTagDto.get().getId(), giftCertificateId);
                if (exist) {
                    tagIdList.add(optionalTagDto.get().getId());
                } else {
                    throw new ValidationException(format(ErrorCodes.OBJECT_NOT_FOUND.message, format("Attached Tag by this %s name", optionalTagDto.get().getName())));
                }
            }
        });
        return tagIdList;
    }

    public List<GiftCertificateDto> doFilter(MultiValueMap<String, String> requestParams) {
//        Map<String, String> map = new HashMap<>();
//        map.put(NAME, criteria.getSearchByName());
//        map.put(TAG_NAME, criteria.getSearchByTagName());
//        map.put(PART_OF_NAME, criteria.getSearchByPartOfName());
//        map.put(PART_OF_DESCRIPTION, criteria.getSearchByPartOfDescription());
//        map.put(PART_OF_TAG_NAME, criteria.getSearchByPartOfTagName());
//        map.put(SORT_BY_NAME, criteria.getSortByName());
//        map.put(SORT_BY_CREATE_DATE, criteria.getSortByCreatedDate());
//        map.put(SORT_BY_TAG_NAME, criteria.getSortByTagName());
        Map<String, String> map = new HashMap<>();
        map.put(NAME, getSingleRequestParameter(requestParams, NAME));
        map.put(TAG_NAME, getSingleRequestParameter(requestParams, TAG_NAME));
        map.put(PART_OF_NAME, getSingleRequestParameter(requestParams, PART_OF_NAME));
        map.put(PART_OF_DESCRIPTION, getSingleRequestParameter(requestParams, PART_OF_DESCRIPTION));
        map.put(PART_OF_TAG_NAME, getSingleRequestParameter(requestParams, PART_OF_TAG_NAME));
        map.put(SORT_BY_NAME, getSingleRequestParameter(requestParams, SORT_BY_NAME));
        map.put(SORT_BY_CREATE_DATE, getSingleRequestParameter(requestParams, SORT_BY_CREATE_DATE));
        map.put(SORT_BY_TAG_NAME, getSingleRequestParameter(requestParams, SORT_BY_TAG_NAME));
        return giftCertificationDao.getGiftCertificateByFilteringParameters(map);
    }
    protected String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }
}

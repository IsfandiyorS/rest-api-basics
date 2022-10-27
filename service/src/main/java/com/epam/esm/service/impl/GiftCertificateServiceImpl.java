package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.converter.impl.TagConverter;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.dto.impl.*;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.IdRequiredException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.exceptions.ValidationException;
import com.epam.esm.service.AbstractCrudService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static com.epam.esm.constant.FilterParameters.*;
import static java.lang.String.format;

@Service
public class GiftCertificateServiceImpl extends AbstractCrudService<GiftCertificate, GiftCertificateDto, GiftCertificateCreateDto, GiftCertificateUpdateDto, GiftCertificateDao> implements GiftCertificateService {

    private final GiftCertificateDao giftCertificationDao;
    private final TagDaoImpl tagDao;
    private final TagConverter tagConverter;
    private final GiftCertificateValidator validator;
    private final GiftCertificateConverter giftCertificateConverter;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificationDao, GiftCertificateValidator validator, TagDaoImpl tagDao, GiftCertificateConverter giftCertificateConverter, TagConverter tagConverter) {
        this.giftCertificationDao = giftCertificationDao;
        this.validator = validator;
        this.tagDao = tagDao;
        this.giftCertificateConverter = giftCertificateConverter;
        this.tagConverter = tagConverter;
    }

    @Override
    public GiftCertificateDto get(Long id) {
        Optional<GiftCertificate> optional = giftCertificationDao.findById(id);
        validate(optional, id, "Gift");
        GiftCertificate giftCertificate = giftCertificationDao.findById(id).get();
        return giftCertificateConverter.convertEntityToDto(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> getAll() {
        List<GiftCertificate> giftCertificateList = giftCertificationDao.getAll();
        List<GiftCertificateDto> dtoList = new ArrayList<>();
        giftCertificateList.forEach(dto -> dtoList.add(giftCertificateConverter.convertEntityToDto(dto)));
        return dtoList;
    }

    @Override
    public GenericDto create(GiftCertificateCreateDto dto) {
        validator.isCreateDtoValid(dto);

        // fixme I think all of these logics should be in dao.
        // fixme clarify there whether or not unique fields except gift certificate id
        GiftCertificate giftCertificate = giftCertificateConverter.convertCreatedDtoToEntity(dto);
        Long giftCertificationId = giftCertificationDao.save(giftCertificate);

        if (!(dto.getTagCreateDtoList() == null || dto.getTagCreateDtoList().isEmpty())) {
            List<TagCreateDto> tagCreateDtoList = dto.getTagCreateDtoList();
            List<Long> tagIdList = new ArrayList<>();
            for (TagCreateDto tag : tagCreateDtoList) {
                Optional<Tag> optionalTag = tagDao.findByName(tag.getName());

                if (optionalTag.isEmpty()) {
                    tagIdList.add(tagDao.save(tagConverter.convertCreatedDtoToEntity(tag)));
                } else {
                    tagIdList.add(optionalTag.get().getId());
                }
            }
            for (Long tagId : tagIdList) {
                giftCertificationDao.attachTagToGiftCertificate(tagId, giftCertificationId);
            }
        }

        return new GenericDto(giftCertificationId);
    }

    @Override
    public Boolean update(GiftCertificateUpdateDto dto) {

        if (dto.getId() == null) {
            throw new IdRequiredException(format(ErrorCodes.OBJECT_ID_REQUIRED.message, "Gift Certificate"));
        }

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificationDao.findById(dto.getId());
        get(dto.getId());

        Map<String, String> updateFieldsMap = new HashMap<>();
        if (!validator.isUpdateDtoValid(dto, updateFieldsMap)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_SHOULD_BE.message, "At least one gift certificate field", "written"));
        }
        giftCertificationDao.update(updateFieldsMap);
        if (dto.getTagList() != null) {
            List<Tag> allCreatedTags = tagDao.getAttachedTagsWithGiftCertificateId(dto.getId());
            List<Tag> tagList = new ArrayList<>();
            for (TagCreateDto tagCreateDto : dto.getTagList()) {
                tagList.add(tagConverter.convertCreatedDtoToEntity(tagCreateDto));
            }

            checkTagsForAvailabilityAndSave(tagList, allCreatedTags, dto.getId());
        }

        return true;
    }

    @Override
    public Boolean delete(Long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificationDao.findById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Tag", id));
        }

        giftCertificationDao.removeById(id);
        return true;
    }

    @Override
    public void validate(Optional<GiftCertificate> entity, Long id, String entityName) {
        if (entity.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, entityName, id));
        }
    }

    @Override
    public List<TagDto> getAttachedTagsWithGiftCertificateId(Long id) {
        get(id);
        List<Tag> tagList = giftCertificationDao.getAttachedTagsWithGiftCertificateId(id);
        List<TagDto> tagDtoList = new ArrayList<>();
        tagList.forEach(tag -> tagDtoList.add(tagConverter.convertEntityToDto(tag)));
        return tagDtoList;
    }

    @Override
    public Boolean attachTagsToGiftCertificate(Long giftCertificateId, List<TagCreateDto> tags) {
        validator.validateListOfTags(tags);

        List<Tag> tagDto = tagDao.getAll();
        List<Tag> tagDtoList = new ArrayList<>();
        tags.forEach(dto -> tagDtoList.add(new Tag(dto.getName())));
        checkTagsForAvailabilityAndSave(tagDtoList, tagDto, giftCertificateId);
        return true;
    }

    @Override
    public Boolean deleteAssociatedTags(Long id, List<TagCreateDto> tags) {
        Optional<GiftCertificate> optionalGiftCertificateDto = giftCertificationDao.findById(id);
        if (optionalGiftCertificateDto.isEmpty()) {
            throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_ID.message, "Gift Certificate", id));
        }
        validator.validateListOfTags(tags);
        giftCertificationDao.deleteTagsAssociation(id, getTagsId(tags, id));
        return true;
    }

    private void checkTagsForAvailabilityAndSave(List<Tag> requestTags, List<Tag> allCreatedNewTags, Long giftCertificateId) {
        if (requestTags == null) {
            return;
        }

        for (Tag request : requestTags) {
            boolean isExist = false;
            for (Tag created : allCreatedNewTags) {
                if (Objects.equals(request.getName(), created.getName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                throw new AlreadyExistException(format(ErrorCodes.OBJECT_ALREADY_EXIST.message, "Tag", "name"));
            }
            Long tagId = tagDao.save(new Tag(request.getName()));
            tagDao.attachTagToGiftCertificate(tagId, giftCertificateId);
        }
    }

    private List<Long> getTagsId(List<TagCreateDto> tags, Long giftCertificateId) {
        List<Long> tagIdList = new ArrayList<>();
        tags.forEach(tag -> {
            String tagName = tag.getName();
            Optional<Tag> optionalTag = tagDao.findByName(tagName);
            if (optionalTag.isEmpty()) {
                throw new ObjectNotFoundException(format(ErrorCodes.OBJECT_NOT_FOUND_BY_FIELD.message, "Tag name", tagName + " name"));
            } else {
                boolean exist = tagDao.checkForAvailabilityOfTagIdInRelatedTable(optionalTag.get().getId(), giftCertificateId);
                if (exist) {
                    tagIdList.add(optionalTag.get().getId());
                } else {
                    throw new ValidationException(format(ErrorCodes.OBJECT_NOT_FOUND.message, format("Attached Tag by this %s name", optionalTag.get().getName())));
                }
            }
        });
        return tagIdList;
    }

    public List<GiftCertificateDto> doFilter(MultiValueMap<String, String> requestParams) {
        Map<String, String> map = new HashMap<>();
        map.put(NAME, getSingleRequestParameter(requestParams, NAME));
        map.put(TAG_NAME, getSingleRequestParameter(requestParams, TAG_NAME));
        map.put(PART_OF_NAME, getSingleRequestParameter(requestParams, PART_OF_NAME));
        map.put(PART_OF_DESCRIPTION, getSingleRequestParameter(requestParams, PART_OF_DESCRIPTION));
        map.put(PART_OF_TAG_NAME, getSingleRequestParameter(requestParams, PART_OF_TAG_NAME));
        map.put(SORT_BY_NAME, getSingleRequestParameter(requestParams, SORT_BY_NAME));
        map.put(SORT_BY_CREATE_DATE, getSingleRequestParameter(requestParams, SORT_BY_CREATE_DATE));
        map.put(SORT_BY_TAG_NAME, getSingleRequestParameter(requestParams, SORT_BY_TAG_NAME));

        List<GiftCertificate> giftCertificateList = giftCertificationDao.getGiftCertificateByFilteringParameters(map);
        List<GiftCertificateDto> dtoList = new ArrayList<>();

        giftCertificateList.forEach(dto -> dtoList.add(giftCertificateConverter.convertEntityToDto(dto)));
        return dtoList;
    }

    protected String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }

}

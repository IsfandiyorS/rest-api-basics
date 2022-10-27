package com.epam.esm.validation;

import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateUpdateDto;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.ValidationException;
import com.epam.esm.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.epam.esm.mapper.GiftCertificateColumn.*;
import static java.lang.String.format;

@Component
public class GiftCertificateValidator implements BaseValidator<GiftCertificateCreateDto, GiftCertificateUpdateDto> {

    private static final int NAME_MAX_LENGTH = 50;
    private static final int NAME_MIN_LENGTH = 1;
    private static final int DESCRIPTION_MAX_LENGTH = 200;
    private static final int DESCRIPTION_MIN_LENGTH = 1;
    private static final BigDecimal PRICE_MIN_VALUE = BigDecimal.ONE;
    private static final BigDecimal PRICE_MAX_VALUE = new BigDecimal(Integer.MAX_VALUE);
    private static final int DURATION_MIN_VALUE = 1;
    private final BaseUtils baseUtils;

    private final TagValidator tagValidator;

    @Autowired
    public GiftCertificateValidator(BaseUtils baseUtils, TagValidator tagValidator) {
        this.baseUtils = baseUtils;
        this.tagValidator = tagValidator;
    }

    @Override
    public void isCreateDtoValid(GiftCertificateCreateDto createDto) {
        if (isNameValid(createDto.getName()) && isDescriptionValid(createDto.getDescription()) && isDurationValid(createDto.getDuration())) {
            isPriceValid(createDto.getPrice());
        }
    }

    @Override
    public boolean isUpdateDtoValid(GiftCertificateUpdateDto updateDto, Map<String, String> updateFieldsMap) {
        if (!baseUtils.isEmpty(updateDto.getName())) {
            updateFieldsMap.put(NAME, updateDto.getName());
        }
        if (!baseUtils.isEmpty(updateDto.getDescription())) {
            updateFieldsMap.put(DESCRIPTION, updateDto.getDescription());
        }
        if (!baseUtils.isEmpty(updateDto.getDuration())) {
            updateFieldsMap.put(DURATION, updateDto.getDuration().toString());
        }
        if (!baseUtils.isEmpty(updateDto.getPrice())) {
            updateFieldsMap.put(PRICE, updateDto.getPrice().toString());
        }
        if (!baseUtils.isEmpty(updateDto.getId())) {
            updateFieldsMap.put(ID, updateDto.getId().toString());
        }
        return updateFieldsMap.size()>0 || updateDto.getTagList().size()>0;
    }

    public void validateListOfTags(List<TagCreateDto> tags) {
        if (baseUtils.isEmpty(tags)) {
            return;
        }
        for (TagCreateDto tag : tags) {
            tagValidator.isCreateDtoValid(tag);
        }
    }

    public boolean isNameValid(String name) {
        if (baseUtils.isEmpty(name)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_IS_NULL.message, "Gift Certificate name"));
        }
        if (!baseUtils.isStringLengthValid(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Gift Certificate name", NAME_MIN_LENGTH, NAME_MAX_LENGTH));
        }
        return true;
    }

    public boolean isDescriptionValid(String description) {
        if (baseUtils.isEmpty(description)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_IS_NULL.message, "Gift Certificate description"));
        }
        if (!baseUtils.isStringLengthValid(description, DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Gift Certificate description", DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH));
        }
        return true;
    }

    public boolean isPriceValid(BigDecimal price) {
        if (baseUtils.isEmpty(price)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_IS_NULL.message, "Gift Certificate description"));
        }
        if (!(price.compareTo(PRICE_MIN_VALUE) >= 0 && price.compareTo(PRICE_MAX_VALUE) <= 0)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Gift Certificate price", PRICE_MIN_VALUE, PRICE_MAX_VALUE));
        }
        return true;
    }

    public boolean isDurationValid(int duration) {
        if (duration < DURATION_MIN_VALUE) {
            throw new ValidationException(format(ErrorCodes.OBJECT_SHOULD_BE.message, "Gift Certificate duration", "more than 0"));
        }
        return true;
    }

//    public void validateFilterSortParams(Map<String, String> filterMap, MultiValueMap<String, String> requestParams, String param) {
//        String requestParameter = getSingleRequestParameter(requestParams, param);
//        if (requestParameter!=null && (!requestParameter.equalsIgnoreCase("asc") || !requestParameter.equalsIgnoreCase("desc"))){
//            throw new ObjectNotFoundDaoException(format(ErrorCodes.FIELD_IN_CORRECT.message, param));
//        } else {
//            filterMap.put(param, getSingleRequestParameter(requestParams, param));
//        }
//    }
//
//    protected String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
//        if (requestParams.containsKey(parameter)) {
//            return requestParams.get(parameter).get(0);
//        } else {
//            return null;
//        }
//    }

}

package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.GiftCertificateUpdateDto;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.GiftCertificateConverter;
import com.epam.esm.mapper.TagConverter;
import com.epam.esm.service.AbstractCrudService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl extends AbstractCrudService<GiftCertificate, GiftCertificateDto, GiftCertificateCreateDto, GiftCertificateUpdateDto, GiftCertificateDao> implements GiftCertificateService {

    private final GiftCertificateDao giftCertificationDao;
    private final GiftCertificateValidator validator;
    private final GiftCertificateConverter converter;
    private final TagDaoImpl tagDao;
    private final TagConverter tagConverter;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificationDao, GiftCertificateValidator validator, GiftCertificateConverter converter, TagDaoImpl tagDao, TagConverter tagConverter) {
        this.giftCertificationDao = giftCertificationDao;
        this.validator = validator;
        this.converter = converter;
        this.tagDao = tagDao;
        this.tagConverter = tagConverter;
    }

    @Override
    public ResponseEntity<GiftCertificateDto> get(Long id) {
//        Optional<GiftCertificate> giftCertificate = giftCertificationDao.getById(id);
//        GiftCertificate certificate=giftCertificate.get();
        GiftCertificateDto certificateDto = new GiftCertificateDto();
//        certificateDto.setName(certificate.getName());
//        certificateDto.setDescription(certificate.getDescription());
//        certificateDto.setDuration(certificate.getDuration());
//        certificateDto.setPrice(certificate.getPrice());

        certificateDto.setName("certificate.getName()");
        certificateDto.setDescription("certificate.getDescription()");
        certificateDto.setDuration(15);
        certificateDto.setPrice(BigDecimal.valueOf(100.5D));
        return ResponseEntity.ok(certificateDto);
    }

    @Override
    public ResponseEntity<List<GiftCertificateDto>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<GenericDto> create(GiftCertificateCreateDto dto) {
        validator.isCreateDtoValid(dto);

        // fixme clarify there whedescriptionther or not unique fields except gift certificate id
        Long giftCertificationId = giftCertificationDao.save(converter.convertDtoToObject(dto));

        if (!(dto.getTagCreateDtoList() == null || dto.getTagCreateDtoList().isEmpty())){
            List<TagCreateDto> tagCreateDtoList=dto.getTagCreateDtoList();
            List<Tag> tagList = new ArrayList<>();
            for (TagCreateDto tagCreateDto: tagCreateDtoList) {
                tagList.add(tagConverter.convertDtoToObject(tagCreateDto));
            }

            for (Tag tag: tagList) {
                Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
                if (optionalTag.isEmpty()){
                    Long tagId = tagDao.save(tag);
                    tagDao.attachTagToGiftCertificate(tagId, giftCertificationId);
                }
            }
        }

        return ResponseEntity.ok(new GenericDto(giftCertificationId));
    }

    @Override
    public ResponseEntity<GiftCertificateDto> update(GiftCertificateUpdateDto dto) {
        return super.update(dto);
    }

    @Override
    public ResponseEntity<Boolean> delete(Long id) {
        return super.delete(id);
    }
}

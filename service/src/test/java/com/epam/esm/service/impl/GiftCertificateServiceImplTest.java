package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.converter.impl.TagConverter;
import com.epam.esm.creator.QueryCreator;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.impl.config.DatabaseConfigurationTest;
import com.epam.esm.utils.BaseUtils;
import com.epam.esm.validation.GiftCertificateValidator;
import com.epam.esm.validation.TagValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = DatabaseConfigurationTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class GiftCertificateServiceImplTest {

    @Autowired
    private GiftCertificateServiceImpl giftCertificateService;

    private static List<GiftCertificate> giftCertificateList;

    @BeforeEach
    void setUp() {
        giftCertificateList = List.of(
                new GiftCertificate(1L, "giftCertificate1", "description1", new BigDecimal("10.1"),
                        1, LocalDateTime.parse("2022-10-26T06:12:15.156"), LocalDateTime.parse("2022-10-26T06:12:15.156"),
                        Collections.singletonList(new Tag(2L, "tagName3"))),

                new GiftCertificate(2L, "giftCertificate3", "description3", new BigDecimal("19.9"),
                        3, LocalDateTime.parse("2022-10-24T06:12:15.156"), LocalDateTime.parse("2022-10-24T06:12:15.156"),
                        Collections.singletonList(new Tag(2L, "tagName3"))),

                new GiftCertificate(3L, "giftCertificate2", "description2", new BigDecimal("9.99"),
                        2, LocalDateTime.parse("2022-10-25T06:12:15.156"), LocalDateTime.parse("2022-10-25T06:12:15.156"),
                        Collections.singletonList(new Tag(2L, "tagName3")))
        );

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        GiftCertificateRowMapper giftCertificateRowMapper = new GiftCertificateRowMapper();
        QueryCreator queryCreator = new QueryCreator();

        BaseUtils baseUtils = new BaseUtils();
        TagValidator tagValidator = new TagValidator(baseUtils);
        GiftCertificateValidator validator = new GiftCertificateValidator(baseUtils, tagValidator);

        TagMapper tagMapper = new TagMapper();
        TagDaoImpl tagDao = new TagDaoImpl(jdbcTemplate, tagMapper);

        TagConverter tagConverter = new TagConverter();
        GiftCertificateConverter giftCertificateConverter = new GiftCertificateConverter(tagConverter);
        GiftCertificateDaoImpl giftCertificateDao = new GiftCertificateDaoImpl(jdbcTemplate, giftCertificateRowMapper, queryCreator);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, validator,
                tagDao, giftCertificateConverter, tagConverter);


    }

//    @Test
//    public void get() {
//        when(giftCertificationDao.findById(1L).get()).thenReturn(giftCertificateList.get(0));
//        GiftCertificateDto dto = giftCertificateService.get(1L);
//        assertEquals("giftCertificateUpdateTest", dto.getName());
//    }

    @Test
    public void getAll() {
//        when(giftCertificationDao.getAll()).thenReturn(giftCertificateList);
        List<GiftCertificateDto> actual = giftCertificateService.getAll();
        Assertions.assertEquals(giftCertificateList.size(), actual.size());
    }

    @Test
    public void create() {
        GiftCertificateCreateDto dto = new GiftCertificateCreateDto("giftCertificate4", "description4", new BigDecimal("10.1"),
                1, Collections.singletonList(new TagCreateDto("tagName3")));
        GenericDto genericDto = giftCertificateService.create(dto);
        assertEquals(4, genericDto.getId());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAttachedTagsWithGiftCertificateId() {
    }

    @Test
    public void attachTagsToGiftCertificate() {
    }

    @Test
    public void deleteAssociatedTags() {
    }

    @Test
    public void doFilter() {
    }

    @Test
    public void testDoFilter() {
    }
}
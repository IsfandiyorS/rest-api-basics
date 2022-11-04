package com.epam.esm.dao.impl;

import com.epam.esm.config.DatabaseConfigurationTest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ActionFallDaoException;
import com.epam.esm.exceptions.ObjectNotFoundDaoException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.esm.constant.GiftCertificateColumn.*;
import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = DatabaseConfigurationTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDaoImpl giftCertificateDao;
    private static List<GiftCertificate> giftCertificateList;
    private static final Tag tag = new Tag(2L, "tag2");

    @BeforeAll
    static void setUp() {
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
    }

    @Test
    void testSave() {
        GiftCertificate expected = new GiftCertificate("giftCertificate1", "description1", new BigDecimal("20.2"),
                2, null, null, Collections.singletonList(tag));
        Long saveId = giftCertificateDao.save(expected);
        assertEquals(saveId, 4);
    }

    @Test
    void testUpdate() throws ActionFallDaoException {
        GiftCertificate actual = new GiftCertificate(1L, "giftCertificateUpdateTest", null,
                new BigDecimal("11.1"), 1, null, null, Collections.singletonList(null));
        giftCertificateDao.update(convert(actual));

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(actual.getId());

        assertEquals(optionalGiftCertificate.get().getName(), actual.getName());
    }

    @Test
    void testDeleteById() throws ActionFallDaoException {
        giftCertificateDao.deleteById(3L);
        Optional<GiftCertificate> optional = giftCertificateDao.findById(3L);

        assertTrue(optional.isEmpty());
    }

    @Test
    void testFindByIdIfGiftCertificateExists() {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(1L);
        assertTrue(optionalGiftCertificate.isPresent());
    }

    @Test
    void testGetAll() {
        List<GiftCertificate> certificateList = giftCertificateDao.getAll();
        assertEquals(certificateList.size(), giftCertificateList.size());
    }


    @Test
    void testGetAttachedTagsWithGiftCertificateId() {
        List<Tag> tagList = giftCertificateDao.getAttachedTagsWithGiftCertificateId(1L);
        Optional<Tag> expected = tagList.stream().filter(entity -> entity.getName().equals(tag.getName())).findAny();

        assertEquals(expected.get().getName(), tag.getName());
    }

    @Test
    void testDeleteTagsAssociation() throws ObjectNotFoundDaoException, ActionFallDaoException {
        giftCertificateDao.deleteTagsAssociation(2L, List.of(tag));
        Optional<GiftCertificate> expectedGiftCertificate = giftCertificateDao.findById(2L);

        Optional<Tag> optionalTag = expectedGiftCertificate.get().getTagList().stream().filter(entity -> entity.getId() == 2L).findFirst();
        assertTrue(optionalTag.isEmpty());
    }

    @Test
    void testFilterGiftCertificateByGivenName() throws ObjectNotFoundDaoException {
        GiftCertificate actual = new GiftCertificate("giftCertificate2", null,
                null, 1, null, null, Collections.singletonList(null));

        Map<String, String> filter = new HashMap<>();
        filter.put("name", actual.getName());
        List<GiftCertificate> giftList = giftCertificateDao.doFilter(filter);

        assertEquals(giftList.get(0).getName(), actual.getName());
    }

    @Test
    void testFilterGiftCertificateByPartOfName() throws ObjectNotFoundDaoException {
        GiftCertificate actual = new GiftCertificate("giftCertif", null,
                null, 1, null, null, Collections.singletonList(null));

        Map<String, String> filter = new HashMap<>();
        filter.put("partOfName", actual.getName());
        List<GiftCertificate> giftList = giftCertificateDao.doFilter(filter);

        assertFalse(giftList.isEmpty());
    }
    @Test
    void testAttachTagToGiftCertificate() throws ActionFallDaoException {
        giftCertificateDao.attachTagToGiftCertificate(1L, 1L);

        List<Tag> tagList = giftCertificateDao.getAttachedTagsWithGiftCertificateId(1L);
        Optional<Tag> expected = tagList.stream().filter(entity -> entity.getName().equals(tag.getName())).findAny();

        assertEquals(expected.get().getName(), tag.getName());
    }

    @Test
    void testFindByName(){
        Optional<GiftCertificate> actual = giftCertificateDao.findByName("certificate3");
        assertTrue(actual.isEmpty());
    }

    private Map<String, String> convert(GiftCertificate actual) {
        Map<String, String> updateFieldsMap = new HashMap<>();
        updateFieldsMap.put(ID, actual.getId().toString());
        updateFieldsMap.put(NAME, actual.getName());
        updateFieldsMap.put(DESCRIPTION, actual.getDescription());
        updateFieldsMap.put(DURATION, actual.getDuration().toString());
        updateFieldsMap.put(PRICE, actual.getPrice().toString());
        return updateFieldsMap;
    }
}
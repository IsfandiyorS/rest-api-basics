package com.epam.esm.service.impl;

import com.epam.esm.criteria.GiftCertificateCriteria;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.esm.constant.GiftCertificateColumn.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDao.class);

    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);

    private static List<GiftCertificate> giftCertificateList;
    private static List<Tag> tagList;

    @BeforeEach
    void setUp() {
        tagList = List.of(
                new Tag(1L, "tag1"),
                new Tag(2L, "tag2"),
                new Tag(3L, "tag3"),
                new Tag(4L, "tag4")
        );

        giftCertificateList = List.of(
                new GiftCertificate(1L, "giftCertificate1", "description1", new BigDecimal("12.00"),
                        1, LocalDateTime.parse("2022-10-26T06:12:15.156"), LocalDateTime.parse("2022-10-26T06:12:15.156"),
                        List.of(tagList.get(1))),

                new GiftCertificate(2L, "giftCertificate3", "description3", new BigDecimal("19.9"),
                        3, LocalDateTime.parse("2022-10-24T06:12:15.156"), LocalDateTime.parse("2022-10-24T06:12:15.156"),
                        List.of(tagList.get(1))),

                new GiftCertificate(3L, "giftCertificate2", "description2", new BigDecimal("9.99"),
                        2, LocalDateTime.parse("2022-10-25T06:12:15.156"), LocalDateTime.parse("2022-10-25T06:12:15.156"),
                        null)
        );
    }

    @Test
    void testGetById() throws ObjectNotFoundException {
        when(giftCertificateDao.findById(2L)).thenReturn(Optional.of(giftCertificateList.get(1)));
        GiftCertificate dto = giftCertificateService.getById(2L);
        assertEquals(dto, giftCertificateList.get(1));
    }

    @Test
    void testGetAll() {
        when(giftCertificateDao.getAll()).thenReturn(giftCertificateList);
        List<GiftCertificate> actual = giftCertificateService.getAll();
        assertEquals(giftCertificateList.size(), actual.size());
    }

    @Test
    void testCreate() throws AlreadyExistException, ValidationException, ActionFallDaoException {
        GiftCertificate giftCertificate = new GiftCertificate("giftCertificate4", "description4",
                new BigDecimal("10.1"), 1, null, null, Collections.singletonList(new Tag("tag3")));

        given(giftCertificateDao.save(giftCertificate)).willReturn(4L);
        Long actualId = giftCertificateService.create(giftCertificate);

        assertEquals(4L, actualId);
        verify(giftCertificateDao).save(giftCertificate);
    }

    @Test
    void testUpdateGiftCertificateDoesNotExist() {
        GiftCertificate giftCertificate = new GiftCertificate(
                4L, null, "description4u", null,
                0, null, null, null);

        Map<String, String> updateFieldsMap = new HashMap<>();
        updateFieldsMap.put(ID, getValue(giftCertificate.getId()));
        updateFieldsMap.put(NAME, getValue(giftCertificate.getName()));
        updateFieldsMap.put(DESCRIPTION, getValue(giftCertificate.getDescription()));
        updateFieldsMap.put(DURATION, getValue(giftCertificate.getDuration()));
        updateFieldsMap.put(PRICE, getValue(giftCertificate.getPrice()));

        ObjectNotFoundException notFoundException = assertThrows(
                ObjectNotFoundException.class, () -> {
                    giftCertificateService.getById(giftCertificate.getId());
                });

        String expectedMessage = "Object with provided id not found";
        String actualMessage = notFoundException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testDeleteByIdIfDoesNotExist() {
        Long id = 6L;
        given(giftCertificateDao.findById(id)).willReturn(Optional.empty());
        ObjectNotFoundException notFoundException = assertThrows(
                ObjectNotFoundException.class, () -> {
                    giftCertificateService.getById(id);
                });

        String expectedMessage = "Object with provided id not found";
        String actualMessage = notFoundException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetAttachedTagsWithDoesNotExistGiftCertificateId() {
        ObjectNotFoundException notFoundException = assertThrows(
                ObjectNotFoundException.class, () -> {
                    giftCertificateService.getAttachedTagsWithGiftCertificateId(4L);
                });

        String expectedMessage = "Object with provided id not found";
        String actualMessage = notFoundException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testAttachTagsToGiftCertificate() {
        Tag tag = new Tag(6L,"tag6");
        when(giftCertificateDao.findById(3L)).thenReturn(Optional.of(giftCertificateList.get(2)));
        assertDoesNotThrow(() ->giftCertificateService.attachTagsToGiftCertificate(3L,List.of(tag)));
    }

    @Test
    void testDeleteAssociatedTags() {
        assertDoesNotThrow(() -> giftCertificateDao.deleteTagsAssociation(1L, List.of(tagList.get(1))));
    }

    @Test
    void testDoFilterWithAvailableGiftCertificate() throws ObjectNotFoundDaoException {
        GiftCertificateCriteria criteria=new GiftCertificateCriteria(null, null, "certifi", null,
                null, "desc", null, null);
        assertNotNull(giftCertificateService.doFilter(criteria));
    }

    @Test
    void testDoFilterWithDoesNotExistGiftCertificate() {
        GiftCertificateCriteria criteria=new GiftCertificateCriteria(null, null, null, null,
                null, "asdgtrdh", null, null);

        assertDoesNotThrow(() ->giftCertificateService.doFilter(criteria));
    }

    String getValue(Object value) {
        if (value == null) {
            return null;
        } else return value.toString();
    }
}
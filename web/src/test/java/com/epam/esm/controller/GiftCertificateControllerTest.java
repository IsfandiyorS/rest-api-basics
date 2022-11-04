package com.epam.esm.controller;

import com.epam.esm.criteria.GiftCertificateCriteria;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.*;
import com.epam.esm.response.DataResponse;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateControllerTest {

    @InjectMocks
    private GiftCertificateController controller;

    @Mock
    private GiftCertificateServiceImpl giftCertificateService;

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
        when(giftCertificateService.getById(1L)).thenReturn(giftCertificateList.get(1));

        ResponseEntity<DataResponse<GiftCertificate>> actual = controller.getById(1L);
        assertEquals(giftCertificateList.get(1), actual.getBody().getData());
    }

    @Test
    void testGetAll() {
        when(giftCertificateService.getAll()).thenReturn(giftCertificateList);

        ResponseEntity<DataResponse<List<GiftCertificate>>> actual = controller.getAll();
        assertEquals(giftCertificateList, actual.getBody().getData());
    }

    @Test
    void testCreate() throws ValidationException, AlreadyExistException, ActionFallDaoException {
        GiftCertificate certificate=new GiftCertificate( "giftCertificate4", "description4", new BigDecimal("12.00"),
                4, LocalDateTime.parse("2022-10-30T06:12:15.156"), LocalDateTime.parse("2022-10-30T06:12:15.156"),
                List.of(tagList.get(1)));

        when(giftCertificateService.create(certificate)).thenReturn(4L);
        ResponseEntity<DataResponse<Long>> actual = controller.create(certificate);
        assertEquals(4L, actual.getBody().getData());
    }

    @Test
    void testUpdate() {
        GiftCertificate certificate=new GiftCertificate( 3L, "giftCertificate3u", "description4", new BigDecimal("12.00"),
                4, null, null, List.of(tagList.get(2)));
        assertDoesNotThrow(() -> controller.update(certificate));
    }

    @Test
    void testDeleteById() throws ObjectNotFoundException, ActionFallDaoException {
        when(giftCertificateService.delete(3L)).thenReturn(1L);

        ResponseEntity<DataResponse<Long>> actual = controller.deleteById(3L);
        assertEquals(1L, actual.getBody().getData());
    }

    @Test
    void testGetAttachedTagsWithGiftCertificateId() throws ObjectNotFoundException {
        when(giftCertificateService.getAttachedTagsWithGiftCertificateId(1L))
                .thenReturn(List.of(tagList.get(1)));

        ResponseEntity<DataResponse<List<Tag>>> actual = controller.getAttachedTagsWithGiftCertificateId(1L);
        assertEquals( 1, actual.getBody().getData().size());

    }

    @Test
    void testAttachTagsToGiftCertificate() throws ValidationException, ObjectNotFoundException, AlreadyExistException, ActionFallDaoException {
        Tag tag = tagList.get(0);
        doNothing().when(giftCertificateService).attachTagsToGiftCertificate(1L, List.of(tag));
        ResponseEntity<DataResponse<String>> actual = controller.attachTagsToGiftCertificate(List.of(tag), 1L);

        String expected="Successfully attached";
        assertEquals(expected, actual.getBody().getData());
        verify(giftCertificateService, times(1)).attachTagsToGiftCertificate(1L, List.of(tag));
    }

    @Test
    void testDeleteAssociatedTags() throws ValidationException, ObjectNotFoundException, ActionFallDaoException {
        when(giftCertificateService.deleteAssociatedTags(2L, List.of(tagList.get(1)))).thenReturn(1L);

        ResponseEntity<DataResponse<Long>> actual = controller.deleteAssociatedTags(List.of(tagList.get(1)), 2L);
        assertEquals(1L, actual.getBody().getData());
    }

    @Test
    void testGetGiftCertificateByFilterParams() throws ObjectNotFoundDaoException {
        GiftCertificateCriteria criteria=new GiftCertificateCriteria(null, null, null, null,
                null, "asdgtrdh", null, null);

        when(giftCertificateService.doFilter(criteria)).thenReturn(null);
        assertDoesNotThrow(() -> controller.getGiftCertificateByFilterParams(criteria));
    }
}
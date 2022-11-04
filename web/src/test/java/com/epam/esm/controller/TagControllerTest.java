package com.epam.esm.controller;

import com.epam.esm.criteria.TagCriteria;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.*;
import com.epam.esm.response.DataResponse;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    @InjectMocks
    private TagController tagController;

    @MockBean
    private TagServiceImpl tagService = Mockito.mock(TagServiceImpl.class);

    private List<Tag> tagList;

    @BeforeEach
    void setUp(){
        tagList = List.of(
                new Tag(1L, "tag1"),
                new Tag(2L, "tag2"),
                new Tag(3L, "tag3"),
                new Tag(4L, "tag4")
        );
    }

    @Test
    void testGetTagById() throws ObjectNotFoundException {
        when(tagService.getById(2L)).thenReturn(tagList.get(1));

        ResponseEntity<DataResponse<Tag>> actual = tagController.getTagById(2L);
        assertEquals(tagList.get(1), actual.getBody().getData());
    }

    @Test
    void testGetAll() throws Exception {

        List<Tag> tags = List.of(tagList.get(1), tagList.get(2), new Tag(5L, "tag5"));
        when(tagService.getAll()).thenReturn(tags);

        ResponseEntity<DataResponse<List<Tag>>> controllerAll = tagController.getAll();
        assertEquals(controllerAll.getBody().getData().size(), tags.size());
    }

    @Test
    void testCreate() throws ValidationException, AlreadyExistException {
        when(tagService.create(tagList.get(0))).thenReturn(5L);

        ResponseEntity<DataResponse<Long>> actual = tagController.create(tagList.get(0));
        assertEquals(5L, actual.getBody().getData());
    }

    @Test
    void testDeleteById() throws ObjectNotFoundException, ActionFallDaoException {
        when(tagService.delete(5L)).thenReturn(1L);

        ResponseEntity<DataResponse<Long>> actual = tagController.deleteById(5L);
        assertEquals(1L, actual.getBody().getData());
    }

    @Test
    void testGetTagsByFilterParams() throws ObjectNotFoundDaoException {
        TagCriteria criteria=new TagCriteria(null, "desc", null);
        List<Tag> tags = List.of(new Tag(5L, "tag5"), tagList.get(2), tagList.get(1));

        when(tagService.doFilter(criteria)).thenReturn(tags);

        ResponseEntity<DataResponse<List<Tag>>> actual = tagController.getTagsByFilterParams(criteria);
        assertEquals(tags, actual.getBody().getData());
    }
}
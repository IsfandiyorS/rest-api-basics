package com.epam.esm.service.impl;

import com.epam.esm.criteria.TagCriteria;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.ObjectNotFoundDaoException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.exceptions.ValidationException;
import com.epam.esm.mapper.TagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.constant.FilterParameters.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDaoImpl tagDao = mock(TagDaoImpl.class);
    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagServiceImpl tagService;
    private static List<Tag> tagList;


    @BeforeEach
    void setUp() {
        tagList = List.of(
                new Tag(1L, "tag1"),
                new Tag(2L, "tag2"),
                new Tag(3L, "tag3"),
                new Tag(4L, "tag4")
        );
    }

    @Test
    void testGetTagById() throws ObjectNotFoundException {
        Tag expected = tagList.get(0);
        when(tagDao.findById(1L)).thenReturn(Optional.of(expected));
        Tag actual = tagService.getById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void testShouldThrowErrorWhenGettingNotExistedId() {
        Long id = 6L;
        given(tagDao.findById(id)).willReturn(Optional.empty());
        ObjectNotFoundException notFoundException = assertThrows(
                ObjectNotFoundException.class, () -> {
                    tagService.getById(id);
                });

        String expectedMessage = "Object with provided id not found";
        String actualMessage = notFoundException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetAll() {
        when(tagDao.getAll()).thenReturn(tagList);
        List<Tag> tagDaoList = tagService.getAll();
        assertEquals(tagDaoList.size(), tagList.size());
    }

    @Test
    void testCreateTagIfDoesNotExist() throws ValidationException, AlreadyExistException {
        Tag tag = new Tag("tag5");

        given(tagDao.findByName(tag.getName())).willReturn(Optional.empty());
        given(tagDao.save(new Tag("tag5"))).willReturn(5L);
        Long tagId = tagService.create(tag);

        assertEquals(tagId, 5L);
        verify(tagDao).save(tag);
    }

    @Test
    void testCreateTagShouldThrowErrorWhenCreatingRepeatedTag() {
        Tag tag = tagList.get(3);
        given(tagDao.findByName(tag.getName())).willReturn(Optional.of(tag));

        AlreadyExistException alreadyExistException = assertThrows(
                AlreadyExistException.class, () -> {
                    tagService.create(tag);
                });

        String expectedMessage = "Object by this name already exist";
        String actualMessage = alreadyExistException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testValidate() {
        ObjectNotFoundException notFoundException = assertThrows(
                ObjectNotFoundException.class, () -> {
                    tagService.validate(Optional.empty());
                });

        String expectedMessage = "Object with provided id not found";
        String actualMessage = notFoundException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testDoFilter() throws ObjectNotFoundDaoException {
        TagCriteria criteria = new TagCriteria("tag", null, null);

        Map<String, String> tagFilterMap = new HashMap<>();
        tagFilterMap.put(TAG_NAME, criteria.getTagName());
        tagFilterMap.put(SORT_BY_TAG_NAME, criteria.getSortByTagName());
        tagFilterMap.put(PART_OF_TAG_NAME, criteria.getPartOfTagName());

        given(tagDao.doFilter(tagFilterMap)).willReturn(List.of(new Tag(1L, "tag")));
        List<Tag> actual = tagService.doFilter(criteria);
        assertFalse(actual.isEmpty());
    }

    @Test
    void testDeleteByIdShouldThrowException() {

        ObjectNotFoundException notFoundException = assertThrows(
            ObjectNotFoundException.class, () -> {
                tagService.delete(4L);
            });

        String expectedMessage = "Object with provided id not found";
        String actualMessage = notFoundException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
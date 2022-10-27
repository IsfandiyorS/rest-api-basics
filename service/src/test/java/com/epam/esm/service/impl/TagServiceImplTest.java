package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.validation.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDaoImpl tagDao;
    @Mock
    private TagValidator tagValidator;
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
    void getTagById() {
        Tag expected = tagList.get(0);
        when(tagDao.findById(1L)).thenReturn(Optional.of(expected));
        TagDto tagDto = tagService.get(1L);
        Tag actual = new Tag(tagDto.getId(), tagDto.getName());
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowErrorWhenGettingTagWithById() {
        Long id = 6L;
        given(tagDao.findById(id)).willReturn(Optional.empty());
        ObjectNotFoundException notFoundException = assertThrows(ObjectNotFoundException.class, () -> {
            tagService.get(id);
        });

        String expectedMessage = String.format("Tag with provided id: %s not found", id);
        String actualMessage = notFoundException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getAll() {
        when(tagDao.getAll()).thenReturn(tagList);
        List<TagDto> tagDaoList = tagService.getAll();
        assertEquals(tagDaoList.size(), tagList.size());
    }

    @Test
    void createTagIfDoesNotExist() {
        TagCreateDto tagCreateDto = new TagCreateDto("tag5");

        given(tagDao.findByName(tagCreateDto.getName())).willReturn(Optional.empty());
        given(tagDao.save(new Tag("tag5"))).willReturn(5L);
        GenericDto genericDto = tagService.create(tagCreateDto);

        assertEquals(genericDto.getId(), 5L);
        verify(tagDao).save(new Tag("tag5"));
    }

    @Test
    void shouldThrowErrorWhenCreatingRepeatedTag() {
        TagCreateDto tagCreateDto = new TagCreateDto("tag4");
        given(tagDao.findByName(tagCreateDto.getName())).willReturn(Optional.of(new Tag("tag4")));

        AlreadyExistException alreadyExistException = assertThrows(AlreadyExistException.class, () -> {
            tagService.create(tagCreateDto);
        });

        String expectedMessage = "Tag by this name already exist";
        String actualMessage = alreadyExistException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}
package com.epam.esm.dao.impl;

import com.epam.esm.config.DatabaseConfigurationTest;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = DatabaseConfigurationTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TagDaoImplTest {

    @Autowired
    private TagDaoImpl tagDao;
    private static List<TagCreateDto> tagCreateDtoList;
    private static List<TagDto> tagDtoList;

    @BeforeAll
    static void setUp() {
        tagCreateDtoList = List.of(
                new TagCreateDto("tag1"),
                new TagCreateDto("tag2"),
                new TagCreateDto("tag3"),
                new TagCreateDto("tag4")
        );

        tagDtoList = List.of(
                new TagDto(1L,"tag1"),
                new TagDto(2L, "tag2"),
                new TagDto(3L, "tag3"),
                new TagDto(4L, "tag4")
        );
    }

    @Test
    void save() {
        TagCreateDto dto = new TagCreateDto("tag5");
    }

    @Test
    void removeById() {
    }

    @Test
    void findById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void findTagByNameAlreadyExisted() {
        Optional<TagDto> optionalTag = tagDao.findByName(tagCreateDtoList.get(0).getName());
        assertEquals(optionalTag.get().getName(), tagCreateDtoList.get(0).getName());
    }

    @Test
    void findTagByNameNotExisted() {
        Optional<TagDto> optionalTag = tagDao.findByName("tag5");
        assertTrue(optionalTag.isEmpty());
    }

    @Test
    void getAttachedTagsWithGiftCertificateId() {
    }

    @Test
    void attachTagToGiftCertificate() {
    }

    @Test
    void checkForAvailabilityOfTagIdInRelatedTable() {
    }
}
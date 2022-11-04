package com.epam.esm.dao.impl;

import com.epam.esm.config.DatabaseConfigurationTest;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ActionFallDaoException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = DatabaseConfigurationTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TagDaoImplTest {

    @Autowired
    private TagDaoImpl tagDao;
    private static List<Tag> tagList;

    @BeforeAll
    static void setUp() {
        tagList = List.of(
                new Tag(1L, "tag1"),
                new Tag(2L, "tag2"),
                new Tag(3L, "tag3"),
                new Tag(4L, "tag4")
        );
    }

    @Test
    void testSave() {
        Tag tag = new Tag("tag5");
        assertEquals(tagDao.save(tag), 6);
    }

    @Test
    void testDeleteById() throws ActionFallDaoException {
        tagDao.deleteById(1L);
        assertTrue(tagDao.findByName(tagList.get(0).getName()).isEmpty());
    }

    @Test
    void testFindById() {
        Tag tag = tagList.get(1);
        assertEquals(tag.getName(), tagDao.findById(tag.getId()).get().getName());
    }

    @Test
    void testGetAll() {
        List<Tag> actual = tagDao.getAll();
        assertEquals(tagList.size(), actual.size()-1);
    }

    @Test
    void testFindByName() {
        Optional<Tag> optionalTag = tagDao.findByName(tagList.get(0).getName());
        assertEquals(optionalTag.get().getName(), tagList.get(0).getName());
    }

    @Test
    void testFindTagByNameDoesNotExisted() {
        Optional<Tag> optionalTag = tagDao.findByName(tagList.get(3).getName());
        assertFalse(optionalTag.isEmpty());
    }

    @Test
    void testGetAttachedTagsWithGiftCertificateIdToEmptyList() {
        List<Tag> tagDaoList = tagDao.getAttachedTagsWithGiftCertificateId(4L);
        assertTrue(tagDaoList.isEmpty());
    }

    @Test
    void testGetAttachedTagsWithGiftCertificateIdToNotEmptyList() {
        List<Tag> tagDaoList = tagDao.getAttachedTagsWithGiftCertificateId(1L);
        assertFalse(tagDaoList.isEmpty());
    }

    @Test
    void testCheckForAvailabilityOfTagIdInRelatedTable() {
        boolean isAvailable = tagDao.checkForAvailabilityOfTagIdInRelatedTable(2L, 1L);
        assertTrue(isAvailable);
    }

}
package com.epam.esm.dao.impl;

import com.epam.esm.constant.ActionMassages;
import com.epam.esm.constant.QueryForTagTable;
import com.epam.esm.creator.QueryCreator;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.ActionFallDaoException;
import com.epam.esm.exceptions.ObjectNotFoundDaoException;
import com.epam.esm.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.constant.ActionMassages.*;
import static com.epam.esm.constant.QueryForTagTable.*;
import static com.epam.esm.constant.TagColumn.*;
import static java.lang.String.*;

/**
 * Class {@code TagDaoImpl} is implementation of interface
 * {@link TagDao} and intended to work with 'tags' table.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@Repository
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;
    private final QueryCreator queryCreator;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper, QueryCreator queryCreator) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
        this.queryCreator = queryCreator;
    }

    @Override
    public Long save(Tag tag) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_TAG_QUERY, new String[]{ID});
                    ps.setString(1, tag.getName());
                    return ps;
                },
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Long deleteById(Long id) throws ActionFallDaoException {
        jdbcTemplate.update(DELETE_TAG_IN_TABLE_WITH_TAGS, id);
        int deleteEntity = jdbcTemplate.update(DELETE_TAG_QUERY, id);
        if (deleteEntity == 0){
            throw new ActionFallDaoException(format(ErrorCodes.ACTION_FALL.message, DELETE));
        }
        return (long) deleteEntity;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(GET_TAG_BY_ID, tagMapper, id).stream().findAny();
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(GET_ALL_TAG, tagMapper);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(GET_TAG_BY_NAME, tagMapper, name).stream().findAny();
    }

    @Override
    public List<Tag> getAttachedTagsWithGiftCertificateId(Long giftCertificateId) {
        return jdbcTemplate.query(GET_ATTACHED_TAGS_BY_GIFT_CERTIFICATE_ID, tagMapper, giftCertificateId);
    }

    @Override
    public boolean checkForAvailabilityOfTagIdInRelatedTable(Long tagId, Long giftCertificateId) {
        List<Long> longList = jdbcTemplate.query(GET_TAG_ID_FROM_RELATED_TABLE, (rs, rowNum) -> rs.getLong(TAG_ID), tagId, giftCertificateId);
        return longList.size() > 0;
    }

    @Override
    public List<Tag> doFilter(Map<String, String> criteria) throws ObjectNotFoundDaoException {
        String query = queryCreator.createGetTagQuery(criteria, TAG_TABLE, TAG_TABLE_NAME_IN_QUERY);
        try {
            return jdbcTemplate.query(query, new TagMapper());
        } catch (DataAccessException e) {
            throw new ObjectNotFoundDaoException(format(ErrorCodes.OBJECT_NOT_FOUND_BY_FIELD.message, FIELD));
        }

    }
}

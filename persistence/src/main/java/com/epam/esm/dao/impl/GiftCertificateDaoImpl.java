package com.epam.esm.dao.impl;

import com.epam.esm.creator.QueryCreator;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.ActionFallDaoException;
import com.epam.esm.exceptions.ObjectNotFoundDaoException;
import com.epam.esm.mapper.GiftCertificateResultSetExtractor;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import com.epam.esm.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.*;

import static com.epam.esm.constant.ActionMassages.*;
import static com.epam.esm.constant.GiftCertificateColumn.*;
import static com.epam.esm.constant.QueryForGiftCertificate.*;
import static com.epam.esm.constant.QueryForTagTable.ATTACH_TAG_TO_GIFT;
import static com.epam.esm.constant.QueryForTagTable.GET_ASSOCIATED_TAGS_QUERY;
import static java.lang.String.format;

/**
 * Class {@code GiftCertificateDaoImpl} is implementation of interface {@link GiftCertificateDao}
 * and intended to work with 'gift_certificate' table.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateRowMapper giftCertificateRowMapper;
    private final QueryCreator queryCreator;
    private final GiftCertificateResultSetExtractor extractor;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateRowMapper giftCertificateRowMapper, QueryCreator queryCreator, GiftCertificateResultSetExtractor extractor, TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
        this.queryCreator = queryCreator;
        this.extractor = extractor;
        this.tagDao = tagDao;
    }

    @Override
    public Long save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_GIFT_CERTIFICATE_QUERY, new String[]{ID});
                    ps.setString(1, giftCertificate.getName());
                    ps.setString(2, giftCertificate.getDescription());
                    ps.setBigDecimal(3, giftCertificate.getPrice());
                    ps.setInt(4, giftCertificate.getDuration());
                    return ps;
                },
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Long update(Map<String, String> item) throws ActionFallDaoException {
        String updateQuery = queryCreator.createUpdateQuery(item, GIFT_CERTIFICATION_TABLE);
        int update = jdbcTemplate.update(updateQuery);
        if (update == 0){
            throw new ActionFallDaoException(format(ErrorCodes.ACTION_FALL.message, UPDATE));
        }
        return (long) update;
    }

    @Override
    public Long deleteById(Long id) throws ActionFallDaoException {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_IN_TABLE_WITH_TAGS, id);
        int deleteEntity = jdbcTemplate.update(DELETE_GIFT_BY_ID, id);
        if (deleteEntity == 0){
            throw new ActionFallDaoException(format(ErrorCodes.ACTION_FALL.message, DELETE));
        }
        return (long) deleteEntity;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        List<GiftCertificate> query = jdbcTemplate.query(GET_GIFT_BY_ID, giftCertificateRowMapper, id);
        return query.stream().findAny();
    }

    @Override
    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query(GET_ALL_GIFT, extractor);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        List<GiftCertificate> giftCertificate = jdbcTemplate.query(GET_GIFT_BY_NAME, giftCertificateRowMapper, name);
        return giftCertificate.stream().findFirst();
    }

    @Override
    public List<GiftCertificate> doFilter(Map<String, String> filterMap) throws ObjectNotFoundDaoException {
        String query = queryCreator.createGetQuery(filterMap, GIFT_CERTIFICATION_TABLE, GIFT_CERTIFICATE_NAME_IN_QUERY);
        try{
            return jdbcTemplate.query(query, extractor);
        } catch (DataAccessException e){
            throw new ObjectNotFoundDaoException(format(ErrorCodes.OBJECT_NOT_FOUND_BY_FIELD.message, FIELD));
        }
    }

    // todo updated to Long
    @Override
    public Long attachTagToGiftCertificate(Long tagId, Long giftCertificateId) throws ActionFallDaoException {
        String query = format(ATTACH_TAG_TO_GIFT, tagId, giftCertificateId);
//        jdbcTemplate.execute(query);
        int update = jdbcTemplate.update(query);
        if (update == 0 ){
            throw new ActionFallDaoException(format(ErrorCodes.ACTION_FALL.message, ATTACH));
        }
        return (long) update;
    }

    @Override
    public List<Tag> getAttachedTagsWithGiftCertificateId(Long giftCertificateId) {
        return jdbcTemplate.query(GET_ASSOCIATED_TAGS_QUERY, new TagMapper(), giftCertificateId);
    }

    @Override
    public Long deleteTagsAssociation(Long id, List<Tag> tagList) throws ActionFallDaoException, ObjectNotFoundDaoException {
        List<Long> tags = getTagsId(tagList, id);
//        tags.forEach(tagId -> {
//                jdbcTemplate.update(REMOVE_TAGS_ASSOCIATION_QUERY, id, tagId);
//            });
        int deletedTags = 0;
        for (Long tagId: tags) {
            deletedTags = jdbcTemplate.update(REMOVE_TAGS_ASSOCIATION_QUERY, id, tagId);
            if (deletedTags == 0){
                throw new ActionFallDaoException(format(ErrorCodes.ACTION_FALL.message, DELETE));
            }
        }
        return (long) deletedTags;
    }

    private List<Long> getTagsId(List<Tag> tags, Long giftCertificateId) throws ObjectNotFoundDaoException {
        List<Long> tagIdList = new ArrayList<>();
        for (Tag tag: tags) {
            String tagName = tag.getName();
            Optional<Tag> optionalTag = tagDao.findByName(tagName);
            if (optionalTag.isEmpty()) {
                throw new ObjectNotFoundDaoException(format(ErrorCodes.OBJECT_NOT_FOUND_BY_FIELD.message, "name"));
            } else {
                boolean exist = tagDao.checkForAvailabilityOfTagIdInRelatedTable(optionalTag.get().getId(), giftCertificateId);
                if (exist) {
                    tagIdList.add(optionalTag.get().getId());
                } else {
                    throw new ObjectNotFoundDaoException(format(ErrorCodes.OBJECT_NOT_FOUND.message));
                }
            }
        }
        return tagIdList;
    }

}

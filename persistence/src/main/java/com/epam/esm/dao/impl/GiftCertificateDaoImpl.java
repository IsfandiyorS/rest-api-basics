package com.epam.esm.dao.impl;

import com.epam.esm.creator.QueryCreator;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.mapper.GiftCertificateDtoRowMapper;
import com.epam.esm.mapper.GiftCertificateRowMapper;
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

import static com.epam.esm.constant.QueryForGiftCertificate.*;
import static com.epam.esm.constant.QueryForTagTable.ATTACH_TAG_TO_GIFT;
import static com.epam.esm.constant.QueryForTagTable.GET_ASSOCIATED_TAGS_QUERY;
import static com.epam.esm.mapper.GiftCertificateColumn.GIFT_CERTIFICATION_TABLE;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateRowMapper giftCertificateRowMapper;
    private final QueryCreator queryCreator;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateRowMapper giftCertificateRowMapper, QueryCreator queryCreator) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
        this.queryCreator = queryCreator;
    }

    @Override
    public Long save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_GIFT_CERTIFICATE_QUERY, new String[]{"id"});
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
    public void update(Map<String, String> item) {
        String updateQuery = queryCreator.createUpdateQuery(item, GIFT_CERTIFICATION_TABLE);
        jdbcTemplate.update(updateQuery);
    }

    @Override
    public void removeById(Long id) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_IN_TABLE_WITH_TAGS, id);
        jdbcTemplate.update(DELETE_GIFT_BY_ID, id);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return jdbcTemplate.query(GET_GIFT_BY_ID, giftCertificateRowMapper, id).stream().findAny();
    }

    @Override
    public List<GiftCertificate> getAll() {
        List<GiftCertificate> query = jdbcTemplate.query(GET_ALL_GIFT, new GiftCertificateDtoRowMapper());
        return query;
    }

    @Override
    public void attachTagToGiftCertificate(Long tagId, Long giftCertificateId) {
        String query = String.format(ATTACH_TAG_TO_GIFT, tagId, giftCertificateId);
        jdbcTemplate.execute(query);
    }

    @Override
    public List<Tag> getAttachedTagsWithGiftCertificateId(Long giftCertificateId) {
        return jdbcTemplate.query(GET_ASSOCIATED_TAGS_QUERY, new TagMapper(), giftCertificateId);
    }

    @Override
    public void deleteTagsAssociation(Long id, List<Long> tags) {
            tags.forEach(tagId -> {
                jdbcTemplate.update(REMOVE_TAGS_ASSOCIATION_QUERY, id, tagId);
            });
    }

    @Override
    public List<GiftCertificate> getGiftCertificateByFilteringParameters(Map<String, String> criteria) {
        String query = queryCreator.createGetQuery(criteria, "gift_certificate");
        try{
            return jdbcTemplate.query(query, new GiftCertificateDtoRowMapper());
        } catch (DataAccessException e){
            throw new DaoException("Objects with not found by this");
        }

    }
}

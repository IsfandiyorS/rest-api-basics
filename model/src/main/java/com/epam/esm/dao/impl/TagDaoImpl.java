package com.epam.esm.dao.impl;

import com.epam.esm.constant.QueryForTagTable;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.constant.QueryForTagTable.*;

@Repository
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public Long save(TagCreateDto tag) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_TAG_QUERY, new String[]{"id"});
                    ps.setString(1, tag.getName());
                    return ps;
                },
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void removeById(Long id) {
        jdbcTemplate.update(DELETE_TAG_QUERY, id);
    }

    @Override
    public Optional<TagDto> findById(Long id) {
        return jdbcTemplate.query(GET_TAG_BY_ID, tagMapper, id).stream().findAny();
    }

    @Override
    public List<TagDto> getAll() {
        return jdbcTemplate.query(GET_ALL_TAG, tagMapper);
    }

    @Override
    public Optional<TagDto> findByName(String name) {
        return jdbcTemplate.query(GET_TAG_BY_NAME, tagMapper, name).stream().findAny();
    }

    @Override
    public List<TagDto> getAttachedTagsWithGiftCertificateId(Long giftCertificateId) {
        return jdbcTemplate.query(GET_ATTACHED_TAGS_BY_GIFT_CERTIFICATE_ID, tagMapper, giftCertificateId);
    }

    @Override
    public void attachTagToGiftCertificate(Long tagId, Long giftCertificateId) {
        String query = String.format(QueryForTagTable.ATTACH_TAG_TO_GIFT, tagId, giftCertificateId);
        jdbcTemplate.execute(query);
    }

    @Override
    public boolean checkForAvailabilityOfTagIdInRelatedTable(Long tagId, Long giftCertificateId) {
        List<Long> longList = jdbcTemplate.query(GET_TAG_ID_FROM_RELATED_TABLE, (rs, rowNum) -> rs.getLong("tag_id"), tagId, giftCertificateId);
        return longList.size()>0;
    }


}

package com.epam.esm.dao.impl;

import com.epam.esm.constant.QueryForTagTable;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Tag tag) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(QueryForTagTable.INSERT_TAG_QUERY, new String[]{"id"});
                    ps.setString(1, tag.getName());
                    return ps;
                },
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void removeById(Long id) {
        jdbcTemplate.update(QueryForTagTable.DELETE_TAG_QUERY, id);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(QueryForTagTable.GET_TAG_BY_ID, new TagMapper(), id).stream().findAny();
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(QueryForTagTable.GET_ALL_TAG, new TagMapper());
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(QueryForTagTable.GET_TAG_BY_NAME, new TagMapper(), name).stream().findAny();
    }

    @Override
    public void attachTagToGiftCertificate(Long tagId, Long giftCertificateId) {
        String query = String.format(QueryForTagTable.ATTACH_TAG_TO_GIFT, tagId, giftCertificateId);
        jdbcTemplate.execute(query);
    }

//    public void updateTags() {
////        if (item.getTags() == null) {
////            return;
////        }
//        List<Long> tagIds = getTagsIds(item.getTags());
//        for (Long id : tagIds) {
//            executeUpdateQuery(ADD_TAGS_QUERY, giftCertificateId, id);
//        }
//    }

//    private List<Long> getTagsIds(List<Tag> tags) {
//        List<Long> tagIds = new ArrayList();
//        tags.stream().forEach(tag -> {
//            String tagName = tag.getName();
//            Tag tagWithId = null;
//
//            try {
//                tagWithId = tagDao.getByName(tagName).get(0);
//            } catch (DaoException e) {
//                e.printStackTrace();
//            }
//
//            tagIds.add(tagWithId.getId());
//        });
//        return tagIds;
//    }
}

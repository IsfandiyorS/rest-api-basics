package com.epam.esm.dao.impl;

import com.epam.esm.constant.QueryForGiftCertificate;
import com.epam.esm.constant.QueryForTagTable;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateMapper;
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
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(QueryForGiftCertificate.INSERT_GIFT_CERTIFICATE_QUERY, new String[]{"id"});
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
    public void update(GiftCertificate item) {
//        jdbcTemplate.update(); fixme create
    }

    @Override
    public void removeById(Long id) {
        jdbcTemplate.update(QueryForGiftCertificate.DELETE_GIFT_BY_ID, id);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        GiftCertificate giftCertificate = jdbcTemplate.queryForObject(QueryForGiftCertificate.GET_GIFT_BY_ID, new GiftCertificateMapper(), id);
        if (giftCertificate.getName() == null) {
            // fixme to empty gift
        }
        return Optional.of(giftCertificate);
    }

    @Override
    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query(QueryForTagTable.GET_ALL_TAG, new GiftCertificateMapper());
    }

}

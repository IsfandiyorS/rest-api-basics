package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(rs.getLong(GiftCertificateColumn.ID));
        giftCertificate.setDescription(rs.getString(GiftCertificateColumn.DESCRIPTION));
        giftCertificate.setDuration(rs.getInt(GiftCertificateColumn.DURATION));
        giftCertificate.setName(rs.getString(GiftCertificateColumn.NAME));
        giftCertificate.setPrice(rs.getBigDecimal(GiftCertificateColumn.PRICE));
        return giftCertificate;
    }
}

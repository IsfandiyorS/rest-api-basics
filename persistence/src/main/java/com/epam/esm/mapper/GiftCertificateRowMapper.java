package com.epam.esm.mapper;

import com.epam.esm.constant.TagColumn;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.constant.GiftCertificateColumn.*;

/**
 * The {@code GiftCertificateRowMapper} class is an implementation of the
 * {@link RowMapper} interface and is designed to work with a gift_certificate
 * table to display ResultSet rows for each row.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    /**
     * Method map each row of data in the ResultSet.
     * It is supposed to map values of every row on {@link GiftCertificate}.
     *
     * @param rs the ResultSet to map (pre-initialized before the current row)
     * @return the result gift certificate (maybe {@code empty})
     * @throws SQLException
     */
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate entity = new GiftCertificate();
        entity.setId(rs.getLong(ID));
        entity.setName(rs.getString(GIFT_NAME_AS_CONNECTED_TABLE));
        entity.setDescription(rs.getString(DESCRIPTION));
        entity.setPrice(rs.getBigDecimal(PRICE));
        entity.setDuration(rs.getInt(DURATION));
        entity.setCreateDate(rs.getTimestamp(CREATE_DATE).toLocalDateTime());
        String lastUpdateDate = rs.getString(LAST_UPDATE_DATE);

        if (lastUpdateDate != null) {
            entity.setLastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
        }
        List<Tag> tags = new ArrayList<>();

        while (!rs.isAfterLast() && rs.getInt(ID) == entity.getId()) {
            long tagId = rs.getLong(TagColumn.TAG_ID);
            if (tagId > 0) {
                String tagName = rs.getString(TagColumn.TAG_NAME);
                tags.add(new Tag(tagId, tagName));
            }
            rs.next();
        }
        entity.setTagList(tags);
        return entity;
    }

}

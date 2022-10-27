package com.epam.esm.mapper;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.mapper.GiftCertificateColumn.*;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    private static final String TAG_ID = "tag_id";
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

        if (lastUpdateDate!=null){
            entity.setLastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
        }
        List<Tag> tags = new ArrayList<>();

        while (!rs.isAfterLast() && rs.getInt(ID) == entity.getId()) {
            long tagId = rs.getLong(TAG_ID);
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

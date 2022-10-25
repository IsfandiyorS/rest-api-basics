package com.epam.esm.mapper;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.mapper.GiftCertificateColumn.*;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificateDto> {
    private static final String TAG_ID = "tag_id";
    @Override
    public GiftCertificateDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificateDto giftCertificate = new GiftCertificateDto();
        giftCertificate.setId(rs.getLong(ID));
        giftCertificate.setName(rs.getString(GIFT_NAME_AS_CONNECTED_TABLE));
        giftCertificate.setDescription(rs.getString(DESCRIPTION));
        giftCertificate.setPrice(rs.getBigDecimal(PRICE));
        giftCertificate.setDuration(rs.getInt(DURATION));
        giftCertificate.setCreateDate(rs.getTimestamp(CREATE_DATE).toLocalDateTime());
        String lastUpdateDate = rs.getString(LAST_UPDATE_DATE);

        if (lastUpdateDate!=null){
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//                giftCertificate.setLastUpdateDate(LocalDateTime.parse(lastUpdateDate, formatter));
            giftCertificate.setLastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
        }
        List<TagDto> tags = new ArrayList<>();

        while (!rs.isAfterLast() && rs.getInt(ID) == giftCertificate.getId()) {
            long tagId = rs.getLong(TAG_ID);
            if (tagId > 0) {
                String tagName = rs.getString(TagColumn.TAG_NAME);
                tags.add(new TagDto(tagId, tagName));
            }
            rs.next();
        }
        giftCertificate.setTagDtoList(tags);
//        giftCertificates.add(giftCertificate);
        return giftCertificate;
    }

}

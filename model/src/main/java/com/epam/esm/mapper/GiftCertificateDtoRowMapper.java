package com.epam.esm.mapper;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.TagDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.mapper.GiftCertificateColumn.*;

@Component
public class GiftCertificateDtoRowMapper implements ResultSetExtractor<List<GiftCertificateDto>> {
    private static final String TAG_ID = "tag_id";

    @Override
    public List<GiftCertificateDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificateDto> giftCertificates = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {
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
            giftCertificates.add(giftCertificate);

        }
        return giftCertificates;
    }
}
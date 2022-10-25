package com.epam.esm.mapper;

import com.epam.esm.dto.impl.TagDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<TagDto> {
    @Override
    public TagDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        TagDto tagDto = new TagDto();

        tagDto.setId(rs.getLong(TagColumn.ID));
        tagDto.setName(rs.getString(TagColumn.NAME));
        return tagDto;
    }
}

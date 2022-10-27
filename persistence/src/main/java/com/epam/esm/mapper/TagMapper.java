package com.epam.esm.mapper;

import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();

        tag.setId(rs.getLong(TagColumn.ID));
        tag.setName(rs.getString(TagColumn.NAME));
        return tag;
    }
}

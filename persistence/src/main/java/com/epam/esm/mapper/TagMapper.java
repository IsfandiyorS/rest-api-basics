package com.epam.esm.mapper;

import com.epam.esm.constant.TagColumn;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code TagMapper} class is an implementation of the
 * {@link RowMapper} interface and is designed to work with a gift_certificate
 * table to display ResultSet rows for each row.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@Component
public class TagMapper implements RowMapper<Tag> {

    /**
     * Method map each row of data in the ResultSet.
     * It is supposed to map values of every row on {@link Tag}.
     *
     * @param rs the ResultSet to map (pre-initialized before the current row)
     * @return the result gift certificate (maybe {@code empty})
     * @throws SQLException
     */
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();

        tag.setId(rs.getLong(TagColumn.ID));
        tag.setName(rs.getString(TagColumn.NAME));
        return tag;
    }
}

package com.epam.esm.constant;

import java.security.PublicKey;

public final class QueryForTagTable {
    public static final String INSERT_TAG_QUERY = "INSERT INTO tag(name) values(?)";
    public static final String DELETE_TAG_QUERY = "DELETE FROM tag WHERE id = ?";
    public static final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?";
    public static final String GET_ALL_TAG = "SELECT * FROM tag";
    public static final String GET_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";
    public static final String ATTACH_TAG_TO_GIFT = "INSERT INTO gift_certificate_tag(tag_id, gift_certificate_id) VALUES(%s, %s)";
}

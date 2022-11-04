package com.epam.esm.constant;

/**
 * Class {@code QueryForTagTable} defined with final key word, it will be used in
 * {@link com.epam.esm.dao.TagDao} classes in order to SQL query
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public final class QueryForTagTable {
    public static final String INSERT_TAG_QUERY = "INSERT INTO tag(name) values(?)";
    public static final String DELETE_TAG_QUERY = "DELETE FROM tag WHERE id = ?";
    public static final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?";
    public static final String GET_ALL_TAG = "SELECT * FROM tag ORDER BY id";
    public static final String GET_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";
    public static final String ATTACH_TAG_TO_GIFT = "INSERT INTO gift_certificate_tag(tag_id, gift_certificate_id) VALUES(%s, %s)";
    public static final String GET_ATTACHED_TAGS_BY_GIFT_CERTIFICATE_ID = """
            SELECT t.id as id, t.name as name FROM gift_certificate gc
            LEFT JOIN gift_certificate_tag gct ON  gc.id=gct.gift_certificate_id
            LEFT JOIN tag t ON gct.tag_id=t.id WHERE gc.id=?
            """;
    public static final String GET_ASSOCIATED_TAGS_QUERY = """
            SELECT * FROM tag t INNER JOIN gift_certificate_tag gct ON
            t.id = gct.tag_id WHERE gct.gift_certificate_id=?
            """;
    public static final String GET_TAG_ID_FROM_RELATED_TABLE= """
            SELECT tag_id FROM gift_certificate_tag WHERE tag_id = ? AND gift_certificate_id = ?;
            """;

    public static final String DELETE_TAG_IN_TABLE_WITH_TAGS =
            "DELETE FROM gift_certificate_tag WHERE tag_id=?";
    private QueryForTagTable(){}
}

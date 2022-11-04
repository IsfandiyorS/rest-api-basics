package com.epam.esm.constant;

/**
 * Class {@code QueryForGiftCertificate} defined with final key word, it will be used in {@link com.epam.esm.dao.GiftCertificateDao} classes
 * in order to SQL query
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public final class QueryForGiftCertificate {
    public static final String GET_GIFT_BY_ID = """
        SELECT gc.id, gc.name as gift_name, duration, description, price,create_date,
        last_update_date, t.id as tag_id, t.name as tag_name
        FROM gift_certificate gc
        LEFT JOIN gift_certificate_tag gct ON  gc.id=gct.gift_certificate_id
        LEFT JOIN tag t ON gct.tag_id=t.id WHERE gc.id=?;
        """;

    public static final String GET_GIFT_BY_NAME = """
        SELECT gc.id, gc.name as gift_name, duration, description, price,create_date,
        last_update_date, t.id as tag_id, t.name as tag_name
        FROM gift_certificate gc
        LEFT JOIN gift_certificate_tag gct ON  gc.id=gct.gift_certificate_id
        LEFT JOIN tag t ON gct.tag_id=t.id WHERE gc.name=?;
        """;
    public static final String INSERT_GIFT_CERTIFICATE_QUERY = """
            INSERT INTO gift_certificate(name, description, price, duration) VALUES(?,?,?,?)""";

    public static final String DELETE_GIFT_BY_ID = "DELETE FROM gift_certificate WHERE id = ?";

    public static final String GET_ALL_GIFT = """
            SELECT gc.id, gc.name as gift_name, duration, description, price,create_date,
            last_update_date, t.id as tag_id, t.name as tag_name
            FROM gift_certificate gc
            LEFT JOIN gift_certificate_tag gct ON gc.id=gct.gift_certificate_id
            LEFT JOIN tag t ON gct.tag_id=t.id order by gc.id
            """;

    public static final String DELETE_GIFT_CERTIFICATE_IN_TABLE_WITH_TAGS =
            "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=?";

    public static final String CHECK_FOR_EXISTING_TAG_WITH_GIFT =
            "SELECT tag_id  FROM gift_certificate_tag WHERE tag_id = ? AND gift_certificate_id = ?";

    public static final String REMOVE_TAGS_ASSOCIATION_QUERY = """
            DELETE FROM gift_certificate_tag WHERE gift_certificate_id=? AND tag_id=?""";


    private QueryForGiftCertificate(){}
}

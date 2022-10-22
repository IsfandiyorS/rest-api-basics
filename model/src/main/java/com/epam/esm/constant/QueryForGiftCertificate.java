package com.epam.esm.constant;

import java.security.PublicKey;

public final class QueryForGiftCertificate {
    public static final String GET_GIFT_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?";
    public static final String INSERT_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificate(name, description, price, duration) VALUES(?,?,?,?)";

    public static final String DELETE_GIFT_BY_ID = "DELETE FROM gift_certificate WHERE id = ?";

    public static final String GET_ALL_GIFT = "SELECT *  FROM gift_certificate";
}

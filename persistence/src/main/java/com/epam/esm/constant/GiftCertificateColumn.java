package com.epam.esm.constant;

/**
 * Class {@code GiftCertificateColumn} defined with final key word it indicates this class
 * contains unchangeable 'gift_certificate' table elements.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public final class GiftCertificateColumn {
    public static final String GIFT_CERTIFICATION_TABLE = "gift_certificate";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String DURATION = "duration";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";
    public static final String GIFT_NAME_AS_CONNECTED_TABLE = "gift_name";
    public static final String GIFT_CERTIFICATE_NAME_IN_QUERY = "gc.";

    private GiftCertificateColumn(){}
}

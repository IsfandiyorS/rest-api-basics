package com.epam.esm.constant;

/**
 * Class {@code FilterParameters} defined with final key word it indicates this class
 * contains unchangeable filter elements.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public final class FilterParameters {
    /**
     * Filter options.
     */
    public static final String NAME = "name";
    public static final String TAG_NAME = "tag_name";
    public static final String PART_OF_NAME = "partOfName";
    public static final String PART_OF_DESCRIPTION = "partOfDescription";
    public static final String PART_OF_TAG_NAME = "partOfTagName";
    public static final String SORT_BY_NAME = "sortByName";
    public static final String SORT_BY_CREATE_DATE = "sortByCreateDate";
    public static final String SORT_BY_TAG_NAME = "sortByTagName";

    private FilterParameters() {
    }
}
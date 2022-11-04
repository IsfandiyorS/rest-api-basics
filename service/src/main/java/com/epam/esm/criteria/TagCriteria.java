package com.epam.esm.criteria;

/**
 * Class {@code TagCriteria} indicates it will be used for filtering or sorting
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public class TagCriteria implements Criteria{
    private String tagName;
    private String sortByTagName;
    private String partOfTagName;

    public TagCriteria() {
    }

    public TagCriteria(String tagName, String sortByTagName, String partOfTagName) {
        this.tagName = tagName;
        this.sortByTagName = sortByTagName;
        this.partOfTagName = partOfTagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSortByTagName() {
        return sortByTagName;
    }

    public void setSortByTagName(String sortByTagName) {
        this.sortByTagName = sortByTagName;
    }

    public String getPartOfTagName() {
        return partOfTagName;
    }

    public void setPartOfTagName(String partOfTagName) {
        this.partOfTagName = partOfTagName;
    }
}

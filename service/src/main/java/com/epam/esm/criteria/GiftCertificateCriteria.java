package com.epam.esm.criteria;

/**
 * Class {@code GiftCertificateCriteria} indicates it will be used for filtering or sorting
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public class GiftCertificateCriteria implements Criteria {
    private String name;
    private String tagName;
    private String partOfName;
    private String partOfDescription;
    private String partOfTagName;
    private String sortByName;
    private String sortByCreateDate;
    private String sortByTagName;

    public GiftCertificateCriteria() {
    }

    public GiftCertificateCriteria(String name, String tagName, String partOfName, String partOfDescription, String partOfTagName,
                                   String sortByName, String sortByCreateDate, String sortByTagName) {
        this.name = name;
        this.tagName = tagName;
        this.partOfName = partOfName;
        this.partOfDescription = partOfDescription;
        this.partOfTagName = partOfTagName;
        this.sortByName = sortByName;
        this.sortByCreateDate = sortByCreateDate;
        this.sortByTagName = sortByTagName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getPartOfName() {
        return partOfName;
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public String getPartOfDescription() {
        return partOfDescription;
    }

    public void setPartOfDescription(String partOfDescription) {
        this.partOfDescription = partOfDescription;
    }

    public String getPartOfTagName() {
        return partOfTagName;
    }

    public void setPartOfTagName(String partOfTagName) {
        this.partOfTagName = partOfTagName;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getSortByCreateDate() {
        return sortByCreateDate;
    }

    public void setSortByCreateDate(String sortByCreateDate) {
        this.sortByCreateDate = sortByCreateDate;
    }

    public String getSortByTagName() {
        return sortByTagName;
    }

    public void setSortByTagName(String sortByTagName) {
        this.sortByTagName = sortByTagName;
    }
}

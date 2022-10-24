package com.epam.esm.criteria.impl;

import com.epam.esm.criteria.Criteria;

import java.io.Serializable;

public class GiftCertificateCriteria implements Criteria, Serializable {
    private String searchByName;
    private String searchByTagName;
    private String searchByPartOfName;
    private String searchByPartOfTagName;
    private String searchByPartOfDescription;
    private String sortByName;
    private String sortByCreatedDate;
    private String sortByTagName;

    public String getSearchByName() {
        return searchByName;
    }

    public void setSearchByName(String searchByName) {
        this.searchByName = searchByName;
    }

    public String getSearchByTagName() {
        return searchByTagName;
    }

    public void setSearchByTagName(String searchByTagName) {
        this.searchByTagName = searchByTagName;
    }

    public String getSearchByPartOfName() {
        return searchByPartOfName;
    }

    public void setSearchByPartOfName(String searchByPartOfName) {
        this.searchByPartOfName = searchByPartOfName;
    }

    public String getSearchByPartOfTagName() {
        return searchByPartOfTagName;
    }

    public void setSearchByPartOfTagName(String searchByPartOfTagName) {
        this.searchByPartOfTagName = searchByPartOfTagName;
    }

    public String getSearchByPartOfDescription() {
        return searchByPartOfDescription;
    }

    public void setSearchByPartOfDescription(String searchByPartOfDescription) {
        this.searchByPartOfDescription = searchByPartOfDescription;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getSortByCreatedDate() {
        return sortByCreatedDate;
    }

    public void setSortByCreatedDate(String sortByCreatedDate) {
        this.sortByCreatedDate = sortByCreatedDate;
    }

    public String getSortByTagName() {
        return sortByTagName;
    }

    public void setSortByTagName(String sortByTagName) {
        this.sortByTagName = sortByTagName;
    }
}

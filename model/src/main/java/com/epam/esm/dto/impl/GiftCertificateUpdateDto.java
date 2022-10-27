package com.epam.esm.dto.impl;

import com.epam.esm.dto.GenericCrudDto;

import java.math.BigDecimal;
import java.util.List;

public class GiftCertificateUpdateDto extends GenericCrudDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    private List<TagCreateDto> tagList;

    public GiftCertificateUpdateDto() {
    }

    public GiftCertificateUpdateDto(Long id, String name, String description, BigDecimal price, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<TagCreateDto> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagCreateDto> tagList) {
        this.tagList = tagList;
    }
}

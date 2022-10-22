package com.epam.esm.dto.impl;

import com.epam.esm.dto.GenericCrudDto;

import java.math.BigDecimal;
import java.util.List;

public class GiftCertificateCreateDto extends GenericCrudDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    private List<TagCreateDto> tagCreateDtoList;

    public GiftCertificateCreateDto() {
    }

    public GiftCertificateCreateDto(String name, String description, BigDecimal price, Integer duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
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

    public List<TagCreateDto> getTagCreateDtoList() {
        return tagCreateDtoList;
    }

    public void setTagCreateDtoList(List<TagCreateDto> tagCreateDtoList) {
        this.tagCreateDtoList = tagCreateDtoList;
    }
}

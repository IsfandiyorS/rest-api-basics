package com.epam.esm.dto.impl;

import com.epam.esm.dto.GenericDto;

import java.math.BigDecimal;

public class GiftCertificateDto extends GenericDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(Long id, String name, String description, BigDecimal price, Integer duration) {
        super(id);
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
}

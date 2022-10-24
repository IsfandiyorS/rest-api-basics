package com.epam.esm.dto.impl;

import com.epam.esm.dto.GenericCrudDto;

public class TagCreateDto extends GenericCrudDto {

    private String name;

    public TagCreateDto(){}

    public TagCreateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

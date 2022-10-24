package com.epam.esm.dto.impl;

import com.epam.esm.dto.GenericCrudDto;

public class TagUpdateDto extends GenericCrudDto {

    private Long id;
    private String name;

    public TagUpdateDto(){}

    public TagUpdateDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

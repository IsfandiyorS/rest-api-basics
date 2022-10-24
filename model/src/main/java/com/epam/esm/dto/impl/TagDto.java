package com.epam.esm.dto.impl;

import com.epam.esm.dto.GenericDto;

public class TagDto extends GenericDto {
    private String name;

    public TagDto(){
    }

    public TagDto(String name) {
        this.name = name;
    }

    public TagDto(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

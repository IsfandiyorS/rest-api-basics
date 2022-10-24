package com.epam.esm.dto;

import com.google.gson.Gson;

import java.io.Serializable;


public class GenericDto implements Dto, Serializable {

    protected Long id;

    public GenericDto() {
    }

    public GenericDto(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

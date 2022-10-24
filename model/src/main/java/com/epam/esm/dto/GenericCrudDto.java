package com.epam.esm.dto;

import com.google.gson.Gson;

public class GenericCrudDto implements CrudDto {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}

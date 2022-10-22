package com.epam.esm.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public abstract class Identifiable implements BaseDomain {

    protected Long id;

    public Identifiable() {
    }

    public Identifiable(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifiable that = (Identifiable) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Identifiable.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}


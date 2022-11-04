package com.epam.esm.entity;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Abstract class {@code AbstractDomain} represents to identify objects.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public abstract class BaseAbstractDomain {

    protected Long id;

    public BaseAbstractDomain() {
    }

    public BaseAbstractDomain(Long id) {
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
        BaseAbstractDomain that = (BaseAbstractDomain) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseAbstractDomain.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}


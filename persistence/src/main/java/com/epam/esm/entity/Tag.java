package com.epam.esm.entity;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Abstract class {@code Tag} represents tag entity.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public class Tag extends BaseAbstractDomain {
    private String name;

    public Tag() {
    }

    public Tag(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("id=" + id)
                .toString();
    }
}

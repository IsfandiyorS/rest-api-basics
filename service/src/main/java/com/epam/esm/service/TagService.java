package com.epam.esm.service;

import com.epam.esm.criteria.TagCriteria;
import com.epam.esm.entity.Tag;

/**
 * Interface {@code TagService} describes abstract behavior for working with {@link Tag} objects.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public interface TagService extends GenericCrudService<Tag, Long, TagCriteria> {
}

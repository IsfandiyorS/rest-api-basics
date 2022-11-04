package com.epam.esm.controller;

import com.epam.esm.criteria.TagCriteria;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.*;
import com.epam.esm.response.DataResponse;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class {@code TagController} is an endpoint of the API which allows to perform CRD operations on tags.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tag".
 * So that {@code TagController} is accessed by sending request to /tag.
 *
 * @author Sultonov Isfandiyor
 * @since 1.0
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    private TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    /**
     * Method for getting tag by ID.
     *
     * @param id ID of tag to get
     * @return ResponseEntity with found tag entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<Tag>> getTagById(@PathVariable("id") Long id) throws ObjectNotFoundException {
        return ResponseEntity.ok(new DataResponse<>(tagService.getById(id)));
    }

    /**
     * Method for getting all tags from data source.
     *
     * @return ResponseEntity body with list of found tags
     */
    @GetMapping("/")
    public ResponseEntity<DataResponse<List<Tag>>> getAll() {
        return ResponseEntity.ok(new DataResponse<>(tagService.getAll()));
    }

    /**
     * Method for saving new tag.
     *
     * @param entity tag for saving
     * @return ResponseEntity with Long value which returns ID of created
     */
    @PostMapping("/create")
    public ResponseEntity<DataResponse<Long>> create(@RequestBody Tag entity) throws AlreadyExistException, ValidationException {
        return ResponseEntity.ok(new DataResponse<>(tagService.create(entity)));
    }

    /**
     * Method for removing tag by ID.
     *
     * @param id ID of tag to remove
     * @return ResponseEntity with Boolean body
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataResponse<Long>> deleteById(@PathVariable("id") Long id) throws ObjectNotFoundException, ActionFallDaoException {
        return ResponseEntity.ok(new DataResponse<>(tagService.delete(id)));
    }

    /**
     * Method for getting list of tags from data source by special filter.
     *
     * @param criteria request parameters, which include the information needed for the search
     * @return ResponseEntity with List of found tags
     */
    @GetMapping("/filter")
    public ResponseEntity<DataResponse<List<Tag>>> getTagsByFilterParams(@RequestBody TagCriteria criteria) throws ObjectNotFoundDaoException {
        return ResponseEntity.ok(new DataResponse<>(tagService.doFilter(criteria)));
    }
}

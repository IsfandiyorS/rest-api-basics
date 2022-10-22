package com.epam.esm.controller;

import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable("id") Long id) {
        return tagService.get(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return tagService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TagCreateDto dto) {
        return tagService.create(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return tagService.delete(id);
    }
}

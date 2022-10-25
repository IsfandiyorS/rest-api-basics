package com.epam.esm.controller;

import com.epam.esm.criteria.impl.GiftCertificateCriteria;
import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.dto.impl.GiftCertificateUpdateDto;
import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gift")
public class GiftCertificateController {

    private final GiftCertificateServiceImpl giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(giftCertificateService.get(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return giftCertificateService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody GiftCertificateCreateDto dto) {
        return giftCertificateService.create(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody GiftCertificateUpdateDto updateDto) {
        return giftCertificateService.update(updateDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return giftCertificateService.delete(id);
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<?> getAttachedTagsWithGiftCertificateId(@PathVariable("id") Long id) {
        return giftCertificateService.getAttachedTagsWithGiftCertificateId(id);
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<?> attachTagsToGiftCertificate(@RequestBody List<TagCreateDto> tags, @PathVariable("id") Long id) {
        return giftCertificateService.attachTagsToGiftCertificate(id, tags);
    }

    @DeleteMapping("/{id}/tags")
    public ResponseEntity<?> deleteAssociatedTags(@RequestBody List<TagCreateDto> tags, @PathVariable("id") Long id) {
        return giftCertificateService.deleteAssociatedTags(id, tags);
    }

    @GetMapping("/filter")
    public List<GiftCertificateDto> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> criteria) {
        return giftCertificateService.doFilter(criteria);
    }
}

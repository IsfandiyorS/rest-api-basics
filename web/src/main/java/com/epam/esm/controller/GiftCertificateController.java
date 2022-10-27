package com.epam.esm.controller;

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

    @GetMapping("/get_all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(giftCertificateService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody GiftCertificateCreateDto dto) {
        return ResponseEntity.ok(giftCertificateService.create(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody GiftCertificateUpdateDto updateDto) {
        return ResponseEntity.ok(giftCertificateService.update(updateDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(giftCertificateService.delete(id));
    }

    @GetMapping("/gift_tags_by_certificate_id/{id}")
    public ResponseEntity<?> getAttachedTagsWithGiftCertificateId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(giftCertificateService.getAttachedTagsWithGiftCertificateId(id));
    }

    @PostMapping("/attach_tags_by_certificate_id/{id}")
    public ResponseEntity<?> attachTagsToGiftCertificate(@RequestBody List<TagCreateDto> tags, @PathVariable("id") Long id) {
        return ResponseEntity.ok(giftCertificateService.attachTagsToGiftCertificate(id, tags));
    }

    @DeleteMapping("/delete_tags_by_certificate_id/{id}")
    public ResponseEntity<?> deleteAssociatedTags(@RequestBody List<TagCreateDto> tags, @PathVariable("id") Long id) {
        return ResponseEntity.ok(giftCertificateService.deleteAssociatedTags(id, tags));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<GiftCertificateDto>> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> criteria) {
        return ResponseEntity.ok(giftCertificateService.doFilter(criteria));
    }
}

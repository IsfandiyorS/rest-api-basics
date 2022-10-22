package com.epam.esm.controller;

import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateUpdateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getTagById(@PathVariable("id") Long id) {
//        return giftCertificateService.get(id);
//    }

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


}

package com.epam.esm.controller;

import com.epam.esm.criteria.GiftCertificateCriteria;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.*;
import com.epam.esm.response.DataResponse;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class {@code GiftCertificateController} is an endpoint of the API
 * which allows to perform CRUD operations on gift certificates.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/gift".
 * So that {@code GiftCertificateController} is accessed by sending request to /gift.
 *
 * @author Sultonov Isfandiyor
 * @since 1.0
 */
@RestController
@RequestMapping("/gift")
public class GiftCertificateController {

    private final GiftCertificateServiceImpl giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Method for getting gift certificate by ID.
     *
     * @param id ID of gift certificate to get.
     * @return ResponseEntity body with found gift certificate.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<GiftCertificate>> getById(@PathVariable("id") Long id) throws ObjectNotFoundException {
        return ResponseEntity.ok(new DataResponse<>(giftCertificateService.getById(id)));
    }

    /**
     * Method for getting all gift certificates from data source.
     *
     * @return ResponseEntity body with list of found gift certificates
     */
    @GetMapping("/")
    public ResponseEntity<DataResponse<List<GiftCertificate>>> getAll() {
        return ResponseEntity.ok(new DataResponse<>(giftCertificateService.getAll()));
    }


    /**
     * Method for saving new gift certificate.
     *
     * @param entity gift certificate for saving
     * @return ResponseEntity with Long value which returns ID of created gift
     */
    @PostMapping("/create")
    public ResponseEntity<DataResponse<Long>> create(@RequestBody GiftCertificate entity) throws AlreadyExistException, ValidationException, ActionFallDaoException {
        return ResponseEntity.ok(new DataResponse<>(giftCertificateService.create(entity)));
    }

    /**
     * Method for updating tags from the gift certificate.
     *
     * @param updateEntity gift certificate entity, which include information to update with its ID
     * @return ResponseEntity with Boolean which indicate the gift is created
     */
    @PostMapping("/update")
    public ResponseEntity<DataResponse<String>> update(@RequestBody GiftCertificate updateEntity) throws ValidationException, ObjectNotFoundException, AlreadyExistException, ActionFallDaoException {
        giftCertificateService.update(updateEntity);
        return ResponseEntity.ok(new DataResponse<>("Successfully updated"));
    }

    /**
     * Method for removing gift certificate by ID.
     *
     * @param id ID of gift certificate to remove
     * @return ResponseEntity with Boolean body
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataResponse<Long>> deleteById(@PathVariable("id") Long id) throws ObjectNotFoundException, ActionFallDaoException {
        return ResponseEntity.ok(new DataResponse<>(giftCertificateService.delete(id)));
    }

    @GetMapping("/gift_tags_by_certificate_id/{id}")
    public ResponseEntity<DataResponse<List<Tag>>> getAttachedTagsWithGiftCertificateId(@PathVariable("id") Long id) throws ObjectNotFoundException {
        return ResponseEntity.ok(new DataResponse<>(giftCertificateService.getAttachedTagsWithGiftCertificateId(id)));
    }

    /**
     * Method for getting list of tags by gift certificate ID.
     *
     * @param id ID of gift certificate
     * @return ResponseEntity with List of found tags body
     */
    @PostMapping("/attach_tags_by_certificate_id/{id}")
    public ResponseEntity<DataResponse<String>> attachTagsToGiftCertificate(@RequestBody List<Tag> tags, @PathVariable("id") Long id) throws AlreadyExistException, ValidationException, ObjectNotFoundException, ActionFallDaoException {
        giftCertificateService.attachTagsToGiftCertificate(id, tags);
        return ResponseEntity.ok(new DataResponse<>("Successfully attached"));
    }

    /**
     * Method for removing tags from the gift certificate.
     *
     * @param tags tags to delete
     * @param id   ID of gift certificate
     * @return ResponseEntity with Boolean body
     */
    @DeleteMapping("/delete_tags_by_certificate_id/{id}")
    public ResponseEntity<DataResponse<Long>> deleteAssociatedTags(@RequestBody List<Tag> tags, @PathVariable("id") Long id) throws ObjectNotFoundException, ValidationException, ActionFallDaoException {

        return ResponseEntity.ok(new DataResponse<>(giftCertificateService.deleteAssociatedTags(id, tags)));
    }


    /**
     * Method for getting list of gift certificates from data source by special filter.
     *
     * @param criteria request parameters, which include the information needed for the search
     * @return ResponseEntity with List of found gift certificates
     */
    @GetMapping("/filter")
    public ResponseEntity<DataResponse<List<GiftCertificate>>> getGiftCertificateByFilterParams(@RequestBody GiftCertificateCriteria criteria) throws ObjectNotFoundDaoException {
        return ResponseEntity.ok(new DataResponse<>(giftCertificateService.doFilter(criteria)));
    }

}
